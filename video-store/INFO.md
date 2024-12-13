
# Video Store x POO

## Classes et Objets
Les classes représentent les entités du domaine (concepts métier) et les objets sont les instances de ces classes.

Exemple :


```java
public class Movie {
    private String title;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    // Getters and Setters
}
```

Ici, la classe Movie représente un film. Les instances de Movie (objets) représenteront des films spécifiques, comme "Star Wars" ou "Inception".

## Encapsulation 

L'encapsulation consiste à cacher les détails internes d'une classe et à fournir des méthodes publiques pour y accéder.

```java
public class Movie {
private String title;
private int priceCode;

    public String getTitle() {
        return title;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }
}
```

_Ici, les propriétés title et priceCode sont privées, ce qui empêche leur modification directe.
Elles ne peuvent être modifiées qu'à travers des méthodes publiques comme setPriceCode._

## Héritage

L'héritage permet de créer une hiérarchie entre les classes et de réutiliser du code.

```java
public class RegularMovie extends Movie {
    public RegularMovie(String title) {
        super(title, 0); // Price code for regular movies
    }
}

public class NewReleaseMovie extends Movie {
    public NewReleaseMovie(String title) {
        super(title, 1); // Price code for new releases
    }
}
```

Ici, RegularMovie et NewReleaseMovie héritent de Movie, ce qui leur permet de réutiliser les propriétés et méthodes définies dans la classe parent.


## Polymorphisme

Le polymorphisme permet d'utiliser des objets de différentes classes de manière interchangeable, souvent en passant par une interface ou une classe abstraite.


```java
public abstract class Movie {
    private String title;

    public Movie(String title) {
        this.title = title;
    }

    public abstract double calculateRentalCost(int daysRented);
}

public class RegularMovie extends Movie {
    public RegularMovie(String title) {
        super(title);
    }

    @Override
    public double calculateRentalCost(int daysRented) {
        return daysRented * 2;
    }
}

public class NewReleaseMovie extends Movie {
    public NewReleaseMovie(String title) {
        super(title);
    }

    @Override
    public double calculateRentalCost(int daysRented) {
        return daysRented * 3;
    }
}

```

_Ici, chaque type de film (RegularMovie, NewReleaseMovie) peut être traité comme un Movie. Cependant, le comportement est spécifique à chaque type grâce à la méthode calculateRentalCost._


## Abstraction

L'abstraction consiste à se concentrer sur les caractéristiques essentielles d'une classe tout en cachant les détails d'implémentation.

Exemple : La classe Movie définit une méthode abstraite calculateRentalCost, mais ne fournit pas les détails de son implémentation. Chaque sous-classe doit fournir sa propre implémentation.

## Relation entre Objets (Composition)
La composition permet de modéliser des relations "a un" ou "contient un".


```java
public class Rental {
    private Movie movie;
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public double calculateCost() {
        return movie.calculateRentalCost(daysRented);
    }
}
```

Ici, un Rental (location) contient un Movie. Cela montre une relation entre les deux objets.

## Autre composition possible

```java
public class Customer {
    private String name;
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public double calculateTotalCost() {
        return rentals.stream()
                      .mapToDouble(Rental::calculateCost)
                      .sum();
    }
}
```

Un Client (Customer) peut avoir plusieurs locations (Rental), et chaque location est associée à un film (Movie).

## Bénéfices de la POO dans ce Kata

- Réutilisabilité : Les classes peuvent être réutilisées et étendues.
- Lisibilité : Le code est plus facile à lire et à comprendre.
- Maintenabilité : Les modifications peuvent être faites de manière isolée dans les classes concernées.
- Évolutivité : Il est simple d'ajouter de nouveaux types de films ou de fonctionnalités sans casser le code existant.



## Kata 

https://github.com/cleancode-katas/cleancode-kata-videostore/tree/master