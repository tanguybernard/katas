// main.ts
import { Effect, pipe } from "effect";
import {HttpClientEffect, HttpClientImplementationEffect, HttpError, UserNotFoundError} from "./http-client-effect.mjs";
import {userImplemService, UserService} from "./user-service.mjs";
import {makePosts, Post, PostService} from "./post-service.mjs";

// Programme principal

// Programme principal
const program = pipe(
    PostService, // Accéder à PostService via le tag
    Effect.flatMap((postService: PostService) =>
        pipe(
            postService.getPosts(), // Récupérer les posts
            Effect.flatMap((posts) =>
                Effect.forEach(
                    posts.slice(0, 20), // Limiter aux 20 premiers posts
                    (post: Post) =>
                        pipe(
                            UserService, // Accéder à UserService via le tag
                            Effect.flatMap(({ findById }) => findById(post.userId)), // Récupérer l'utilisateur pour chaque post
                            Effect.map((user) => ({ ...post, userEmail: user.email })) // Combiner le post avec l'email de l'utilisateur
                        ),
                )
            )
        )
    )
);
// Fournir les services nécessaires
const runnable = pipe(
    program,
    Effect.provideService(HttpClientEffect, new HttpClientImplementationEffect("https://jsonplaceholder.typicode.com")),
    Effect.provideService(UserService, userImplemService()), // Fournir UserService
    Effect.provideService(PostService, makePosts()) // Fournir PostService
);

// Exécuter le programme
Effect.runPromise(runnable).then(console.log).catch(console.error);