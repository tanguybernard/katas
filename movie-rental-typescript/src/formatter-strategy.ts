
interface FormatterStrategy{
    format(name: String, movies: {title: string, amount: string}[], totalAmount: string, frequentRenterPoints:number) : string
}