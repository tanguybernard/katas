// Définir le service utilisateur
import {Context, Effect, Either, pipe} from "effect";
import {HttpClientEffect, HttpError, UserNotFoundError} from "./http-client-effect.mjs";

export interface User {
    id: number;
    name: string;
    email: string;
}

export interface UserService {
    findById: (id: number) => Effect.Effect<User, UserNotFoundError | HttpError>;
}

export const UserService = Context.GenericTag<UserService>("UserService");


export const userImplemService = (): UserService => {
    const findById = (id: number) => {
        return Effect.gen(function* () {
            const client = yield* HttpClientEffect; // Accéder à HttpClientEffect via yield*
            const response = yield* client.get(`/users/${id}`, {}); // Exécuter la requête HTTP

            if (response.status === 404) {
                return yield* Effect.fail(new UserNotFoundError(id)); // Gérer l'erreur 404
            }

            const user: User = yield* Effect.tryPromise({
                try: () => response.json(), // Parser la réponse JSON
                catch: (error) => new HttpError(error as Error), // Gérer les erreurs de parsing
            });

            return user;
        });
    };

    return { findById }; // Retourner l'objet conforme à l'interface UsersService
};