package fr.azemouchi.tetravex;

import fr.azemouchi.tetravex.exceptions.CannotResolveException;
import fr.azemouchi.tetravex.utils.BinaryUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws CannotResolveException, IOException {

        System.out.print('\0');

        Scanner scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir les données de la partie sous la forme suivante : TTABCD" +
                "\n T : largeur du plateau, sur un entier 16 bits non signé (big endian, valeurs possibles : 1 à 65535) ;" +
                "\n A : chiffre de gauche de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9) ;" +
                "\n B : chiffre du haut de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9) ;" +
                "\n C : chiffre de droite de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9) ;" +
                "\n D : chiffre du bas de la première pièce, sur un octet non signé (valeurs possibles : 1 à 9)." +
                "\n\n"
        );

        StringBuilder entry = new StringBuilder();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("solve")) {
                break;
            } else {
                entry.append(line).append(System.lineSeparator());
            }
        }

        scanner.close();

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

        // Start with hex :
        // new TetravexSolver("03859491596966746159186834119096040482");

        Thread thread = new Thread(() -> {
            try {
                new TetravexSolver(bytes2);
            } catch (CannotResolveException e) {
                e.printStackTrace();
            }
        });

        thread.start();

    }

}
