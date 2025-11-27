package game;

public class Game {

    private Board board;

    public Game() {
        board = new Board();
    }

    public String toString()
    {
        return board.toString();
    }

    public Board getBoard() {
        return board;
    }
}
