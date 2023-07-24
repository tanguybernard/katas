
export class TextFormatterStrategy implements FormatterStrategy {
    format(name: String, movies: {
        title: string;
        amount: string
    }[], totalAmount: string, frequentRenterPoints: number): string {

        let result = "Rental Record for " + name + "\n";
        movies.forEach(({title, amount}) => {
            result += "\t" + title + "\t" + amount + "\n";
        })
        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points";
        return result;

    }

}