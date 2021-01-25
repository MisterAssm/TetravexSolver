package fr.azemouchi.tetravex;

import fr.azemouchi.tetravex.exceptions.CannotResolveException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException, CannotResolveException {

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

                entry.append(line)
                        .append(System.lineSeparator());
            }

        }

        String[] split = entry.toString().replaceAll("\\.", "").split("\\s+");
        byte[] bytes = new byte[split.length];

        IntStream.range(0, split.length).forEachOrdered(i -> bytes[i] = Byte.parseByte(split[i], 16));

        Thread thread = new Thread(() -> {
            try {
                new TetravexSolver(bytes);
            } catch (CannotResolveException e) {
                e.printStackTrace();
            }
        });

        thread.start();

    }

}
