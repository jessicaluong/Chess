package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Pawn;
import game.Square;

class PawnTest {

	Board board; 
	Pawn whitePawn;
	Pawn blackPawn;
	
	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whitePawn = new Pawn("Pawn", true);
		blackPawn = new Pawn("Pawn", false);
	}
	
	private boolean isValidMove(int row, int col, String color) {
		boolean moveOk = false; 

		if (color.equals("white")) {
			for (Square i: whitePawn.getPossibleMoves(board)) {
				if (i == board.getSquare(row, col)) {
					moveOk = true;
				}
			}
		} else if (color.equals("black")) {
			for (Square i: blackPawn.getPossibleMoves(board)) {
				if (i == board.getSquare(row, col)) {
					moveOk = true;
				}
			}
		}

		return moveOk;
	}

	@Test
	void testWhitePawnFirstMove() {
		board.setUpPiece(6, 1, whitePawn);	
		
		assertTrue(isValidMove(6, 1, "white"));
		// move one space 
		assertTrue(isValidMove(5, 1, "white"));
		// move two spaces 
		assertTrue(isValidMove(4, 1, "white"));
		// move three spaces
		assertFalse(isValidMove(3, 1, "white"));
		// move backwards
		assertFalse(isValidMove(7, 1, "white"));
		
		// cannot move diagonal without capturing
		assertFalse(isValidMove(5, 0, "white"));
		assertFalse(isValidMove(5, 2, "white"));

		// cannot jump over another piece
		board.setUpPiece(5, 1, blackPawn);
		assertFalse(isValidMove(4, 1, "white"));	
	}
	
	@Test
	void testBlackPawnFirstMove() {
		board.setUpPiece(1, 1, blackPawn);	
		
		assertTrue(isValidMove(1, 1, "black"));
		// move one space 
		assertTrue(isValidMove(2, 1, "black"));
		// move two spaces 
		assertTrue(isValidMove(3, 1, "black"));
		// move three spaces
		assertFalse(isValidMove(4, 1, "black"));
		// move backwards
		assertFalse(isValidMove(0, 1, "black"));

		// cannot move diagonal without capturing
		assertFalse(isValidMove(2, 0, "black"));
		assertFalse(isValidMove(2, 2, "black"));

		// cannot jump over another piece
		board.setUpPiece(2, 1, whitePawn);
		assertFalse(isValidMove(3, 1, "black"));
	}
	
	@Test
	void testWhitePawnMiddleMoves() {
		board.setUpPiece(4, 4, whitePawn);
		// move one space
		assertTrue(isValidMove(3, 4, "white"));
		// move two spaces
		assertFalse(isValidMove(2, 4, "white"));
	}
	
	@Test
	void testBlackPawnMiddleMoves() {
		board.setUpPiece(4, 4, blackPawn);	
		// move one space
		assertTrue(isValidMove(5, 4, "black"));
		// move two spaces
		assertFalse(isValidMove(6, 4, "black"));
	}
	
	@Test
	void testWhitePawnCaptureOpponent() {
		Pawn blackPawn2 = new Pawn("Pawn", false);
		board.setUpPiece(4, 4, whitePawn);	
		
		board.setUpPiece(3, 3, blackPawn);	
		board.setUpPiece(3, 5, blackPawn2);	
		assertTrue(isValidMove(3, 3, "white"));
		assertTrue(isValidMove(3, 5, "white"));
	}
	
	@Test
	void testBlackPawnCaptureOpponent() {
		Pawn whitePawn2 = new Pawn("Pawn", true);
		board.setUpPiece(4, 4, blackPawn);	
		
		board.setUpPiece(5, 3, whitePawn);	
		board.setUpPiece(5, 5, whitePawn2);
		assertTrue(isValidMove(5, 3, "black"));
		assertTrue(isValidMove(5, 5, "black"));
	}
	
	@Test
	void testCaptureOwnPiece() {
		Pawn whitePawn2 = new Pawn("Pawn", true);
		board.setUpPiece(4, 4, whitePawn);	
		board.setUpPiece(3, 3, whitePawn2);
		
		assertFalse(isValidMove(3, 3, "white"));
	}

}