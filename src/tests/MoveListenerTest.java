package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.event.MouseEvent;

import game.Board;
import game.MoveListener;
import game.Pawn;
import game.Piece;
import game.Square;
import optionpane.BishopMockOptionPane;
import optionpane.KnightMockOptionPane;
import optionpane.QueenMockOptionPane;
import optionpane.RookMockOptionPane;

class MoveListenerTest {

	Board board; 
	Pawn whitePawn;
	Pawn blackPawn;
	MoveListener listener;

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		whitePawn = new Pawn("Pawn", true);
		blackPawn = new Pawn("Pawn", false);
		listener = board.getMoveListener();
	}
	
	/**
	 * Simulates a mouse click
	 */
	private static void click(Component source) {
		MouseEvent press, release, click;
		
		press = new MouseEvent(source, MouseEvent.MOUSE_PRESSED, 1, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		release = new MouseEvent(source, MouseEvent.MOUSE_RELEASED, 2, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		click = new MouseEvent(source, MouseEvent.MOUSE_CLICKED, 3, 0, 0, 0, 0, false, MouseEvent.BUTTON1);

		source.dispatchEvent(press);
		source.dispatchEvent(release);
		source.dispatchEvent(click);
	}
	
	/**
	 * Simulates a mouse click on a square on the board
	 * @param x is the row of the square
	 * @param y is the column of the square 
	 */
	private void clickSquare(int x, int y) {
		Square square = board.getSquare(x, y);
		click(square);
	}
	
	@Test
	void moveBackToInitialSquare() {
		board.setUpPiece(6, 0, whitePawn);
		
		clickSquare(6, 0);
		clickSquare(6, 0);
		
		assertEquals(board.getSquare(6, 0).getPiece(), whitePawn);
	}
	
	@Test
	void testMovePieceToEmptySquare() {
		board.setUpPiece(6, 0, whitePawn);
		
		clickSquare(6, 0);
		clickSquare(5, 0);
		
		assertFalse(board.getSquare(6, 0).hasPiece());
		assertEquals(board.getSquare(5, 0).getPiece(), whitePawn);
	}
	
	@Test
	void testCapturePiece() {
		board.setUpPiece(6, 0, whitePawn);
		
		board.setUpPiece(5, 1, blackPawn);

		// capturing black piece 
		clickSquare(6, 0);
		clickSquare(5, 1);
		
		assertEquals(board.getWhitePieces().size(), 1);
		assertTrue(board.getBlackPieces().isEmpty());
		assertEquals(board.getSquare(5, 1).getPiece(), whitePawn);
	}
	
	@Test
	void testSwapTurns() {		
		board.setUpPiece(1, 0, blackPawn);
		
		// cannot move black piece first 
		clickSquare(1, 0);
		clickSquare(2, 0);
		
		assertFalse(board.getSquare(2, 0).hasPiece());

		board.setUpPiece(6, 0, whitePawn);
		
		// move white piece first
		clickSquare(6, 0);
		clickSquare(5, 0);
		
		// then move black piece
		clickSquare(1, 0);
		clickSquare(2, 0);

		assertEquals(board.getSquare(2, 0).getPiece(), blackPawn);
	}
	
	private Piece moveForwards(int row, int col) {
		if (row == 1) {
			clickSquare(1, col);
			clickSquare(0, col);
			return board.getSquare(0, col).getPiece();

		} else if (row == 6){
			clickSquare(6, col);
			clickSquare(7, col);
			return board.getSquare(7, col).getPiece();
		}
		return null;
	}

	@Test
	void testPawnPromotion() {
		
		Pawn whitePawn2 = new Pawn("Pawn", true);		
		Pawn whitePawn3 = new Pawn("Pawn", true);		
		Pawn whitePawn4 = new Pawn("Pawn", true);
		
		Pawn blackPawn2 = new Pawn("Pawn", false);		
		Pawn blackPawn3 = new Pawn("Pawn", false);		
		Pawn blackPawn4 = new Pawn("Pawn", false);
		
		board.setUpPiece(1, 0, whitePawn);
		board.setUpPiece(1, 1, whitePawn2);
		board.setUpPiece(1, 2, whitePawn3);
		board.setUpPiece(1, 3, whitePawn4);
		
		board.setUpPiece(6, 0, blackPawn);
		board.setUpPiece(6, 1, blackPawn2);
		board.setUpPiece(6, 2, blackPawn3);
		board.setUpPiece(6, 3, blackPawn4);
		
		Piece piece = null;
		

		// white pawn promotion to white queen
		listener.setOptionPane(new QueenMockOptionPane());
		piece = moveForwards(1, 0);
		assertEquals(piece.getType(), "Queen");
		assertTrue(piece.isWhite());

		// black pawn promotion to black queen
		listener.setOptionPane(new QueenMockOptionPane());
		piece = moveForwards(6, 0);
		assertEquals(piece.getType(), "Queen");
		assertFalse(piece.isWhite());
		

		// white pawn promotion to white rook
		listener.setOptionPane(new RookMockOptionPane());
		piece = moveForwards(1, 1);
		assertEquals(piece.getType(), "Rook");
		assertTrue(piece.isWhite());

		// black pawn promotion to black rook
		listener.setOptionPane(new RookMockOptionPane());
		piece = moveForwards(6, 1);
		assertEquals(piece.getType(), "Rook");
		assertFalse(piece.isWhite());

		// white pawn promotion to white bishop
		listener.setOptionPane(new BishopMockOptionPane());
		piece = moveForwards(1, 2);
		assertEquals(piece.getType(), "Bishop");
		assertTrue(piece.isWhite());
		

		// black pawn promotion to black bishop
		listener.setOptionPane(new BishopMockOptionPane());
		piece = moveForwards(6, 2);
		assertEquals(piece.getType(), "Bishop");
		assertFalse(piece.isWhite());

		// white pawn promotion to white knight
		listener.setOptionPane(new KnightMockOptionPane());
		piece = moveForwards(1, 3);
		assertEquals(piece.getType(), "Knight");
		assertTrue(piece.isWhite());

		// black pawn promotion to black knight
		listener.setOptionPane(new KnightMockOptionPane());
		piece = moveForwards(6, 3);
		assertEquals(piece.getType(), "Knight");
		assertFalse(piece.isWhite());

	}

	
}