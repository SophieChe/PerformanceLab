import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Task2 {
    public static void main(String[] args) {
        try {
            filesProcess(args);
        } catch (Exception e) {
            log.error("Ошибка выполнения программы", e);
            System.exit(1);
        }
    }

    private static void filesProcess(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Для работы программы необходимы 2 файла");
        }

        Path ellipseDataPath = Paths.get(args[0]);
        Path pointsDataPath = Paths.get(args[1]);

        double[] ellipseData = FilesProcess.parseNumbersFile(ellipseDataPath);
        if (ellipseData.length != 4) {
            throw new IllegalArgumentException(
                    "Файл с эллипсом должен содержать ровно 4 числа, а не %d"
                            .formatted(ellipseData.length)
            );
        }

        double[] pointsDataFlat = FilesProcess.parseNumbersFile(pointsDataPath);
        if (pointsDataFlat.length % 2 != 0) {
            throw new IllegalArgumentException(
                    "Файл с точками должен содержать чётное количество чисел (x, y пары), а не %d"
                            .formatted(pointsDataFlat.length)
            );
        }

        List<double[]> points = doPointsList(pointsDataFlat);
        checkPoints(ellipseData, points);
    }

    protected static void checkPoints(double[] ellipseData, List<double[]> points) {
        double centerX = ellipseData[0];
        double centerY = ellipseData[1];
        double a = ellipseData[2];
        double b = ellipseData[3];

        for (double[] point : points) {
            double result = Math.sqrt(point[0] - centerX) / Math.sqrt(a)
                    + Math.sqrt(point[1] - centerY) / Math.sqrt(b);
            if (result == 1.0) {
                System.out.print("0" + "\n");
            } else if (result < 1.0) {
                System.out.print("1" + "\n");
            } else {
                System.out.print("2" + "\n");
            }
        }
    }

    private static List<double[]> doPointsList(double[] pointsDataFlat) {
        List<double[]> points = new ArrayList<>();

        for (int i = 0; i < pointsDataFlat.length; i += 2) {
            points.add(new double[]{pointsDataFlat[i], pointsDataFlat[i + 1]});
        }
        return points;
    }
}
