import {HttpClient} from "./http-client.mjs";

export class UserService {
    private httpClient: HttpClient;

    constructor(httpClient: HttpClient) {
        this.httpClient = httpClient;
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


}