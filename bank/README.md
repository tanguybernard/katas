
```java

import net.jqwik.api.*;
import net.jqwik.api.constraints.DoubleRange;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {


    @Property
    public void depositAPositiveAmount(@ForAll double amount) {

        Assume.that(amount > 0.0);

        var account = new Account("0", new Balance(0.0));

        var result = account.deposit(amount);

        assertThat(result.balance().amount()).isEqualTo(amount);
        assertThat(result.balance().amount()).isGreaterThan(0.0);
    }

    @Property
    public void depositNegative(@ForAll double amount) {

        Assume.that(amount < 0);

        var account = new Account("0", new Balance(0.0));


        assertThatThrownBy(() -> account.deposit(amount)).hasMessage("Not negative");
    }

    @Property
    public void withdraw(@ForAll @DoubleRange(min = 0.01, max = 200.00) double amount) {

        var account = new Account("0", new Balance(1000.0));

        var result = account.withdraw(amount);

        assertThat(result.balance().amount()).isEqualTo(account.balance().amount()-amount);
        assertThat(result.balance().amount()).isGreaterThanOrEqualTo(0.0);
    }

    @Property
    public void divideNonZeroBySelf(@ForAll("invariantsWithdraw") int value) {
        var account = new Account("0", new Balance(1000.0));

        var result = account.withdraw(value);

        assertThat(result.balance().amount()).isEqualTo(account.balance().amount()-value);
        assertThat(result.balance().amount()).isGreaterThanOrEqualTo(0.0);
    }

    @Provide
    Arbitrary<Integer> invariantsWithdraw() {
        return Arbitraries.integers().filter(v -> v >=0.0 && v <= 200.00);
    }

}

```



```java
public record Account(String id, Balance balance) {



    public Account deposit(double amount) {
        return new Account(id(), new Balance(this.balance.amount()+amount));
    }

    public Account withdraw(double amount) {
        if(amount > 200.0) {
            throw new RuntimeException("Too much");
        }
        return new Account(id(), new Balance(this.balance.amount()-amount));
    }
}



///////////////////////////////////////

public record Balance(Double amount) {

    public Balance(Double amount) {
        if(amount <0.0) {
            throw new RuntimeException("Not negative");
        }
        this.amount = amount;
    }
}
```
