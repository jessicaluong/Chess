package game;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This method looks for checks and checkmates on the board.
 */
public class CheckmateDetector {
	public Board board; 

	public CheckmateDetector(Board board) {
		this.board = board;
	}
	
	private Square getWhiteKingLocation(){
		Square kingLocation = null;
		
		for (Piece i: board.getWhitePieces()) {
			if (i.getType().equals("King")) {
				kingLocation = i.getSquare(); 
			} 
		}		
		
		return kingLocation;
	}
	
	private Square getBlackKingLocation() {
		Square kingLocation = null;
		
		for (Piece i: board.getBlackPieces()) {
			if (i.getType().equals("King")) {
				kingLocation = i.getSquare(); 
			} 
		}		
		
		return kingLocation;
	}
	
	private boolean isWhiteInCheck() { 
		Square kingLocation = getWhiteKingLocation();
		
		for (Piece i: board.getBlackPieces()) {
			for (Square j: i.getPossibleMoves(board)) {
				// a black piece can capture the white king
				if (j == kingLocation) {
					return true;
				} 
			}
		}
		
		return false; 
	}
	
	private boolean isBlackInCheck() {
		Square kingLocation = getBlackKingLocation();
		
		for (Piece i: board.getWhitePieces()) {
			for (Square j: i.getPossibleMoves(board)) {
				// a white piece can capture the black king
				if (j == kingLocation) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Temporarily moves piece to a new location.
	 */
	private Piece tryMove(Square initialSquare, Square tempSquare, Piece pieceToMove) {
		
		Piece saveCapturedPiece = null;
	
		initialSquare.removePieceFromSquare();
		
		if (tempSquare.hasPiece()) {
			saveCapturedPiece = tempSquare.getPiece();
			board.removePieceFromBoard(saveCapturedPiece);
			tempSquare.setPiece(pieceToMove);
		} else {
			tempSquare.setPiece(pieceToMove);
		}
		
		return saveCapturedPiece;
	}
	
	/**
	 * Return piece and if it captured a piece, the piece it captured, to its/their original location(s).
	 */
	private void returnPiece(Square initialSquare, Square tempSquare, Piece pieceMoved, Piece saveCapturedPiece) {
		
		tempSquare.removePieceFromSquare();
		initialSquare.setPiece(pieceMoved);
		
		// put captured piece back on the board and into its collection of pieces
		if (saveCapturedPiece != null) {
			board.setUpPiece(tempSquare.getRow(), tempSquare.getCol(), saveCapturedPiece);
			tempSquare.setPiece(saveCapturedPiece);
		}
		
	}
	
	/**
	 * Checks if the current player's move will put their own king in check.
	 */
	public boolean willBeInCheck(Square initialSquare, Square clickedSquare, Piece pieceHeld) {
		
		boolean inCheck = false;
		Piece saveCapturedPiece = null;
		
		saveCapturedPiece = tryMove(initialSquare, clickedSquare, pieceHeld);
		
		if (pieceHeld.isWhite()) {
			inCheck = isWhiteInCheck();
			if (inCheck) {
        board.setTitle("Invalid move - White king will be in check with this move.");
			}	
		} else {
			inCheck = isBlackInCheck();
			if (inCheck) {
        board.setTitle("Invalid move - Black king will be in check with this move.");
			}
		}
		
		returnPiece(initialSquare, clickedSquare, pieceHeld, saveCapturedPiece);

		return inCheck;
	}
	
	/**
	 * Check if current player's move put opponent in check.
	 */
	public boolean isOpponentInCheck(Piece pieceHeld) {
		boolean inCheck = false;
		
		// check if white piece move put black king in check 
		if (pieceHeld.isWhite() && isBlackInCheck()) {
			board.setTitle("Black is in Check - Black to Move");
			inCheck = true;
			if (isCheckmate(pieceHeld)) {
				board.setTitle("Checkmate - White Wins!");
			}
		// check if black piece move put white king in check 
		} else if (!pieceHeld.isWhite() && isWhiteInCheck()){
			board.setTitle("White is in Check - White to Move");
			inCheck = true;
			if (isCheckmate(pieceHeld)) {
				board.setTitle("Checkmate - Black Wins!");
			}
		}	
		
		return inCheck;
	}
	
	/**
	 * Moves king to adjacent squares to see if it can get out of check.
	 */
	private boolean canKingEscape(Piece king, Square kingLocation) {
		// move king to adjacent squares see if it can get out of check
		
		Piece saveCapturedPiece = null;
		boolean isInCheckmate = true; 
		
		for (Square i: king.getPossibleMoves(board)) {
			
			saveCapturedPiece = tryMove(kingLocation, i, king);
			
			if (king.isWhite() && !isWhiteInCheck()) {
				isInCheckmate = false;
			} else if (!king.isWhite() && !isBlackInCheck()){
				isInCheckmate = false;
			}

			returnPiece(kingLocation, i, king, saveCapturedPiece);
			
			// king has an escape so don't need to go through whole for loop
			if (!isInCheckmate) {
				return false;
			}
		}
		return true; 
	}
	
	/**
	 * Gets all opponent pieces that are putting the player's king in check.
	 */
	private ArrayList<Piece> getCheckingPieces(Square kingLocation, Collection<Piece> listOfPieces) {
		ArrayList<Piece> checkingPieces = new ArrayList<>();
		
		for (Piece i: listOfPieces) {
			for (Square j: i.getPossibleMoves(board)) {
				if (j == kingLocation) {
					checkingPieces.add(i);
				}
			}
		}
		return checkingPieces; 
	}
	
	/**
	 * Checks if a player's piece can block opponent piece and stop the check.
	 */
	private boolean canBlock(ArrayList<Piece> checkingPieces, Collection<Piece> listOfPieces, boolean pieceIsWhite) {
		boolean isInCheckmate = true; 
		Piece saveCapturedPiece = null;
		
		for (Piece i: checkingPieces) {
			for (Square checkingPiecePossibleMove: i.getPossibleMoves(board)) {
				for (Piece piece: listOfPieces) {
					for (Square blackPiecePossibleMove: piece.getPossibleMoves(board)) {
						if (checkingPiecePossibleMove == blackPiecePossibleMove) {
							
							// check if landing on these squares will stop the check 
							Square pieceLocation = piece.getSquare();
							saveCapturedPiece = tryMove(piece.getSquare(), checkingPiecePossibleMove, piece);
							
							if (pieceIsWhite && !isWhiteInCheck()) {
								isInCheckmate = false;
							} else if (!pieceIsWhite && !isBlackInCheck()) {
								isInCheckmate = false;
							}
			
							returnPiece(pieceLocation, checkingPiecePossibleMove, piece, saveCapturedPiece);
							
							if (!isInCheckmate) {
								return false;
							}
						}
					}
				}
			}
		}
		return isInCheckmate;
	}
	
	public boolean isCheckmate(Piece pieceHeld) {
		Piece king = null;
		boolean isInCheckmate = true;
		Square kingLocation = null;
		
		// on white's turn, check if black in checkmate from white's move
		if (pieceHeld.isWhite()) {
			kingLocation = getBlackKingLocation(); 
			king = kingLocation.getPiece();
	
			// move king to adjacent squares see if it can get out of check
			if (!canKingEscape(king, kingLocation)) {
				return false;
			}
			
			// get all white pieces that put black king in check
			ArrayList<Piece> checkingPieces = getCheckingPieces(kingLocation, board.getWhitePieces());
			
			// check if any black pieces can block/capture the black pieces that are checking the white king
			if (!canBlock(checkingPieces, board.getBlackPieces(), false)) {
				return false;
			}
			
		// on black's turn, check if white is in checkmate from black's move
		} else {
			kingLocation = getWhiteKingLocation();
			king = kingLocation.getPiece();
			
			// move king to adjacent squares see if it can get out of check
			if (!canKingEscape(king, kingLocation)) {
				return false;
			}
			
			// get all black pieces that put white king in check
			ArrayList<Piece> checkingPieces = getCheckingPieces(kingLocation, board.getBlackPieces());
			
			// check if any white pieces can block/capture the black pieces that are checking the black king
			if (!canBlock(checkingPieces, board.getWhitePieces(), true)) {
				return false;
			}
		}
		
		return isInCheckmate; 
	}
	
}