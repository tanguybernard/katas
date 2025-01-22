// user-repository.ts
import { Effect, pipe } from "effect";
import { HttpClient } from "../../http-client.mjs";
import { UserEntity } from "../user-entity.mjs";
import {MissingUser} from "./missing-user.mjs";
import {UserCache} from "../../user-cache.mjs";

// Fonction pure pour récupérer un utilisateur
export const getUserByIdPure = (userId: number) =>
    pipe(
        Effect.flatMap(UserCache, (cache: UserCache) => cache.get(userId)), // Vérifier le cache
        Effect.flatMap((cachedUser) =>
            cachedUser
                ? Effect.succeed(cachedUser) // Retourner l'utilisateur du cache
                : pipe(
                    Effect.flatMap(HttpClient, (httpClient) =>
                        Effect.tryPromise({
                            try: async () => {
                                const result = await httpClient.get(`/users/${userId}`, {});
                                const user = await result.json();
                                if (!user) {
                                    throw new MissingUser({ message: `User with ID ${userId} not found` });
                                }
                                return user;
                            },
                            catch: (error) => new Error(`Failed to fetch user: ${error}`),
                        })
                    ),
                    Effect.tap((user) => Effect.flatMap(UserCache, (cache) => cache.set(userId, user))) // Mettre à jour le cache
                )
        )
    );