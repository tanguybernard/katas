// main.ts
import {Effect, pipe} from "effect";
import { HttpClientEffect, HttpClientImplementationEffect } from "./http-client-effect.mjs";
import {makePosts, PostService} from "./post-service.mjs";
import {userImplemService, UserService} from "./user-service.mjs";

const program = Effect.gen(function* () {
    const postService = yield* PostService; // Récupérer PostService via le tag
    const { findById } = yield* UserService; // Récupérer UserService via le tag

    // Récupérer les posts
    const posts = yield* postService.getPosts();

    // Pour chaque post, récupérer l'email de l'utilisateur
    const postsWithEmails = yield* Effect.forEach(
        posts.slice(0, 20),
        (post: any) =>
            Effect.gen(function* () {
                const user = yield* findById(post.userId);
                return { ...post, userEmail: user.email };
            }),
        { concurrency: 5 } // Limite le nombre de requêtes concurrentes
    );

    return postsWithEmails;
});

// Fournir les services nécessaires
const runnable = pipe(
    program,
    Effect.provideService(HttpClientEffect, new HttpClientImplementationEffect("https://jsonplaceholder.typicode.com")),
    Effect.provideService(UserService, userImplemService()),
    Effect.provideService(PostService, makePosts()) // Fournir PostService
);

// Exécuter le programme
Effect.runPromise(runnable).then(console.log).catch(console.error);