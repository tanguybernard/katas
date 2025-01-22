import {HttpClient} from "../../http-client.mjs";
import {Context, Effect, pipe} from "effect";
import {User} from "../../../ports/user-api.mjs";
import {UserEffectRepository} from "./user-effect-repository.mjs";
import {MissingUser} from "./missing-user.mjs";
import {UserEntity} from "../user-entity.mjs";
import {UserCache} from "../../user-cache.mjs";





export class UserRestEffectRepository implements UserEffectRepository{

    constructor(private httpClient: HttpClient) {

    }

    getById(userId: number): Effect.Effect<never, MissingUser | Error, UserEntity> {
        return pipe(
            Effect.flatMap(UserCache, (cache) => cache.get(userId)), // Vérifier le cache
            Effect.flatMap((cachedUser) =>
                cachedUser
                    ? Effect.succeed(cachedUser) // Retourner l'utilisateur du cache
                    : pipe(
                        Effect.tryPromise({
                            try: async () => {
                                const result = await this.httpClient.get(`/users/${userId}`, {});
                                const user = await result.json();
                                if (!user) {
                                    throw new MissingUser({ message: `User with ID ${userId} not found` });
                                }
                                return user;
                            },
                            catch: (error) => new Error(`Failed to fetch user: ${error}`),
                        }),
                        //run a side effect:
                        Effect.tap((user) => Effect.flatMap(UserCache, (cache) => cache.set(userId, user))) // Mettre à jour le cache
                    )
            )
        );
    }


    getByIdWithoutCache(userId: number): Effect.Effect<never, MissingUser | Error, UserEntity> {
        return Effect.tryPromise({
            try: async () => {
                const result = await this.httpClient.get(`/users/${userId}`, {});
                const user = await result.json();
                if (!user) {
                    throw new MissingUser({ message: `User with ID ${userId} not found` });
                }
                return user;
            },
            catch: (error) => {
                if (error instanceof MissingUser) {
                    return error;
                }
                return new Error(`Failed to fetch user: ${error}`);
            },
        });
    }
}
