package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Knight;
import game.Square;

class KnightTest {
	
	Board board; 
	Knight whiteKnight;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whiteKnight = new Knight("Knight", true); 
		board.setUpPiece(4, 4, whiteKnight);	
	}
	
	private boolean isValidMove(int row, int col) {
		boolean moveOk = false; 

		for (Square i: whiteKnight.getPossibleMoves(board)) {
			if (i == board.getSquare(row, col)) {
				moveOk = true;
			}
		}
		return moveOk;
	}
	
	@Test
	void testMovesOnEmptyBoard() {
		assertTrue(isValidMove(4, 4));
		assertTrue(isValidMove(4+1, 4+2));
		assertTrue(isValidMove(4+2, 4+1));
		assertTrue(isValidMove(4-1, 4-2));
		assertTrue(isValidMove(4-2, 4-1));
		assertTrue(isValidMove(4+1, 4-2));
		assertTrue(isValidMove(4+2, 4-1));
		assertTrue(isValidMove(4-1, 4+2));
		assertTrue(isValidMove(4-2, 4+1));
	}

	@Test
	void testCaptureOwnPiece() {
		Knight whiteKnight2 = new Knight("Knight", true);
		board.setUpPiece(3, 2, whiteKnight2);
		
		assertFalse(isValidMove(3, 2));
	}
	
	@Test
	void testCaptureOpponentPiece() {
		Knight blackKnight = new Knight("Knight", false);	
		board.setUpPiece(3, 2, blackKnight);
		
		assertTrue(isValidMove(3, 2));	
	}
	
}
