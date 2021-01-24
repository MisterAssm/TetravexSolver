package fr.azemouchi.tetravex.models;

import fr.azemouchi.tetravex.utils.ArrayUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Piece {

    public static final int VIDE;
    public static final int GAUCHE;
    public static final int HAUT;
    public static final int DROITE;
    public static final int BAS;

    static {
        VIDE = -1;
        GAUCHE = 0;
        HAUT = 1;
        DROITE = 2;
        BAS = 3;
    }

    private final int[] piece;
    private int index;

    public Piece(int gauche, int haut, int droite, int bas) {
        this.piece = new int[4];
        this.piece[GAUCHE] = gauche;
        this.piece[HAUT] = haut;
        this.piece[DROITE] = droite;
        this.piece[BAS] = bas;
    }

    public Piece(int[] piece) {
        this.piece = piece;
    }

    public int getPiece(int index) {
        return this.piece[index & 3];
    }

    public boolean match(Piece ideal) {
        final List<Integer> collect = IntStream.of(ArrayUtils.remove(ideal.getPiece(), DROITE)).boxed().collect(Collectors.toList());
        final List<Integer> collect1 = IntStream.of(ArrayUtils.remove(piece, DROITE)).boxed().collect(Collectors.toList());

        for (int i = 0; i < collect.size(); i++) {
            if (collect.get(i) != VIDE && !collect.get(i).equals(collect1.get(i))) {
                return false;
            }
        }

        return true;
    }

    public int[] getPiece() {
        return piece;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("+-----+\n");
        sb.append("|\\ ").append(this.piece[HAUT]).append(" /|\n");
        sb.append("|").append(this.piece[GAUCHE]).append(" X ").append(this.piece[DROITE]).append("|\n");
        sb.append("|/ ").append(this.piece[BAS]).append(" \\|\n");
        sb.append("+-----+\n");
        return sb.toString();
    }
}
