package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Bishop;
import game.Board;
import game.Square;

class BishopTest {
	
	Board board; 
	Bishop whiteBishop;
	Bishop blackBishop;
	int x, y;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whiteBishop = new Bishop("Bishop", true);
		blackBishop = new Bishop("Bishop", false);
		
		x = 4; y = 4; 
		board.setUpPiece(x, y, whiteBishop);
	}

	private boolean isValidMove(int row, int col) {
		boolean moveOk = false; 

		for (Square i: whiteBishop.getPossibleMoves(board)) {
			if (i == board.getSquare(row, col)) {
				moveOk = true;
			}
		}
		return moveOk;
	}
	
	@Test
	void testMovesOnEmptyBoard() {
		int i = x; 
		int j = y;
		// also checks for starting position
		while (i <= 7 && j <= 7) {
			assertTrue(isValidMove(i, j));
			i++; j++;
		}
		
		i = x-1; j = y-1; 
		while (i >= 0 && j >= 0) {
			assertTrue(isValidMove(i, j));
			i--; j--;
		}
		
		i = x-1; j = y+1; 
		while (i >= 0 && j <= 7) {
			assertTrue(isValidMove(i, j));
			i--; j++;
		}
		
		i = x+1; j = y-1; 
		while (i <= 0 && j >= 0) {
			assertTrue(isValidMove(i, j));
			i++; j--;
		}
	}
	
	@Test
	void testJumpOverPiece() {
		board.setUpPiece(x+1, y+1, blackBishop);
		assertFalse(isValidMove(x+2, y+2));
	}
	
	@Test
	void testCaptureOpponentPiece() {
		board.setUpPiece(x+1, y+1, blackBishop);
		assertTrue(isValidMove(x+1, y+1));
	}
	
	@Test
	void testCaptureOwnPiece() {
		Bishop whiteBishop2 = new Bishop("Bishop", true);
		board.setUpPiece(x+1, y+1, whiteBishop2);
		assertFalse(isValidMove(x+1, y+1));
	}

}
