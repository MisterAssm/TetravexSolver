package fr.azemouchi.tetravex.utils;

import java.io.IOException;
import java.io.InputStream;

public class BinaryUtils {

    public static String convertToBinary(String hex) {

        StringBuilder stringBuilderResult = new StringBuilder();
        StringBuilder stringBuilderValue = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < hex.length(); i++) {
            int n = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);

            stringBuilderValue.append((n < 10 ? "0" + n : n));

            if (stringBuilderValue.length() >= 2) {
                stringBuilder.append(stringBuilderValue.toString()).append(" ");

                if (getLengthWithoutSpaces(stringBuilder) >= 16) {
                    stringBuilderResult
                            .append(stringBuilder
                                    .append(" ".repeat(5))
                                    .append(".".repeat(16))
                                    .toString()
                            ).append(System.lineSeparator());
                    stringBuilder.setLength(0);
                }

                stringBuilderValue.setLength(0);
            }
        }

        if (stringBuilderValue.length() > 0) {
            stringBuilder.append(stringBuilderValue.toString()).append(" ");
            stringBuilderValue.setLength(0);
        }

        if (stringBuilder.length() > 0) {
            stringBuilderResult
                    .append(stringBuilder.append(" ".repeat(29 - stringBuilder.length())).toString())
                    .append(".".repeat(16 - getLengthWithoutSpaces(stringBuilder)))
                    .append(System.lineSeparator());
            stringBuilder.setLength(0);
        }

        return stringBuilderResult.toString();
    }

    private static int getLengthWithoutSpaces(StringBuilder stringBuilder) {
        return stringBuilder.toString().replaceAll("\\s+", "").length();
    }

}
