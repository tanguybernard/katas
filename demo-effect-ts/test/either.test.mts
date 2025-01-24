import { describe, it, expect, vi } from "vitest"
import {Effect, Either, Layer} from "effect"
import {HttpClientEffect, HttpError, mainEffect, runnable, UserNotFoundError} from "../src/script-tiny-either.mjs";



describe("User PostService", () => {
    it("should return a user when the user exists", async () => {
        // Mock du HttpClientEffect
        const mockHttpClient = {
            get: () =>
                Effect.succeed(
                    Either.right({
                        status: 200,
                        json: () => Promise.resolve({ id: 1, name: "John Doe" }),
                    } as Response)
                ),
        }

        // Créer un Layer mocké
        const mockLayer = Layer.succeed(HttpClientEffect, mockHttpClient)

        // Injecter le Layer mocké dans le programme
        const result = await Effect.runPromise(
            Effect.provide(mainEffect(1), mockLayer)
        )

        expect(result).toEqual({ id: 1, name: "John Doe" })
    })

    it("should fail with UserNotFoundError when the user does not exist", async () => {
        // Mock du HttpClientEffect pour simuler un 404
        const mockHttpClient = {
            get: () =>
                Effect.succeed(
                    Either.right({
                        status: 404,
                        json: () => Promise.resolve({}),
                    } as Response)
                ),
        }

        // Créer un Layer mocké
        const mockLayer = Layer.succeed(HttpClientEffect, mockHttpClient)

        // Injecter le Layer mocké et capturer l'erreur
        const result = await Effect.runPromise(
            Effect.flip(Effect.provide(mainEffect(999), mockLayer))
        )

        expect(result).toBeInstanceOf(UserNotFoundError)
        expect((result as UserNotFoundError).userId).toBe(999)
    })

    it("should fail with HttpError when the request fails", async () => {
        // Mock du HttpClientEffect pour simuler une erreur
        const mockHttpClient = {
            get: () =>
                Effect.succeed(
                    Either.left(new HttpError(new Error("Network error")))
                ),
        }

        // Créer un Layer mocké
        const mockLayer = Layer.succeed(HttpClientEffect, mockHttpClient)

        // Injecter le Layer mocké et capturer l'erreur
        const result = await Effect.runPromise(
            Effect.flip(Effect.provide(mainEffect(1), mockLayer))
        )

        expect(result).toBeInstanceOf(HttpError)
        expect((result as HttpError).error.message).toBe("Network error")
    })
})