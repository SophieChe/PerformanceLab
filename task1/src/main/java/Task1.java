import java.util.ArrayList;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите длину первого массива: ");
        int n1 = scanner.nextInt();
        System.out.print("Введите длину шага для первого массива: ");
        int m1 = scanner.nextInt();
        System.out.print("Введите длину второго массива: ");
        int n2 = scanner.nextInt();
        System.out.print("Введите длину шага для первого массива: ");
        int m2 = scanner.nextInt();

        ArrayList<Integer> result = new ArrayList<>();

        ArrayList<Integer> integers1 = fillArray(n1);
        ArrayList<Integer> integers2 = fillArray(n2);
        findWay(integers1,result,m1);
        findWay(integers2,result,m2);

        for (int e : result) {
            System.out.print(e);
        }
    }

    private static ArrayList<Integer> fillArray(int n){
        ArrayList<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list1.add(i + 1);
        }
        return list1;
    }

    private static void findWay(ArrayList<Integer> list, ArrayList<Integer> result, int m){
        int p = 0;

        do {
            result.add(list.get(p));
            p = p + m - 1;
            if (p > list.size() - 1) {
                p = p - (list.size() - 1);
                p = p - 1;
            }
        } while (p != 0);
    }
}