package fr.azemouchi.tetravex.manager;

import fr.azemouchi.tetravex.models.Move;
import fr.azemouchi.tetravex.models.Piece;
import fr.azemouchi.tetravex.utils.BinaryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fr.azemouchi.tetravex.models.Piece.*;

public class Board {

    private Piece[] pieces;

    private boolean[] placed;
    private short width;
    private short height;
    private int placedCount;
    private Short[][] board;
    private int depth;
    private String entry;

    private Board() {
    }

    public Board(Piece[] pieces, short width, short height, Short[][] board) {
        this.pieces = pieces;
        this.width = width;
        this.height = height;
        this.board = board;
        this.placedCount = 0;
        this.placed = new boolean[width * height];
        this.depth = 0;
        this.entry = "";

        IntStream.range(0, pieces.length).forEachOrdered(i -> pieces[i].setIndex(i));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[x][y] >= 0) {
                    ++placedCount;
                    placed[board[x][y]] = true;
                }
            }
        }
    }

    public boolean exists(int x, int y) {
        return !(x < 0 || y < 0 || x >= width || y >= height);
    }

    public boolean isAlone(int x, int y) {
        return (
                (!exists(x - 1, y) || board[y][x - 1] == VIDE) &&
                        (!exists(x + 1, y) || board[y][x + 1] == VIDE) &&
                        (!exists(x, y - 1) || board[y - 1][x] == VIDE) &&
                        (!exists(x, y + 1) || board[y + 1][x] == VIDE)
        );
    }

    public boolean isPiece(int x, int y) {
        return (exists(x, y) && board[y][x] != VIDE);
    }

    private Piece getIdeal(int x, int y) {
        Piece ideal = new Piece(VIDE, VIDE, VIDE, VIDE);

        if (isPiece(x - 1, y)) {
            ideal.getPiece()[GAUCHE] = pieces[board[y][x - 1]].getPiece()[DROITE];
        }
        if (isPiece(x + 1, y)) {
            ideal.getPiece()[DROITE] = pieces[board[y][x + 1]].getPiece()[GAUCHE];
        }
        if (isPiece(x, y - 1)) {
            ideal.getPiece()[HAUT] = pieces[board[y - 1][x]].getPiece()[BAS];
        }
        if (isPiece(x, y + 1)) {
            ideal.getPiece()[BAS] = pieces[board[y + 1][x]].getPiece()[HAUT];
        }

        return ideal;
    }

    public Board copy() {
        Board copy = new Board();
        copy.width = width;
        copy.height = height;
        copy.pieces = pieces;
        copy.placedCount = placedCount;
        copy.placed = Arrays.copyOf(placed, placed.length);
        copy.board = new Short[height][];
        copy.entry = entry;

        for (int i = 0; i < height; i++) {
            copy.board[i] = Arrays.copyOf(board[i], board[i].length);
        }

        return copy;
    }

    public Iterator<Move> getMoves() {
        List<Move> moves = new ArrayList<>();

        if (placedCount == 0)
            return IntStream.range(0, placed.length).mapToObj(i -> new Move(i, 0, 0)).collect(Collectors.toList()).iterator();

        int minMatches = width * height + 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x] == VIDE && !isAlone(x, y)) {
                    List<Piece> matches = new ArrayList<>();

                    for (int i = 0; i < placed.length; i++) {
                        if (!placed[i] && pieces[i].match(getIdeal(x, y))) {
                            matches.add(pieces[i]);
                        }
                    }

                    if (matches.size() < minMatches) {
                        minMatches = matches.size();
                        moves.clear();

                        for (Piece p : matches) {
                            moves.add(new Move(p.getIndex(), x, y));
                        }
                    }
                }
            }
        }

        return moves.iterator();
    }

    public void addMove(Move move) {
        placed[move.getIndex()] = true;
        board[move.getHeight()][move.getWidth()] = (short) move.getIndex();
        ++placedCount;
    }

    public boolean isSolution() {
        return (placedCount == (width * height));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder outputFormat = new StringBuilder(/*entry.substring(0, 2)*/);

        for (int y = 0; y < height; y++) {
            stringBuilder.append("+-----"
                    .repeat(Math.max(0, width)))
                    .append("+")
                    .append(System.lineSeparator());

            for (int x = 0; x < width; x++) {
                stringBuilder.append("|");
                final Short aShort = board[y][x];

                if (aShort >= 0) {
                    stringBuilder.append("\\ ")
                            .append(pieces[aShort].getPiece()[HAUT])
                            .append(" /");

                    Arrays.stream(pieces[aShort].getPiece()).forEachOrdered(outputFormat::append);

                } else stringBuilder.append(" ".repeat(Math.max(0, 5)));
            }

            stringBuilder.append("|")
                    .append(System.lineSeparator());

            for (int x = 0; x < width; x++) {
                stringBuilder.append("|");
                final Short aShort = board[y][x];
                if (aShort >= 0) {
                    Piece p = pieces[aShort];
                    stringBuilder.append(p.getPiece()[GAUCHE])
                            .append(" X ")
                            .append(p.getPiece()[DROITE]);
                } else stringBuilder.append(" ".repeat(Math.max(0, 5)));
            }

            stringBuilder.append("|")
                    .append(System.lineSeparator());

            for (int x = 0; x < width; x++) {
                stringBuilder.append("|");

                final Short aShort = board[y][x];
                if (aShort >= 0) stringBuilder.append("/ ")
                        .append(pieces[aShort].getPiece()[BAS])
                        .append(" \\");
                else stringBuilder.append(" ".repeat(Math.max(0, 5)));
            }

            stringBuilder.append("|")
                    .append(System.lineSeparator());
        }

        stringBuilder.append("+-----".repeat(Math.max(0, width)))
                .append("+")
                .append(System.lineSeparator().repeat(2))
                .append("<=> Output: ").append(System.lineSeparator().repeat(2))
                .append(BinaryUtils.convertToBinary(outputFormat.toString()));

        return stringBuilder.toString();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
