import {Cause, Effect, Layer, pipe} from "effect"
import {
    HttpClientEffect,
    HttpClientImplementationEffect,
    HttpError,
    UserNotFoundError
} from "./driven/http-client-effect.mjs";


const makeUsers = Effect.gen(function* () {
    const client = yield* HttpClientEffect

    const findById = (id: number) => {
        const res = client.get(`/users/${id}`, {})

        return Effect.gen(function* () {
            const response = yield* res
            if (response.status === 404) {
                return yield* Effect.fail(new UserNotFoundError(id))
            }
            const user = yield* Effect.tryPromise({
                try: () => response.json(),
                catch: (error) => new HttpError(error as Error)
            })
            return user
        })
    }

    return { findById } as const
})

const client = new HttpClientImplementationEffect('https://jsonplaceholder.typicode.com');

const layer = Layer.succeed(HttpClientEffect, client);

const mainEffect = Effect.gen(function* () {
    const mm = yield* makeUsers;

    const user = yield* mm.findById(1);
    return user;
});

const mainEffect2 = pipe(
    makeUsers,
    Effect.flatMap((mm) => mm.findById(1))
)


const runnable = Effect.provide(mainEffect2, layer)

console.log('Yaouh !!!')
Effect.runPromise(runnable).then(
    (user) => console.log('User:', user),
    (error) => {
        if (Cause.isFailure(error)) {
            switch (error.error._tag) {
                case 'UserNotFoundError':
                    console.error(`User not found: ${error.error.userId}`)
                    break
                case 'RequestTimeoutError':
                    console.error('Request timed out')
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
)