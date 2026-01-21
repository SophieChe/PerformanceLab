import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.KeyTestsFileDto;
import dto.KeyValueFileDto;
import dto.TestDto;
import dto.ValueDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Slf4j
public class Task3 {
    public static void main(String[] args) {
        try {
            filesProcess(args);
        } catch (Exception e) {
            log.error("Ошибка выполнения программы", e);
            System.exit(1);
        }
    }

    public static void filesProcess(String[] args) throws IOException {

        if (args.length != 3) {
            throw new IllegalArgumentException("Для работы программы необходимы файлы: values.json, tests.json, report.json");
        }

        Path valuesFile = Paths.get(args[0]);
        Path testsFile = Paths.get(args[1]);
        Path reportFile = Paths.get(args[2]);

        checkResources(List.of(valuesFile, testsFile), reportFile);



        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        KeyValueFileDto keyValueFileDto = objectMapper.readValue(valuesFile.toFile(), KeyValueFileDto.class);
        KeyTestsFileDto keyTestsFileDto = objectMapper.readValue(testsFile.toFile(), KeyTestsFileDto.class);

        Map<Integer, String> valuesMap = fillMap(keyValueFileDto);

        for (TestDto test : keyTestsFileDto.tests()) {
            fillValues(test, valuesMap);
        }

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(reportFile.toFile(), keyTestsFileDto);
    }

    private static Map<Integer, String> fillMap(KeyValueFileDto keyValueFileDto) {
        Map<Integer, String> valuesMap = new HashMap<>();

        for (ValueDto value : keyValueFileDto.values()) {
            if (valuesMap.containsKey(value.id()) ) {
                throw new IllegalStateException("Дублирующий id %d в values.json: ".formatted(value.id()));
            } else if(value.value().isBlank()){
                throw new IllegalStateException("Нет value в values.json для id %d: ".formatted(value.id()));
            }
            valuesMap.put(value.id(), value.value());
        }
        return valuesMap;
    }

    private static void fillValues(TestDto root, Map<Integer, String> valuesMap) {
        Queue<TestDto> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TestDto current = queue.poll();
            if (valuesMap.containsKey(current.getId())) {
                current.setValue(valuesMap.get(current.getId()));
            }
            if (current.getValues() != null) {
                queue.addAll(current.getValues());
            }
        }
    }

    private static void checkResources(List<Path> paths, Path reportFile) {
        for (Path path : paths) {
            if (!Files.exists(path)) {
                throw new IllegalStateException("Файл не существует: " + path);
            }
            if (!Files.isRegularFile(path)) {
                throw new IllegalStateException("Путь не является файлом: " + path);
            }
            if (!Files.isReadable(path)) {
                throw new IllegalStateException("Файл недоступен для чтения: " + path);
            }
        }
        if (!Files.exists(reportFile)) {
            try {
                Files.createFile(reportFile);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать report.json: " + reportFile, e);
            }
        }
    }
}