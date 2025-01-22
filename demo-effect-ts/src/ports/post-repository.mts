import {Context} from "effect";
import {PostEntity} from "../driven/rest/post-entity.mjs";

export interface PostRepository {
    readonly getAll: () => Promise<PostEntity>
}

export const PostRepository = Context.GenericTag<PostRepository>('PostRepository');


