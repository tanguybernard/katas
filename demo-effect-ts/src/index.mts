import {Context, Effect, Layer, pipe, Runtime} from "effect";
import {HttpClient, HttpClientImplementation} from "./driven/http-client.mjs";

import {UserRestEffectRepository} from "./driven/rest/effect/user-rest-effect-repository";
import {PostEffectRepository, PostRestEffectRepository} from "./driven/rest/effect/post-effect-repository.mjs";
import {UserEffectRepository} from "./driven/rest/effect/user-effect-repository.mjs";
import {PostEntity} from "./driven/rest/post-entity.mjs";
import {InvalidEmail} from "./driven/rest/effect/invalid-user.mjs";
import {DanaEmailError} from "./driven/rest/effect/dana-email-error.mjs";
import {UserCache, UserCacheLive} from "./driven/user-cache.mjs";
import {getUserByIdPure} from "./driven/rest/effect/pure-user.mjs";


const httpClient = new HttpClientImplementation('https://jsonplaceholder.typicode.com')
const postRepo = new PostRestEffectRepository(httpClient);
const userRepo = new UserRestEffectRepository(httpClient);






// Créer le contexte avec l'instance de PostRestRepository
const context = Context.make(PostEffectRepository, postRepo);

const contextWithUser = Context.make(PostEffectRepository, postRepo).pipe(
    Context.add(UserEffectRepository, userRepo),
    Context.add(UserCache, UserCacheLive)
);

const programOnlyPost = pipe(
    Effect.flatMap(PostEffectRepository, (repo:PostEffectRepository) => repo.getAll()), // Utiliser le tag pour accéder à l'implémentation
    Effect.tap((posts) => Effect.log(posts)), // Logguer les résultats
    Effect.catchAll((error) => Effect.log(`Error: ${error.message}`)) // Gérer les erreurs
);

// Exécuter le programme avec le contexte
//Effect.runPromise(programOnlyPost.pipe(Effect.provide(context)));




const program = pipe(
    Effect.flatMap(PostEffectRepository, (repo: PostEffectRepository) => repo.getAll()),
    Effect.flatMap((posts) =>
        Effect.forEach(posts, (post:PostEntity) =>
                pipe(
                    Effect.flatMap(UserEffectRepository, (userRepo:UserEffectRepository) => userRepo.getById(post.userId)),
                    Effect.map((user) => ({
                        title: post.title,
                        email: user.email
                    }))
                ),

        )
    ),
    Effect.tap((results) => Effect.log(results)), // Logguer les résultats
    Effect.catchAll((error) => Effect.log(`Error: ${error.message}`)) // Gérer les erreurs
);

// Exécuter le programme avec le contexte
//Effect.runPromise(program.pipe(Effect.provide(contextWithUser)));



///TEST 3


const fetchPosts = Effect.flatMap(PostEffectRepository, (repo) => repo.getAll());

// Étape 2 : Pour chaque post, récupérer l'utilisateur associé
const fetchUserForPost = (post: { userId: number; title: string }) =>
    pipe(
        Effect.flatMap(UserEffectRepository, (userRepo) => userRepo.getById(post.userId)),
        Effect.catchTag("MissingUser", () => Effect.succeed({ email: null })), // Gérer MissingUser
        Effect.flatMap((user) => {
            if (user.email && user.email.endsWith("@dana.io")) {
                return Effect.fail(new DanaEmailError({ message: `Dana email detected: ${user.email}` }));
            }
            if (user.email && user.email.endsWith("@karina.biz")) {//
                return Effect.fail(new InvalidEmail({ message: `Invalid email: ${user.email}` }));
            }
            return Effect.succeed({
                title: post.title,
                email: user.email,
            });
        }),
        Effect.catchTag("InvalidEmail", () => Effect.succeed(null)) // Exclure les posts avec des e-mails invalides
    );

// Étape 3 : Traiter tous les posts
const processPosts = (posts: Array<{ userId: number; title: string }>) =>
    Effect.forEach(posts, fetchUserForPost, { concurrency: 5 });

// Étape 4 : Filtrer les résultats et logger
const filterAndLogResults = (results: Array<{ title: string; email: string | null } | null>) =>
    pipe(
        Effect.succeed(results.filter((result) => result !== null)), // Filtrer les posts exclus
        Effect.tap((results) => Effect.log(results)) // Logguer les résultats
    );

// Programme principal
const program3 = pipe(
    fetchPosts, // Étape 1 : Récupérer les posts
    Effect.flatMap(processPosts), // Étape 2 et 3 : Traiter chaque post
    Effect.flatMap(filterAndLogResults), // Étape 4 : Filtrer et logger
    Effect.catchTag("DanaEmailError", (error: DanaEmailError) => Effect.log(`Fatal error: ${error.message}`)
        .pipe(Effect.flatMap(() => Effect.fail(error)))), // Gérer DanaEmailError
    Effect.catchAll((error) => Effect.log(`Error: ${error.message}`)) // Gérer les erreurs globales
);


//TODO COMMENT Effect.runPromise(program3.pipe(Effect.provide(contextWithUser)));



//PURE 4


const httpClientLayer = Layer.sync(HttpClient, () => httpClient);

// Étape 2 : Combiner les couches nécessaires (HttpClient + UserCache)
const appLayer = pipe(
    UserCacheLive,
    Layer.merge(httpClientLayer) // Ajouter HttpClient à l'environnement
);


// Programme
const program4 = pipe(
    getUserByIdPure(1), // Récupérer un utilisateur
    Effect.tap((user) => Effect.log(`User: ${JSON.stringify(user)}`)), // Logguer le résultat
    Effect.catchAll((error) => Effect.log(`Error: ${error.message}`)) // Gérer les erreurs
);

const programWithContext = program4.pipe(Effect.provide(appLayer));

Effect.runPromise(programWithContext)
    .then(() => console.log("Program executed successfully!"))
    .catch((error) => console.error("Program failed:", error));