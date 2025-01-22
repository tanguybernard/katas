import { Effect, Context, Layer } from "effect";
import {UserEntity} from "./rest/user-entity.mjs";

// Définir le service de cache
interface UserCache {
    get: (userId: number) => Effect.Effect<never, never, UserEntity | undefined>;
    set: (userId: number, user: UserEntity) => Effect.Effect<never, never, void>;
}

export const UserCache = Context.GenericTag<UserCache>("UserCache");

// Implémentation du cache en mémoire
export const UserCacheLive = Layer.sync(UserCache, () => {
    const cache = new Map<number, UserEntity>();

    return {
        get: (userId) => Effect.sync(() => cache.get(userId)),
        set: (userId, user) => Effect.sync(() => cache.set(userId, user)),
    };
});