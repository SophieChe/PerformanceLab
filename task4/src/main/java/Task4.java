import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Task4 {

    private static final int MAX_STEP = 20;

    public static void main(String[] args) {
        try {
            Path path = getPath(args);
            checkFile(path);
            List<Integer> list = readFile(path);
            Collections.sort(list);
            System.out.println(countSteps(list));
        } catch (NoSuchFileException e) {
            log.error("Файл не найден: {}", e.getFile());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("Ошибка чтения файла: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Неожиданная ошибка", e);
        }
    }

    private static List<Integer> readFile(Path path) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла: " + e.getMessage(), e);
        }
        return list;
    }

    private static Path getPath(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Для работы программы необходим 1 файл.");
        }
        return Paths.get(args[0]);
    }

    private static void checkFile(Path path) throws NoSuchFileException {
        if (!Files.exists(path)) {
            throw new NoSuchFileException("Файл не найден: " + path);
        }
    }

    private static String countSteps(List<Integer> list) {
        int median = list.get(list.size() / 2 - 1);
        int medianPosition = list.size() / 2 - 1;
        int step = 1;
        int p = 0;

        while (step <= MAX_STEP) {
            while (list.get(p) != median) {
                if (p < medianPosition) {
                    list.set(p, list.get(p) + 1);
                } else if (p > medianPosition) {
                    list.set(p, list.get(p) - 1);
                }
                if (step <= MAX_STEP && list.get(list.size() - 1) == median && p == list.size() - 1) {
                    return String.valueOf(step);
                }
                step++;
            }
            p++;
        }
        return "20 ходов недостаточно для приведения всех элементов массива к одному числу";
    }
}
