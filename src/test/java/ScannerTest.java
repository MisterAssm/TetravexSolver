import java.util.Arrays;
import java.util.Scanner;

public class ScannerTest {

    public static void main(String[] args) {

        StringBuilder entry = new StringBuilder();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equalsIgnoreCase("solve")) {
                break;
            } else {
                entry.append(scanner.nextLine()).append(System.lineSeparator());
            }
        }

        scanner.close();
        System.out.println(entry);
    }


}
