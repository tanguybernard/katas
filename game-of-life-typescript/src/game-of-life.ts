
export class GameOfLife {
    private input: string;


    constructor(input: string) {
        this.input = input;
    }

    public getInput(): string[][]{

        return this.input.split("\n").map(raw => raw.split(""));

    }

    isAliveNextRound(v: number, h: number) {

        let alive = 0;

       let t = this.getInput();

        if(this.getInput()[v][h+1] && this.getInput()[v][h+1]==="*"){
            alive++;
        }
        if(this.getInput()[v][h-1] && this.getInput()[v][h-1] ==="*"){
            alive++;
        }
        if(this.getInput()[v+1][h] && this.getInput()[v+1][h]==="*"){
            alive++;
        }
        if(this.getInput()[v-1][h] && this.getInput()[v-1][h] ==="*"){
            alive++;
        }
        if(this.getInput()[v-1][h-1] && this.getInput()[v-1][h-1] ==="*"){
            alive++;
        }
        if(this.getInput()[v-1][h+1] && this.getInput()[v-1][h+1] ==="*"){
            alive++;
        }
        if(this.getInput()[v+1][h-1] && this.getInput()[v+1][h-1] ==="*"){
            alive++;
        }
        if(this.getInput()[v+1][h+1] && this.getInput()[v+1][h+1] ==="*"){
            alive++;
        }

        return alive >= 2;

    }
}