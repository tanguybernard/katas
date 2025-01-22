import {Context, Effect, Layer} from "effect";
import { Data } from "effect"
import {HttpClient, HttpClientImplementation} from "./driven/http-client.mjs";

const myClient = new HttpClientImplementation('https://jsonplaceholder.typicode.com')


myClient.get('/posts/1', {})
    .then((r) => r.json())
    .then(r => console.log(r))
    .catch(e => console.log(e));


class MissingUser extends Data.TaggedError("MissingUser")<{
    message: string
}> {}

type User = {id: number, userId: number, email: string}

export interface UserService {
    readonly getUserById: ({ id }: { id: number }) => Effect.Effect<
        User,
        MissingUser,
        HttpClient
    >;
}

export const UserService = Context.GenericTag<UserService>("UserService");



const id = 1;



export const getAllEffect = (userId: number) =>
    Effect.gen(function* () {
        const http = yield* HttpClient;

        const response: Response = yield* Effect.tryPromise(() =>
            http.get('/users/'+userId, {})
        );

        const json: User = yield* Effect.tryPromise(() =>
            response.json()
        );

        return json;
    });



const service = getAllEffect(id).pipe(
    Effect.provideService(HttpClient, myClient),
);

const response = service.pipe(
    Effect.matchEffect({
        onFailure: (e) => {
            console.error('Erreur lors de la récupération des commandes :', e);
            return Effect.succeed({
                statusCode: 400,
                body: '',
            });
        },
        onSuccess: (user) => {
            return Effect.succeed({
                statusCode: 200,
                body: {
                    message: 'Orders fetched successfully',
                    user,
                },
            });
        },
    })
);

const user = Effect.runPromise(response);

user.then(console.log)



export const UserService2Live = Layer.effect(
    UserService,
    Effect.map(HttpClient, (http) => UserService.of({
        getUserById: ({ id }) => {
            return Effect.succeed({userId:1, id:1, email:"test@example.com"});
        }

    }))
);



const mainEffect = Effect.gen(function* () {
    const userService = yield* UserService;

    const user = yield* userService.getUserById({ id: 1 });
    return user;
});


const runnable = Effect.provide(mainEffect, UserService2Live)
    .pipe(Effect.provideService(HttpClient, myClient))

console.log('Yaouh !!!')
Effect.runPromise(runnable).then(console.log).catch(console.error);