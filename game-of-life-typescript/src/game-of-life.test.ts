import {GameOfLife} from "./game-of-life";

describe("Game Of life", () => {



    it("create grid from input", () => {

        const input = "" +
            "........\n" +
            "....*...\n" +
            "...**...\n" +
            "........";

        expect(new GameOfLife(input).getInput()).toStrictEqual([
            [".",".",".",".",".",".",".","."],
            [".",".",".",".","*",".",".","."],
            [".",".",".","*","*",".",".","."],
            [".",".",".",".",".",".",".","."],

        ])

    });


    it("Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.", () => {

        const input = "" +
            "........\n" +
            "....*...\n" +
            "...**...\n" +
            "........";

        expect(new GameOfLife(input).isAliveNextRound(1,4)).toBeTruthy();


    });


});