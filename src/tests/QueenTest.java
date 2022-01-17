package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Queen;
import game.Square;

class QueenTest {
	
	Board board; 
	Queen whiteQueen;
	Queen blackQueen;
	int x, y;


	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whiteQueen = new Queen("Queen", true);
		blackQueen = new Queen("Queen", false);
		
		x = 4; y = 4; 
		board.setUpPiece(x, y, whiteQueen);
	}
	
	private boolean isValidMove(int row, int col) {
		boolean moveOk = false; 

		for (Square i: whiteQueen.getPossibleMoves(board)) {
			if (i == board.getSquare(row, col)) {
				moveOk = true;
			}
		}
		return moveOk;
	}

	@Test
	void testMovesOnEmptyBoard() {
		for (int i = 0; i <= 7; i++) {
			assertTrue(isValidMove(i, y));
			assertTrue(isValidMove(x, i));
		}
		
		int i = x+1; 
		int j = y+1;
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
		board.setUpPiece(x+1, y+1, blackQueen);
		assertFalse(isValidMove(x+2, y+2));
	}
	
	@Test
	void testCaptureOpponentPiece() {
		board.setUpPiece(x+1, y+1, blackQueen);
		assertTrue(isValidMove(x+1, y+1));
	}
	
	@Test
	void testCaptureOwnPiece() {
		Queen whiteQueen2 = new Queen("Queen", true);
		board.setUpPiece(x+1, y+1, whiteQueen2);
		assertFalse(isValidMove(x+1, y+1));
	}
	
	
}