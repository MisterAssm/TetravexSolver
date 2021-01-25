import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ScannerTest {

    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            StringBuilder entry = new StringBuilder();
            String line;
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                if (line.isEmpty()) break;

                entry.append(line).append("\n");
            }

        }
//        System.out.println(input);
    }


}
