package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import optionpane.DefaultOptionPane;
import optionpane.OptionPane;

import java.awt.Color; 

/**
 * Tracks clicks on the board and tracks player turns.
 */
public class MoveListener extends MouseAdapter {
	
	private Square initialSquare; // save square that's clicked 
	private Piece pieceHeld; 
	private Board board;
	private boolean isWhiteTurn;
	Collection<Square> pieceHeldMoves;
	CheckmateDetector checkmateDetector; 
	OptionPane optionPane;
	
	/**
	 * Constructor for MoveListener. Sets first turn to white and sets the board the MoveListener is on.
	 * @param chessBoard The board the MoveListener is added to 
	 */
	public MoveListener(Board chessBoard) {
		this.board = chessBoard;
		isWhiteTurn = true;
		checkmateDetector = new CheckmateDetector(board);
		//optionPane = new BishopMockOptionPane();
		optionPane = new DefaultOptionPane();
	}
	
	/**
	 * Alternate player turns and sets title on board to indicate whose turn it is.
	 */
	private void swapTurns() {
		if (isWhiteTurn) {
			isWhiteTurn = false;
			board.setTitle("Black to Move");
		} else {
			isWhiteTurn = true;
			board.setTitle("White to Move");
		}
	}
	
	/**
	 * Determines whether a player can go or not
	 * @return True if a piece of the correct color is moved false otherwise
	 */
	private boolean isValidTurn(Square initialSquare, Square clickedSquare) {
		if (!clickedSquare.hasPiece() || initialSquare != null) {
			return true;
		} else if (isWhiteTurn && clickedSquare.getPiece().isWhite()) {
			return true;
		} else if (!isWhiteTurn && !clickedSquare.getPiece().isWhite()) {
			return true;
		} 
		return false;	
	}
	
	private void holdPiece(Square clickedSquare) {
		System.out.println("has piece");
		this.initialSquare = clickedSquare;
		pieceHeld = clickedSquare.getPiece();
		clickedSquare.setBackground(Color.LIGHT_GRAY);
		clickedSquare = null;
	}
	
	private void capturePiece(Square clickedSquare) {
		System.out.println("capture piece");
		board.removePieceFromBoard(clickedSquare.getPiece());
		clickedSquare.setPiece(pieceHeld);
		clickedSquare.setBackground(clickedSquare.getSquareColor());
		initialSquare.setBackground(initialSquare.getSquareColor());
		initialSquare = null;
		swapTurns(); 
	}
	
	private void pieceToEmptySquare(Square clickedSquare) {
		System.out.println("moving from (" + initialSquare.getRow() + "," + initialSquare.getCol() + ") to (" + clickedSquare.getRow() + "," + clickedSquare.getCol() +")");
		clickedSquare.setPiece(pieceHeld);
		clickedSquare.setBackground(clickedSquare.getSquareColor());
		initialSquare.setBackground(initialSquare.getSquareColor());
		if (initialSquare != clickedSquare) {
			swapTurns(); 
		}
		initialSquare = null;
	}
	

	public void setOptionPane(OptionPane option) {
		this.optionPane = option;
	}
	
	public int getValueFromDialog() {
		Object[] options = { "QUEEN", "ROOK", "BISHOP", "KNIGHT" };
		JLabel label = new JLabel("Pick a piece");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		return JOptionPane.showOptionDialog(null, label, "Promotion!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	}
	
	private void promotePawn(Piece pawn, boolean isWhitePawn) {
		
		int x = pawn.getSquare().getRow();
		int y = pawn.getSquare().getCol();
		
		Object[] options = { "QUEEN", "ROOK", "BISHOP", "KNIGHT" };
		JLabel label = new JLabel("Pick a piece");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		int input = 4; 
		while (input == 4) {
			input = optionPane.showOptionDialog(null, label, "Promotion!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);;
			if (input == 0) {
				board.removePieceFromBoard(pawn);
				Piece piece = new Queen("Queen", isWhitePawn);
				board.setUpPiece(x, y, piece);
			} else if (input == 1) {
				board.removePieceFromBoard(pawn);
				Piece piece = new Rook("Rook", isWhitePawn);
				board.setUpPiece(x, y, piece);
			} else if (input == 2) {
				board.removePieceFromBoard(pawn);
				Piece piece = new Bishop("Bishop", isWhitePawn);
				board.setUpPiece(x, y, piece);
			} else if (input == 3) {
				board.removePieceFromBoard(pawn);
				Piece piece = new Knight("Knight", isWhitePawn);
				board.setUpPiece(x, y, piece);
			} else {
				input = 4; // user tried to close window without picking an option
			}
		}
	}
	
	
	private void checkPawnPromotion(Square clickedSquare) {
		if (clickedSquare.getPiece().getType().equals("Pawn")) {
			Piece pawn = clickedSquare.getPiece();
			
			if (pawn.isWhite() && clickedSquare.getRow() == 0) {
				promotePawn(pawn, true);
			} else if (!pawn.isWhite() && clickedSquare.getRow() == 7){
				promotePawn(pawn, false);
			}
		}
	}
	
	@Override 
	public void mouseClicked(MouseEvent e) {

		Square clickedSquare = (Square) e.getSource();

		if (isValidTurn(initialSquare, clickedSquare)) {

			// gives player a piece to hold
			if (clickedSquare.hasPiece() && initialSquare == null) {
				holdPiece(clickedSquare);
				pieceHeldMoves = pieceHeld.getPossibleMoves(board);
				
			// allows piece to move back to its initial square
			} else if (initialSquare != null && initialSquare == clickedSquare) {
				pieceToEmptySquare(clickedSquare);

			} else if (initialSquare != null && pieceHeldMoves.contains(clickedSquare) && !checkmateDetector.willBeInCheck(initialSquare, clickedSquare, pieceHeld)) {
				
				initialSquare.removePieceFromSquare();

				// move piece to a space with a piece of a different color - captures the other piece
				if (clickedSquare.hasPiece()) {
					capturePiece(clickedSquare);
				// move piece to empty space
				} else {
					pieceToEmptySquare(clickedSquare);
				}
				
				// check if the move put opponent in check 
				checkmateDetector.isOpponentInCheck(pieceHeld);
				
				// check if pawn is eligible for promotion
				checkPawnPromotion(clickedSquare);
	
			// if player is not holding a piece and clicks an empty square, nothing happens 
			} else if (!clickedSquare.hasPiece() && initialSquare == null) {
				System.out.println("do nothing"); 
			}
		} else {
			System.out.println("wrong turn");
		}			
	}
	
	// Change color of the square to indicate it is pressed.
	@Override
	public void mousePressed(MouseEvent e) {
		Square pressedSquare = (Square) e.getSource();
		pressedSquare.setBackground(Color.PINK);
	}
	
	// Revert color of square to original color to indicate it is released.
	@Override
	public void mouseReleased(MouseEvent e) {
		Square pressedSquare = (Square) e.getSource();
		pressedSquare.setBackground(pressedSquare.getSquareColor());
	}
	
	public CheckmateDetector getCheckmateDetector() {
		return checkmateDetector;
	}
}