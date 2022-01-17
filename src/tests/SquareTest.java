package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Pawn;
import game.Piece;
import game.Square;

class SquareTest {
	
	Board board; 
	Square square; 
	Piece piece;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		square = board.getSquare(0, 0);
		piece = new Pawn("Pawn", true);
	}

	@Test
	void testSetPiece() {
		assertEquals(square.getPiece(), null);
		
		square.setPiece(piece);
		assertEquals(square.getPiece(), piece);
	}
	
	@Test
	void testRemovePiece() {
		square.setPiece(piece);
		square.removePieceFromSquare();
		assertEquals(square.getPiece(), null);
	}

}
