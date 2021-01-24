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

            if (stringBuilderValue.length() >= 4) {
                stringBuilder.append(stringBuilderValue.toString()).append(" ");

                if (stringBuilder.length() >= 40) {
                    stringBuilderResult.append(stringBuilder.toString()).append(System.lineSeparator());
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
            stringBuilderResult.append(stringBuilder.toString()).append(System.lineSeparator());
            stringBuilder.setLength(0);
        }

        return stringBuilderResult.toString();
    }

    public static byte[] convertToHex(InputStream inputStream) throws IOException {

        int value;
        StringBuilder stringBuilderHex = new StringBuilder();

        int n = 0;
        int max = 1000000;

        while ((value = inputStream.read()) != -1 && n++ < max) {
            stringBuilderHex.append("0x")
                    .append(String.format("%02X ", value));
        }

        String[] split = stringBuilderHex.toString().split("\\s+");

        byte[] array = new byte[split.length];

        for (int i = 0; i < split.length; i++) {
            array[i] = (byte) (short) Short.decode(split[i]);
        }

        inputStream.close();

        return array;
    }

}
