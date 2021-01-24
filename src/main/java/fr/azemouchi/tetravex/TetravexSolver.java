package fr.azemouchi.tetravex;

import fr.azemouchi.tetravex.exceptions.CannotResolveException;
import fr.azemouchi.tetravex.manager.Board;
import fr.azemouchi.tetravex.manager.Solver;
import fr.azemouchi.tetravex.models.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class TetravexSolver {

    public TetravexSolver(String entry) throws CannotResolveException {

        final String[] split = entry.substring(2).split("(?<=\\G.{4})");

        if (split[split.length - 1].length() != 4) {
            throw new CannotResolveException("No solution is possible for this game.");
        }

        Piece[] pieces = new Piece[split.length];

        IntStream.range(0, split.length).forEachOrdered(i -> {
            String s = split[i];
            pieces[i] = new Piece(Integer.parseInt(s.substring(0, 1)), Integer.parseInt(s.substring(1, 2)), Integer.parseInt(s.substring(2, 3)), Integer.parseInt(s.substring(3, 4)));
        });

        final short waist = Short.parseShort(entry.substring(0, 2), 16);
        solve(waist, pieces);
    }

    public TetravexSolver(byte[] entry) throws CannotResolveException {

        if ((entry.length - 2) % 4 != 0) {
            throw new CannotResolveException("No solution is possible for this game.");
        }

        byte[] modifiedArray = Arrays.copyOfRange(entry, 2, entry.length);
        Piece[] pieces = new Piece[modifiedArray.length / 4];

        int j = 0;
        for (int i = 0; i < modifiedArray.length; i++) {
            if (i % 4 != 0) continue;

            byte b = modifiedArray[i];
            byte b1 = modifiedArray[i + 1];
            byte b2 = modifiedArray[i + 2];
            byte b3 = modifiedArray[i + 3];

            pieces[j] = new Piece(b, b1, b2, b3);
            j++;
        }

        final short waist = (short) (((short) entry[0]) + ((short) entry[1]));
        solve(waist, pieces);
    }

    private Short[][] getBoard(int value) {
        List<List<Short>> boardList = new ArrayList<>();

        for (int i = 0; i < value; i++) {
            List<Short> list = new ArrayList<>(value);
            for (int j = 0; j < value; j++) {
                list.add((short) -1);
            }
            boardList.add(list);
        }


        Short[][] array;
        Short[] blankArray = new Short[0];
        array = boardList.stream().map(shorts -> shorts.toArray(blankArray)).toArray(Short[][]::new);

        return array;
    }

    private void solve(short waist, Piece[] pieces) throws CannotResolveException {
        Board board = new Board(pieces, waist, waist, getBoard(waist));

        Solver solver = new Solver(board);
        solver.solve();

        if (Objects.isNull(solver.getSolution())) {
            throw new CannotResolveException("No solution is possible for this game.");
        } else {
            System.out.println("Solution : \n" + solver.getSolution());
        }
    }
}
