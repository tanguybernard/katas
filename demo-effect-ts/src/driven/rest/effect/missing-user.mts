import {Data} from "effect";

export class MissingUser extends Data.TaggedError("MissingUser")<{
    message: string;
}> {}

