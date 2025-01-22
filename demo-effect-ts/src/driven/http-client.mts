import { Context } from 'effect';


export interface HttpClient {
    readonly get: (endpoint: string, headers: Record<string, string>) => Promise<Response>
}

export const HttpClient = Context.GenericTag<HttpClient>('HttpClient');

export class HttpClientImplementation implements HttpClient {
    private baseUrl: string;

    constructor(baseUrl: string) {
        this.baseUrl = baseUrl;
    }

    async get(endpoint: string, headers: Record<string, string> = {}): Promise<Response> {

        const url = `${this.baseUrl}${endpoint}`;
        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                ...headers,
            },
        });

        return response;
    }
}


