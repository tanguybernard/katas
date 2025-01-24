// post-service.ts
import {Context, Effect} from "effect";
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
    const getPosts = () => {
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

    return { getPosts }; // Retourner l'objet conforme à l'interface PostService
};