package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Rook;
import game.Square;

class RookTest {
	
	Board board; 
	Rook whiteRook;
	Rook blackRook;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whiteRook = new Rook("Rook", true);
		blackRook = new Rook("Rook", false);
		board.setUpPiece(4, 4, whiteRook);
	}

	
	private boolean isValidMove(int row, int col) {
		boolean moveOk = false; 

		for (Square i: whiteRook.getPossibleMoves(board)) {
			if (i == board.getSquare(row, col)) {
				moveOk = true;
			}
		}
		return moveOk;
	}
	
	@Test
	void testMovesOnEmptyBoard() {
		for (int i = 0; i <= 7; i++) {
			assertTrue(isValidMove(i, 4));
			assertTrue(isValidMove(4, i));
		}
	}
	
	@Test
	void testJumpOverPiece() {
		board.setUpPiece(4, 6, blackRook);
		assertFalse(isValidMove(4, 7));
	}
	
	@Test
	void testCaptureOpponentPiece() {
		board.setUpPiece(4, 6, blackRook);	
		assertTrue(isValidMove(4, 6));
	}
	
	@Test
	void testCaptureOwnPiece() {
		Rook whiteRook2 = new Rook("Rook", true);
		board.setUpPiece(4, 6, whiteRook2);
		assertFalse(isValidMove(4, 6));
	}

}