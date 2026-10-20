import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.OptionalInt;
import org.junit.Test;

public class PrimeMaxOnesTest {

    @Test
    public void primalityPredicate() {
        List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 97, 7919);
        primes.forEach(p -> assertTrue(p + " має бути простим", PrimeMaxOnes.IS_PRIME.test(p)));
        for (int notPrime : new int[]{-7, 0, 1, 4, 9, 15, 21, 100, 7917}) {
            assertFalse(notPrime + " не є простим", PrimeMaxOnes.IS_PRIME.test(notPrime));
        }
    }

    @Test
    public void countsOnesInBinaryForm() {
        assertEquals(0, PrimeMaxOnes.ONES.applyAsInt(0));
        assertEquals(1, PrimeMaxOnes.ONES.applyAsInt(8));
        assertEquals(3, PrimeMaxOnes.ONES.applyAsInt(7));
        assertEquals(5, PrimeMaxOnes.ONES.applyAsInt(31));
    }

    @Test
    public void smallLimits() {
        assertEquals(OptionalInt.of(2), PrimeMaxOnes.findPrimeWithMaxOnes(2));   // 10
        assertEquals(OptionalInt.of(3), PrimeMaxOnes.findPrimeWithMaxOnes(3));   // 11
        assertEquals(OptionalInt.of(3), PrimeMaxOnes.findPrimeWithMaxOnes(6));   // 5 теж 101, перше з двома одиницями це 3
        assertEquals(OptionalInt.of(7), PrimeMaxOnes.findPrimeWithMaxOnes(7));   // 111
        assertEquals(OptionalInt.of(7), PrimeMaxOnes.findPrimeWithMaxOnes(20));  // 11, 13, 19 теж по 3, перше 7
    }

    @Test
    public void knownLargerLimits() {
        assertEquals(OptionalInt.of(31), PrimeMaxOnes.findPrimeWithMaxOnes(100));
        assertEquals(OptionalInt.of(127), PrimeMaxOnes.findPrimeWithMaxOnes(127));
        assertEquals(OptionalInt.of(383), PrimeMaxOnes.findPrimeWithMaxOnes(500));   // 101111111
        assertEquals(OptionalInt.of(991), PrimeMaxOnes.findPrimeWithMaxOnes(1000));  // 1111011111
        assertEquals(OptionalInt.of(8191), PrimeMaxOnes.findPrimeWithMaxOnes(10000)); // 1111111111111
    }

    @Test
    public void noPrimesBelowTwo() {
        assertTrue(PrimeMaxOnes.findPrimeWithMaxOnes(1).isEmpty());
        assertTrue(PrimeMaxOnes.findPrimeWithMaxOnes(0).isEmpty());
        assertTrue(PrimeMaxOnes.findPrimeWithMaxOnes(-5).isEmpty());
    }

    @Test
    public void tiesReturnTheSmallest() {
        // до 100 п'ять одиниць мають 31, 47, 59, 61, 79, і береться перше з них
        assertEquals(List.of(31, 47, 59, 61, 79), PrimeMaxOnes.primesWithOnes(100, 5));
        assertEquals(31, PrimeMaxOnes.findPrimeWithMaxOnes(100).getAsInt());
    }

    @Test
    public void agreesWithPlainLoopsForManyLimits() {
        for (int n = 2; n <= 3000; n++) {
            int expected = -1;
            int bestOnes = -1;
            for (int x = 2; x <= n; x++) {
                boolean prime = true;
                for (int d = 2; d * d <= x; d++) {
                    if (x % d == 0) {
                        prime = false;
                        break;
                    }
                }
                if (prime && Integer.bitCount(x) > bestOnes) {
                    bestOnes = Integer.bitCount(x);
                    expected = x;
                }
            }
            assertEquals("n = " + n, expected, PrimeMaxOnes.findPrimeWithMaxOnes(n).getAsInt());
        }
    }
}
