import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    public void testCheckPointsOutput() {
        double[] ellipse = {0, 0, 5, 3};
        List<double[]> points = List.of(
                new double[]{0, 3},
                new double[]{0, 0},
                new double[]{6, 0}
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Task2.checkPoints(ellipse, points);

        String expectedOutput = "0\n1\n2\n";
        assertEquals(expectedOutput, outContent.toString());

        System.setOut(System.out);
    }
}
