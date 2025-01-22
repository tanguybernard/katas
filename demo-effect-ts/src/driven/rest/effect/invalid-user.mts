import {Data} from "effect";

export class InvalidEmail extends Data.TaggedError("InvalidEmail")<{
    message: string;
}> {}