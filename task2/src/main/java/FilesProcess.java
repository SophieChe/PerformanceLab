import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class FilesProcess {
    public static double[] parseNumbersFile(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Ошибка чтения файла: %s".formatted(path), e
            );
        }
    }
}
