package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.King;
import game.Pawn;
import game.Square;

class KingTest {
	
	Board board; 
	King whiteKing;
	King blackKing;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whiteKing = new King("King", true);
		blackKing = new King("King", false);
		board.setUpPiece(4, 4, whiteKing);
	}
	
	private boolean isValidMove(int row, int col) {
		boolean moveOk = false; 

		for (Square i: whiteKing.getPossibleMoves(board)) {
			if (i == board.getSquare(row, col)) {
				moveOk = true;
			}
		}
		return moveOk;
	}

	@Test
	void testMovesOnEmptyBoard() {
		assertTrue(isValidMove(4, 4));
		assertTrue(isValidMove(4+1, 4+1));
		assertTrue(isValidMove(4-1, 4-1));
		assertTrue(isValidMove(4+1, 4-1));
		assertTrue(isValidMove(4-1, 4+1));
		assertTrue(isValidMove(4+1, 4));
		assertTrue(isValidMove(4, 4+1));
		assertTrue(isValidMove(4-1, 4));
		assertTrue(isValidMove(4, 4-1));
	}
	
	@Test
	void testCaptureOpponentPiece() {
		board.setUpPiece(4, 5, blackKing);	
		assertTrue(isValidMove(4, 5));
	}
	
	@Test
	void testCaptureOwnPiece() {
		Pawn whitePawn = new Pawn("Pawn", true);
		board.setUpPiece(4, 5, whitePawn);

		assertFalse(isValidMove(4, 5));
	}

}