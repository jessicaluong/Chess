package game;

public class Game {

	public static Board board;
	
	public static void main(String[] args) {
		board = new Board();
		board.initializePieces();
	}
}