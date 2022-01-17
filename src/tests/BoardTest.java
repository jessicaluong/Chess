package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Board;
import game.Pawn;
import game.Piece;

class BoardTest {
	
	Board board; 

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
	}
	
	@Test
	public void testsetUpPiece() {
		Piece piece = new Pawn("Pawn", true);
		board.setUpPiece(0, 0, piece);
		assertEquals(board.getWhitePieces().size(), 1);
		assertTrue(board.getSquare(0, 0).getPiece().equals(piece));
	}

	@Test
	public void testInitializePieces() {
		assertTrue(board.getBlackPieces().isEmpty());
		assertTrue(board.getWhitePieces().isEmpty());
		board.initializePieces();
		assertEquals(board.getBlackPieces().size(), 16);
		assertEquals(board.getWhitePieces().size(), 16);
	}
	
	@Test 
	public void testRemovePieceFromBoard() {
		board.initializePieces();
		Iterator<Piece> iterator = board.getWhitePieces().iterator(); 
		Piece piece = iterator.next();
		board.removePieceFromBoard(piece);
		assertEquals(board.getWhitePieces().size(), 15);
	}
	
	

}
