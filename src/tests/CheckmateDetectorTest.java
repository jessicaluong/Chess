package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Bishop;
import game.Board;
import game.CheckmateDetector;
import game.King;
import game.MoveListener;
import game.Pawn;
import game.Rook;
import game.Square;

class CheckmateDetectorTest {

	Board board; 
	CheckmateDetector detector; 

	@BeforeEach
	void setUp() throws Exception {
		board = new Board();
		MoveListener listener = board.getMoveListener();
		detector = listener.getCheckmateDetector();
	}
	
	private static void click(Component source) {
		MouseEvent press, release, click;
		
		press = new MouseEvent(source, MouseEvent.MOUSE_PRESSED, 1, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		release = new MouseEvent(source, MouseEvent.MOUSE_RELEASED, 2, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		click = new MouseEvent(source, MouseEvent.MOUSE_CLICKED, 3, 0, 0, 0, 0, false, MouseEvent.BUTTON1);

		source.dispatchEvent(press);
		source.dispatchEvent(release);
		source.dispatchEvent(click);
	}
	
	private void clickSquare(int x, int y) {
		Square square = board.getSquare(x, y);
		click(square);
	}

	@Test
	void testWhiteWillBeInCheck() {
		King whiteKing = new King("King", true);
		Rook blackRook = new Rook ("Rook", false);
		
		board.setUpPiece(0, 0, whiteKing);
		board.setUpPiece(1, 1, blackRook);
		
		// try moving white king into check
		clickSquare(0, 0);
		clickSquare(0, 1);
		
		assertFalse(board.getSquare(0, 1).hasPiece());		
	}
	
	@Test
	void testBlackWillBeInCheck() {
		King blackKing = new King("King", false);
		Rook whiteRook = new Rook ("Rook", true);
		
		board.setUpPiece(0, 0, blackKing);
		board.setUpPiece(2, 1, whiteRook);

		// white has to move first
		clickSquare(2, 1);
		clickSquare(1, 1);
		
		// try moving black king into check
		clickSquare(0, 0);
		clickSquare(0, 1);
		
		assertFalse(board.getSquare(0, 1).hasPiece());		
	}
	
	@Test
	void testIsWhiteOpponentInCheck() {
		King whiteKing = new King("King", true);
		Pawn blackPawn1 = new Pawn("Pawn", false);
		Pawn blackPawn2 = new Pawn("Pawn", false);
		Pawn blackPawn3 = new Pawn("Pawn", false);
		
		board.setUpPiece(6, 6, whiteKing);
		board.setUpPiece(7, 6, blackPawn1);
		board.setUpPiece(6, 7, blackPawn2);
		board.setUpPiece(5, 6, blackPawn3);
		
		// move white king first because white has to start and game checks for valid turns
		clickSquare(6, 6);
		clickSquare(7, 7);
		
		// move black pawn to check white king
		clickSquare(5, 6);
		clickSquare(6, 6);
		
		assertTrue(detector.isOpponentInCheck(blackPawn3));
	}

	@Test
	void testIsBlackOpponentInCheck() {
		King blackKing = new King("King", false);
		Pawn whitePawn1 = new Pawn("Pawn", true);
		Pawn whitePawn2 = new Pawn("Pawn", true);
		Pawn whitePawn3 = new Pawn("Pawn", true);
		
		board.setUpPiece(0, 0, blackKing);
		board.setUpPiece(1, 0, whitePawn1);
		board.setUpPiece(0, 1, whitePawn2);
		board.setUpPiece(2, 1, whitePawn3);
		
		// move white pawn to check black king
		clickSquare(2, 1);
		clickSquare(1, 1);
		
		assertTrue(detector.isOpponentInCheck(whitePawn3));
	}

	@Test
	void testWhiteInCheckmate() {
		King whiteKing = new King("King", true);
		Pawn whitePawn1 = new Pawn("Pawn", true);
		Pawn whitePawn2 = new Pawn("Pawn", true);
		Bishop blackBishop = new Bishop ("Bishop", false);
		
		board.setUpPiece(1, 1, whiteKing);
		board.setUpPiece(0, 1, whitePawn1);
		board.setUpPiece(1, 0, whitePawn2);
		board.setUpPiece(7, 5, blackBishop);
		
		// move white king first because white has to start and game checks for valid turns
		clickSquare(1, 1);
		clickSquare(0, 0);
		
		// move black bishop to check white king
		clickSquare(7, 5);
		clickSquare(6, 6);
		
		assertTrue(detector.isCheckmate(blackBishop));
	}
	
	@Test
	void testBlackInCheckmate() {
		King blackKing = new King("King", false);
		Pawn blackPawn1 = new Pawn("Pawn", false);
		Pawn blackPawn2 = new Pawn("Pawn", false);
		Bishop whiteBishop = new Bishop ("Bishop", true);

		
		board.setUpPiece(7, 7, blackKing);
		board.setUpPiece(7, 6, blackPawn1);
		board.setUpPiece(6, 7, blackPawn2);
		board.setUpPiece(0, 2, whiteBishop);

		// move white bishop to check black king
		clickSquare(0, 2);
		clickSquare(1, 1);
		
		assertTrue(detector.isCheckmate(whiteBishop));
	}
	
}