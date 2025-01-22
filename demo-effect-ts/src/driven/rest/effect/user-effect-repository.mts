import {Context, Effect} from "effect";
import {UserEntity} from "../user-entity.mjs";

export interface UserEffectRepository {
    readonly getById: (userId: number) => Effect<UserEntity>;
}

export const UserEffectRepository = Context.GenericTag<UserEffectRepository>('UserEffectRepository');