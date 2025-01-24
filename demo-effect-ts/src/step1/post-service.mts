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

    async getUserEmail(userId: number): Promise<string> {
        try {
            const response = await this.httpClient.get(`https://jsonplaceholder.typicode.com/users/${userId}`);
            const user = await response.json();
            return user.email;
        } catch (error) {
            console.error(`User with ID ${userId} not found.`);
            throw error;
        }
    }

    async getPostsWithUserEmails(): Promise<{ title: string, userEmail: string }[]> {
        const posts = await this.getLast20Posts();
        const postsWithEmails = await Promise.all(posts.map(async (post) => {
            const userEmail = await this.getUserEmail(post.userId);
            return {
                title: post.title,
                userEmail: userEmail
            };
        }));
        return postsWithEmails;
    }
}