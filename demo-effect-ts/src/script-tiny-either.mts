import { Effect, Layer, Context, pipe, Either, Cause } from "effect"
import fetch, { Response } from 'node-fetch'

// Définir les erreurs personnalisées
export class UserNotFoundError {
    readonly _tag = "UserNotFoundError"
    constructor(readonly userId: number) {}
}

export class HttpError {
    readonly _tag = "HttpError"
    constructor(readonly error: Error) {}
}

// Définir le client HTTP
export interface HttpClientEffect {
    readonly get: (
        endpoint: string,
        headers: Record<string, string>
    ) => Effect.Effect<Either.Either<HttpError, Response>, never>
}

export const HttpClientEffect = Context.GenericTag<HttpClientEffect>('HttpClientEffect')

export class HttpClientImplementationEffect implements HttpClientEffect {
    private baseUrl: string

    constructor(baseUrl: string) {
        this.baseUrl = baseUrl
    }

    get(
        endpoint: string,
        headers: Record<string, any> = {}
    ): Effect.Effect<Either.Either<HttpError, Response>, never> {
        const url = `${this.baseUrl}${endpoint}`

        return Effect.tryPromise({
            try: () => fetch(url, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    ...headers,
                },
            }),
            catch: (error) => new HttpError(error as Error)
        }).pipe(
            Effect.map(Either.right), // Envelopper la réponse réussie dans Either.right
            Effect.catchAll((error) => Effect.succeed(Either.left(error))) // Envelopper l'erreur dans Either.left
        )
    }
}

// Définir le service utilisateur
const makeUsers = Effect.gen(function* () {
    const client = yield* HttpClientEffect

    const findById = (id: number) => {
        const res = client.get(`/users/${id}`, {})

        return pipe(
            res,
            Effect.flatMap((eitherResponse) =>
                Either.match(eitherResponse, {
                    onLeft: (httpError) => Effect.fail(httpError), // Propagate HttpError
                    onRight: (response) => {
                        if (response.status === 404) {
                            return Effect.fail(new UserNotFoundError(id))
                        }
                        return Effect.tryPromise({
                            try: () => response.json(),
                            catch: (error) => new HttpError(error as Error)
                        })
                    }
                })
            )
        )
    }

    return { findById } as const
})

// Configurer le programme principal
export const mainEffect = (userId) => pipe(
    makeUsers,
    Effect.flatMap((mm) => mm.findById(userId))
)

// Configurer le layer et exécuter le programme
const client = new HttpClientImplementationEffect('https://jsonplaceholder.typicode.com')
// HttpClientEffect est un tag (ou une clé) qui représente la dépendance "client HTTP".
const layer = Layer.succeed(HttpClientEffect, client) //agit comme un conteneur pour fournir des implémentations concrètes
export const runnable =(userId: number) => Effect.provide(mainEffect(userId), layer) //utilisé pour injecter les dépendances nécessaires dans un effet (mainEffect)


console.log('Yaouh !!!')
/*
Effect.runPromise(runnable(1)).then(
    (user) => console.log('User:', user),
    (error) => {
        if (Cause.isFailure(error)) {
            switch (error.error._tag) {
                case 'UserNotFoundError':
                    console.error(`User not found: ${error.error.userId}`)
                    break
                case 'HttpError':
                    console.error('HTTP error:', error.error.error.message)
                    break
                default:
                    console.error('Unknown error:', error.error)
            }
        } else {
            console.error('Unknown error:', error)
        }
    }
)*/