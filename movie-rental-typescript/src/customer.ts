import {Movie} from "./movie";
import {Rental} from "./rental";

export class Customer {

    private name: string;
    private rentals: Rental[] = [];

    public constructor(name: string) {
        this.name = name;
    }

    public addRental(arg: Rental) {
        this.rentals.push(arg);
    }

    public getName(): string {
        return this.name;
    }

    public statement(): string {
        let totalAmount: number = 0;
        let frequentRenterPoints: number = 0;
        let movies = [];

        for (const each of this.rentals) {
            let thisAmount = 0;

            // determine amounts for each line
            switch (each.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (each.getDaysRented() > 2) {
                        thisAmount += (each.getDaysRented() - 2) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += each.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    thisAmount += 1.5;
                    if (each.getDaysRented() > 3) {
                        thisAmount += (each.getDaysRented() - 3) * 1.5;
                    }
                    break;
            }

            // add frequent renter points
            frequentRenterPoints++;
            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() === Movie.NEW_RELEASE) && each.getDaysRented() > 1)
                frequentRenterPoints++;

            // show figures for this rental
            movies.push({title: each.getMovie().getTitle(), amount: thisAmount.toFixed(1)})
            totalAmount += thisAmount;
        }


        return this.formatString(this.getName(), movies, totalAmount.toFixed(1), frequentRenterPoints)

    }

    formatString(name: String, movies: {title: string, amount: string}[], totalAmount: string, frequentRenterPoints:number): string{

        let result = "Rental Record for " + name + "\n";

        movies.forEach(({title, amount}) => {
            result += "\t" + title + "\t" + amount + "\n";
        })

        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points";

        return result;
    }

}