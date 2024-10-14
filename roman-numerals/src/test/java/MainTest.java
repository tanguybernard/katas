import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;
import org.example.Roman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testable
public class MainTest {


    Roman converter;
    @BeforeEach
    public void setUp() {
        converter = new Roman();
    }


    @Provide
    Arbitrary<Integer> nonZeroNumbers() {
        return Arbitraries.integers().filter(v -> v != 0);
    }
    public int divide(int a, int b) {
        return a/b;
    }



    @Property
    public void divideBySelf(@ForAll("nonZeroNumbers") @Positive int a, @ForAll @Positive int b) {

        Assume.that(a > b);

        int result = divide(a, b);
        assertThat(result).isPositive();
    }

    @Test
    public void test1(){
        String result = converter.convert(1);
        assertThat(result).isEqualTo("I");
    }

    @Test
    public void test2(){
        String result = converter.convert(2);
        assertThat(result).isEqualTo("II");
    }

    @Test
    public void test12(){
        String result = converter.convert(12);
        assertThat(result).isEqualTo("XII");
    }



    // Fournir une liste d'entrées
    @Provide
    Arbitrary<List<Integer>> integerList() {
        return Arbitraries.of(Collections.singleton(List.of(5, 12, 14, 15, 48)));
    }

    // Liste des résultats attendus (par exemple, ici les valeurs doublées)
    @Provide
    Arbitrary<List<String>> expectedResultList() {
        return Arbitraries.of(Collections.singleton(List.of("V", "XII", "XIV", "XV", "XLVIII")));
    }

    @Property
    boolean testListValues(@ForAll("integerList") List<Integer> inputList,
                           @ForAll("expectedResultList") List<String> resultList) {

        converter = new Roman();
        // Comparer les listes d'entrée et de sortie (en fonction d'une logique)
        for (int i = 0; i < inputList.size(); i++) {
            if (!Objects.equals(converter.convert(inputList.get(i)), resultList.get(i))) {
                return false;  // Fausse propriété si les résultats ne correspondent pas
            }
        }
        return true;
    }


    @Property
    public void converterTest(@ForAll("nonZeroNumbers") @Positive int a) {


        Assume.that(a > 0);

        //Le nombre maximun romain est donc MMMCMXCIX = 3999 arabe
        Assume.that(a <= 3999); //Remove this and show error with 4000 => MMMM



        converter = new Roman();

        String  result = converter.convert(a);

        //TODO chercher d'autres cas :
        assertThat(result).doesNotContain("XXXX","MMMM");

        /*

        Shrunk Sample (4 steps)
        -----------------------
                arg0: 4000

        Original Sample
        ---------------
                arg0: 35291

         */

    }

}
