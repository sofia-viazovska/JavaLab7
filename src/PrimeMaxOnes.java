import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * Завдання 6: серед простих чисел, які не перевищують n, знайти таке,
 * у двійковому записі якого найбільша кількість одиниць.
 * Усе зроблено за допомогою lambda-виразів і потоків IntStream.
 */
public class PrimeMaxOnes {

    /** Число просте, якщо воно більше 1 і не ділиться націло на жодне d від 2 до кореня з нього. */
    static final IntPredicate IS_PRIME = x -> x >= 2
            && IntStream.rangeClosed(2, (int) Math.sqrt(x)).noneMatch(d -> x % d == 0);

    /** Кількість одиниць у двійковому записі числа. */
    static final IntUnaryOperator ONES = x -> Integer.bitCount(x);

    /**
     * Повертає перше (найменше) просте число не більше n з максимальною кількістю
     * одиниць у двійковому записі. Якщо простих чисел немає (n менше 2), результат порожній.
     */
    public static OptionalInt findPrimeWithMaxOnes(int n) {
        return IntStream.rangeClosed(2, n)
                .filter(IS_PRIME)
                .reduce((best, candidate) -> ONES.applyAsInt(candidate) > ONES.applyAsInt(best) ? candidate : best);
    }

    /** Усі прості числа не більші за n, у двійковому записі яких рівно ones одиниць. */
    public static List<Integer> primesWithOnes(int n, int ones) {
        return IntStream.rangeClosed(2, n)
                .filter(IS_PRIME)
                .filter(p -> ONES.applyAsInt(p) == ones)
                .boxed()
                .toList();
    }
}
