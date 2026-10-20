import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

/** Лабораторна 7 (lambda), завдання 6: n вводиться з клавіатури, програма виводить результат. */
public class Main {

    /** Верхня межа n, щоб перевірка простоти не тривала занадто довго. */
    static final int MAX_N = 5_000_000;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Серед простих чисел, не більших за n, шукаємо те, у двійковому записі якого найбільше одиниць.");
        while (true) {
            System.out.print("\nВведіть n (ціле від 2 до " + MAX_N + ", 0 для виходу): ");
            if (!in.hasNextLine()) {
                return;
            }
            String line = in.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n == 0) {
                    return;
                }
                if (n < 2 || n > MAX_N) {
                    System.out.println("Число має бути від 2 до " + MAX_N + ".");
                    continue;
                }
                show(n);
            } catch (NumberFormatException e) {
                System.out.println("\"" + line + "\" не є цілим числом.");
            }
        }
    }

    private static void show(int n) {
        OptionalInt result = PrimeMaxOnes.findPrimeWithMaxOnes(n);
        if (result.isEmpty()) {
            System.out.println("Простих чисел, не більших за " + n + ", немає.");
            return;
        }
        int prime = result.getAsInt();
        int ones = PrimeMaxOnes.ONES.applyAsInt(prime);
        List<Integer> all = PrimeMaxOnes.primesWithOnes(n, ones);
        System.out.println("Шукане просте число: " + prime);
        System.out.println("Двійковий запис: " + Integer.toBinaryString(prime));
        System.out.println("Кількість одиниць: " + ones);
        System.out.println("Усі прості числа до " + n + " з такою кількістю одиниць: " + all
                + " (береться перше з них)");
    }
}
