import {HttpClient} from "../../http-client.mjs";
import {Context, Effect} from "effect";
import {PostEntity} from "../post-entity.mjs";


export interface PostEffectRepository {
    readonly getAll: () => Effect<PostEntity>
}

export const PostEffectRepository = Context.GenericTag<PostEffectRepository>('PostEffectRepository');



export class PostRestEffectRepository implements PostEffectRepository{

    constructor(private httpClient: HttpClient) {

    }


    getAll(): Effect.Effect<never, Error, any> {
        return Effect.tryPromise({
            try: async () => {
                const result = await this.httpClient.get("/posts", {});
                return result.json();
            },
            catch: (error) => new Error(`Failed to fetch posts: ${error}`),
        });
    }
}
