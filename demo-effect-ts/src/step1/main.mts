import {HttpClient} from "./http-client.mjs";
import {PostService} from "./post-service.mjs";
import {UserService} from "./user-service.mjs";

async function main() {
    const httpClient = new HttpClient();
    const postService = new PostService(httpClient);
    const userService = new UserService(httpClient);

    try {
        const postsWithEmails = await getPostWithUserEmails(postService, userService);
        postsWithEmails.forEach(post => {
            console.log(`Title: ${post.title}, User Email: ${post.userEmail}`);
        });
    } catch (error) {
        console.error('An error occurred:', error);
    }
}

async function getPostWithUserEmails(postService: PostService, userService: UserService): Promise<{ title: string, userEmail: string }[]> {
    const posts = await postService.getLast20Posts();
    return await Promise.all(posts.map(async (post) => {
        const userEmail = await userService.getUserEmail(post.userId);
        return {
            title: post.title,
            userEmail: userEmail
        };
    }));
}

main();