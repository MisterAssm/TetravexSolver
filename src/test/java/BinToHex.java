import java.io.*;
import java.util.Arrays;

public class BinToHex {

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

    public static byte[] convertToHex(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return convertToHex(inputStream);
    }

    public static byte[] convertToHex(String s) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(s.getBytes());
        return convertToHex(inputStream);
    }

    public static void main(String[] args) throws IOException {
        //display output to console

        System.out.println("\0");

        String entry = "000a 0405 0908 0306 0101 0008 0407 0507\n" +
                "0700 0507 0006 0506 0307 0808 0009 0002\n" +
                "0502 0003 0509 0407 0607 0802 0408 0805\n" +
                "0300 0100 0800 0303 0005 0605 0009 0909\n" +
                "0606 0007 0105 0707 0500 0507 0608 0000\n" +
                "0002 0804 0008 0004 0007 0807 0007 0709\n" +
                "0003 0305 0508 0104 0307 0100 0903 0607\n" +
                "0903 0503 0806 0409 0305 0703 0304 0703\n" +
                "0809 0507 0008 0402 0205 0202 0002 0502\n" +
                "0407 0304 0500 0606 0205 0903 0205 0702\n" +
                "0703 0206 0709 0300 0905 0509 0807 0008\n" +
                "0104 0306 0003 0600 0700 0407 0008 0807\n" +
                "0009 0009 0400 0103 0300 0202 0504 0007\n" +
                "0001 0408 0807 0400 0005 0509 0706 0001\n" +
                "0106 0100 0404 0108 0108 0300 0506 0101\n" +
                "0007 0003 0609 0402 0801 0009 0405 0006\n" +
                "0108 0504 0004 0009 0703 0908 0000 0605\n" +
                "0307 0100 0207 0608 0106 0001 0407 0502\n" +
                "0800 0008 0400 0008 0402 0402 0407 0404\n" +
                "0007 0903 0904 0607 0609 0406 0607 0007\n" +
                "0503 0600 0602 0108 0100 0700 0409 0006\n" +
                "0406 0201 0109 0007 0409 0004 0204 0005\n" +
                "0704 0209 0106 0404 0501 0709 0004 0009\n" +
                "0601 0207 0102 0408 0003 0805 0504 0604\n" +
                "0201 0601 0005 0801 0503 0905 0402 0104\n" +
                "0608 ";

        String[] split = entry.split("[\\s,]+");
        String[] split2 = new String[split.length * 2];

        for (int i = 0; i < split.length; i++) {
            String base = split[i];
            int half = base.length() % 2 == 0 ? base.length() / 2 : base.length() / 2 + 1;
            final String first = base.substring(0, half);
            final String second = base.substring(half);

            split2[i * 2] = first;
            split2[i * 2 + 1] = second;
        }


        byte[] bytes = new byte[split2.length];

        for (int i = 0; i < split2.length; i++) {
            bytes[i] = Byte.parseByte(split2[i], 16);
        }

        InputStream inputStream = new ByteArrayInputStream(bytes);

        final byte[] bytes2 = convertToHex(inputStream);

        System.out.println(Arrays.toString(bytes2));
        System.out.println("bytes2.length = " + bytes2.length);
        System.out.println("length 2 " + ((bytes2.length - 2) / 4));

    }
}
