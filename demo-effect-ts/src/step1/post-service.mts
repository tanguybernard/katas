import {HttpClient} from "./http-client.mjs";

export class PostService {
    private httpClient: HttpClient;

    constructor(httpClient: HttpClient) {
        this.httpClient = httpClient;
    }

    async getLast20Posts(): Promise<any[]> {
        const response = await this.httpClient.get('https://jsonplaceholder.typicode.com/posts');
        const posts = await response.json();
        return posts.slice(-20); // Récupère les 20 derniers posts
    }


}