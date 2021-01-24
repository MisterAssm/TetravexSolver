package fr.azemouchi.tetravex.manager;

import fr.azemouchi.tetravex.manager.Board;
import fr.azemouchi.tetravex.models.Move;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;

public class Solver {

    private final Board initial;
    private Board solution;

    public Solver(Board initial) {
        this.initial = initial;
    }

    public void solve() {
        ArrayDeque<Board> states = new ArrayDeque<>();

        solution = null;
        initial.setDepth(0);
        states.offerFirst(initial);

        while (!states.isEmpty()) {
            Board current = states.pollLast();

            if (current.isSolution()) {
                solution = current;
                break;

            } else if (current.getDepth() < Integer.MAX_VALUE) {

                Iterator<Move> moves = current.getMoves();

                if (Objects.isNull(moves)) continue;

                while (moves.hasNext()) {
                    Move move = moves.next();
                    Board next = current.copy();

                    next.addMove(move);
                    next.setDepth(current.getDepth() + 1);

                    states.offerFirst(next);
                }
            }
        }
    }

    public Board getInitial() {
        return initial;
    }

    public Board getSolution() {
        return solution;
    }
}
