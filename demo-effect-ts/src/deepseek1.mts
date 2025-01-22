import { Effect, Context, pipe } from "effect"
import {HttpClient, HttpClientImplementation} from "./driven/http-client.mjs";

// Définir les interfaces pour les données
interface Post {
    id: number
    title: string
    userId: number
}

interface User {
    id: number
    email: string
}

// Définir une erreur personnalisée pour un utilisateur manquant
class MissingUser extends Error {
    constructor(userId: number) {
        super(`User with ID ${userId} not found`)
        this.name = "MissingUser"
    }
}



// Fonction pour récupérer les posts
const fetchPosts = Effect.gen(function* (_) {
    const httpClient = yield* _(HttpClient)
    const response = yield* _(
        Effect.tryPromise({
            try: () => httpClient.get("/posts"),
            catch: (error) => new Error(`Failed to fetch posts: ${error}`),
        })
    )

    if (!response.ok) {
        return yield* _(Effect.fail(new Error(`Failed to fetch posts: ${response.statusText}`)))
    }

    return yield* _(Effect.tryPromise({
        try: () => response.json() as Promise<Post[]>,
        catch: (error) => new Error(`Failed to parse posts: ${error}`),
    }))
})

// Fonction pour récupérer un utilisateur par son ID
const fetchUser = (userId: number) =>
    Effect.gen(function* (_) {
        const httpClient = yield* _(HttpClient)
        const response = yield* _(
            Effect.tryPromise({
                try: () => httpClient.get(`/users/${userId}`),
                catch: (error) => new Error(`Failed to fetch user ${userId}: ${error}`),
            })
        )

        if (!response.ok) {
            if (response.status === 404) {
                return yield* _(Effect.fail(new MissingUser(userId)))
            }
            return yield* _(Effect.fail(new Error(`Failed to fetch user ${userId}: ${response.statusText}`)))
        }

        return yield* _(Effect.tryPromise({
            try: () => response.json() as Promise<User>,
            catch: (error) => new Error(`Failed to parse user ${userId}: ${error}`),
        }))
    })

// Fonction principale pour récupérer les posts et les emails des utilisateurs
const fetchPostsWithUserEmails = Effect.gen(function* (_) {
    const posts = yield* _(fetchPosts)

    for (const post of posts) {
        const userEffect = fetchUser(post.userId)

        // Gérer l'erreur MissingUser
        const userResult = yield* _(
            Effect.either(userEffect) // Convertir l'effet en Either<Error, User>
        )

        if (userResult._tag === "Left") {
            if (userResult.left instanceof MissingUser) {
                console.error(`Error for Post ID ${post.id}: ${userResult.left.message}`)
            } else {
                console.error(`Unexpected error for Post ID ${post.id}: ${userResult.left.message}`)
            }
            continue // Passer au post suivant en cas d'erreur
        }

        const user = userResult.right
        console.log(`Post ID: ${post.id}, Title: ${post.title}, User Email: ${user.email}`)
    }
})

// Créer une instance du client HTTP
const httpClient = new HttpClientImplementation("https://jsonplaceholder.typicode.com")

// Fournir le client HTTP au programme Effect
const program = Effect.provideService(
    fetchPostsWithUserEmails,
    HttpClient,
    httpClient
)

// Exécuter le programme Effect
Effect.runPromise(program).catch(console.error)