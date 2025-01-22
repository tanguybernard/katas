import { Data, Effect } from "effect";

export class DanaEmailError extends Data.TaggedError("DanaEmailError")<{
    message: string;
}> {}