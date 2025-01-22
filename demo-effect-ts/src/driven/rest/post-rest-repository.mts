import {HttpClient} from "../http-client.mjs";
import {PostRepository} from "../../ports/post-repository.mjs";



export class PostRestRepository implements PostRepository{

    constructor(private httpClient: HttpClient) {

    }

    async getAll(): Promise<any> {
        const result = await this.httpClient.get('/posts', {});
        return result.json();

    }

}





