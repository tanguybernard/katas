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

    //TODO pass strategy param to statement, strategy for output string or html or json...
    public statement(): string {
        let totalAmount: number = 0;
        let frequentRenterPoints: number = 0;
        let movies = [];

        for (const rentalMovie of this.rentals) {

            const thisAmount = this.getAmount(rentalMovie);

            const aMovie = this.movieFor(rentalMovie);

            frequentRenterPoints = this.computePoints(frequentRenterPoints, rentalMovie.getDaysRented(), aMovie.getPriceCode());

            // show figures for this rental
            movies.push({title: aMovie.getTitle(), amount: thisAmount.toFixed(1)})
            totalAmount += thisAmount;
        }


        return this.formatString(this.getName(), movies, totalAmount.toFixed(1), frequentRenterPoints)

    }

    //TODO simplify computePoints
    private computePoints(frequentRenterPoints: number, daysRented:number, priceCode:number) {
        // add frequent renter points
        frequentRenterPoints++;
        // add bonus for a two day new release rental
        if ((priceCode === Movie.NEW_RELEASE) && daysRented > 1)
            frequentRenterPoints++;
        return frequentRenterPoints;
    }

    private getAmount(rental: Rental) {
        let amount = 0;
        // determine amounts for each line
        switch (rental.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                amount += 2;
                if (rental.getDaysRented() > 2) {
                    amount += (rental.getDaysRented() - 2) * 1.5;
                }
                break;
            case Movie.NEW_RELEASE:
                amount += rental.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                amount += 1.5;
                if (rental.getDaysRented() > 3) {
                    amount += (rental.getDaysRented() - 3) * 1.5;
                }
                break;
        }
        return amount;
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


    movieFor(rental: Rental){
        return  rental.getMovie()
    }

}