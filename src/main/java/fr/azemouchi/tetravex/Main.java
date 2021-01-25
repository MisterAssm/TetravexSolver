package fr.azemouchi.tetravex;

import fr.azemouchi.tetravex.utils.BinaryUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.print('\0');

        System.out.print("Veuillez saisir les données de la partie sous la forme suivante : TTABCD" +
                "\n T : largeur du plateau, sur un entier 16 bits non signé (big endian, valeurs possibles : 1 à 65535) ;" +
                "\n A : chiffre de gauche de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9) ;" +
                "\n B : chiffre du haut de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9) ;" +
                "\n C : chiffre de droite de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9) ;" +
                "\n D : chiffre du bas de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9)." +
                "\n\n"
        );

        StringBuilder entry = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            String line;
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                if (line.isEmpty()) break;

                entry.append(line).append("\n");
            }

        }

        String[] split = entry.toString().split("\\s+");
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

        final byte[] bytes2 = BinaryUtils.convertToHex(inputStream);

        System.out.println("bytes2 = " + Arrays.toString(bytes2));

        // Start with hex :
        // new TetravexSolver("03859491596966746159186834119096040482");

        /*
        Thread thread = new Thread(() -> {
            try {
                new TetravexSolver(bytes2);
            } catch (CannotResolveException e) {
                e.printStackTrace();
            }
        });

        thread.start();*/

    }

}
