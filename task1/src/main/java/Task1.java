import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Task1 {
    private static final int THREADS = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите длину первого массива: ");
        int n1 = checkInput(scanner);
        System.out.print("Введите длину шага для первого массива: ");
        int m1 = checkInput(scanner);
        System.out.print("Введите длину второго массива: ");
        int n2 = checkInput(scanner);
        System.out.print("Введите длину шага для второго массива: ");
        int m2 = checkInput(scanner);

        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

        List<Integer> result = new ArrayList<>();

        List<Integer> integers1 = fillArray(n1);
        List<Integer> integers2 = fillArray(n2);

        Future<List<Integer>> result1 = executorService.submit(() -> findWay(integers1, m1));
        Future<List<Integer>> result2 = executorService.submit(() -> findWay(integers2, m2));

        try {
            result.addAll(result1.get());
            result.addAll(result2.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Поток был прерван.", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Ошибка в задаче.", e.getCause());
        } finally {
            executorService.shutdown();
        }

        for (int e : result) {
            System.out.print(e);
        }
    }

    private static List<Integer> fillArray(int n) {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list1.add(i + 1);
        }
        return list1;
    }

    private static List<Integer> findWay(List<Integer> list, int m) {
        List<Integer> result = new ArrayList<>();
        int p = 0;

        do {
            result.add(list.get(p));
            p = p + m - 1;
            if (p > list.size() - 1) {
                p = p - (list.size() - 1);
                p = p - 1;
            }
        } while (p != 0);

        return result;
    }

    private static int checkInput(Scanner scanner) {
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Введите целое число.");
                scanner.next();
            }
            int value = scanner.nextInt();
            if (value <= 0) {
                System.out.println("Ошибка! Число должно быть больше 0.");
            } else {
                return value;
            }
        }
    }
}