public class AsciiConverter {

    private static String asciiToHex(String asciiValue){
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (char aChar : chars) {
            hex.append(Integer.toHexString(aChar));
        }
        return hex.toString();
    }

    public static void main(String[] args) {

    }

}
