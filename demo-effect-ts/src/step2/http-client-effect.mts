import {Context, Data, Effect, pipe} from 'effect';
import fetch, {Response} from 'node-fetch';


export class UserNotFoundError {
    readonly _tag = "UserNotFoundError"
    constructor(readonly userId: number) {}
}


export class HttpError {
    readonly _tag = "HttpError"
    constructor(readonly error: Error) {}
}


export interface HttpClientEffect {
    readonly get: (
        endpoint: string,
        headers: Record<string, string>
    ) => Effect.Effect<Response, HttpError>;
}

export const HttpClientEffect = Context.GenericTag<HttpClientEffect>('HttpClientEffect');


export class HttpClientImplementationEffect implements HttpClientEffect {
    private baseUrl: string;

    constructor(baseUrl: string) {
        this.baseUrl = baseUrl;
    }

    get(
        endpoint: string,
        headers: Record<string, string> = {}
    ): Effect.Effect<Response, HttpError> {
        const url = `${this.baseUrl}${endpoint}`;

        return Effect.tryPromise({
            try: () => fetch(url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    ...headers,
                },
            }),
            catch: (error) => new HttpError(error as Error),
        });
    }
}