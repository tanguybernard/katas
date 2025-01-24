// post-service.ts
import {Context, Effect, pipe} from "effect";
import { HttpClientEffect, HttpError } from "./http-client-effect.mjs";


export interface Post {
    userId: number;
    id: number;
    title: string;
    body: string;
}

export interface PostService {
    getPosts: () => Effect.Effect<Post[], HttpError>;
}

export const PostService = Context.GenericTag<PostService>("PostService");

export const makePosts = (): PostService => {
    const getPostsV1 = () => {
        return Effect.gen(function* () {
            const client = yield* HttpClientEffect; // Accéder à HttpClientEffect via yield*
            const response = yield* client.get("/posts", {}); // Exécuter la requête HTTP

            if (response.status !== 200) {
                return yield* Effect.fail(new HttpError(new Error("Failed to fetch posts"))); // Gérer les erreurs HTTP
            }

            const posts: Post[] = yield* Effect.tryPromise({
                try: () => response.json(), // Parser la réponse JSON
                catch: (error) => new HttpError(error as Error), // Gérer les erreurs de parsing
            });

            return posts;
        });
    };

    const getPostsV2 = () => {
        return pipe(
            HttpClientEffect,
            Effect.flatMap(client => client.get("/posts", {})),
            Effect.flatMap(response => {
                if (response.status !== 200) {
                    return Effect.fail(new HttpError(new Error("Failed to fetch posts")));
                }
                return Effect.tryPromise({
                    try: () => response.json() as Promise<Post[]>,
                    catch: (error) => new HttpError(error as Error),
                });
            })
        );
    };

    //V3
    const getPosts = () => {
        return pipe(
            HttpClientEffect,
            Effect.flatMap(client => client.get("/posts", {})),
            Effect.flatMap(response => {
                if (response.status !== 200) {
                    return Effect.fail(new HttpError(new Error("Failed to fetch posts")));
                }
                // Simulation d'une erreur dans la promesse
                const simulateError = true; // Changez cette valeur pour contrôler si une erreur est simulée
                if (simulateError) {
                    return Effect.promise(() => Promise.reject(new Error("Simulated JSON parsing error")));
                }

                return Effect.promise(() => response.json() as Promise<Post[]>);
            }),
            Effect.mapError(error => new HttpError(error as Error)) // Gestion des erreurs de parsing
        );
    };


    return { getPosts }; // Retourner l'objet conforme à l'interface PostService
};