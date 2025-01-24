export class HttpClient {
    async get(url: string): Promise<Response> {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response;
    }
}