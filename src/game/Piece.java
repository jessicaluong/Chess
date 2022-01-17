package game;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public abstract class Piece {
	
	private ImageIcon img; 
	private Square square;
	private final boolean isWhite;
	private String type;
	
	/**
	 * Constructor for Piece. Sets the piece type and color, and creates the piece's image for the GUI.
	 */
	Piece(String type, boolean isWhite) {
		this.isWhite = isWhite;
		this.type = type;
				
		if (isWhite) {
			createImage("PieceImages/White" + type + ".png");
			
		} else {
			createImage("PieceImages/Black" + type + ".png");
		}
	}

	private void createImage(String imgPath) {
		ImageIcon img = new ImageIcon(imgPath);
		this.img = img;
	}
	
	/**
	 * Sets the square the piece is on and the piece's image on the square for the GUI.
	 */
	public void setSquare(Square square) {
		this.square = square;
		square.setIcon(this.getIcon());
	}
	
	/**
	 * Adds an empty square or a square with a piece of another color to the piece's possible moves (allowing for capture).
	 */
	protected void addMove(int row, int col, Board board, Collection<Square> possibleMoves) {
		if (row <= 7 && row >= 0 && col <= 7 && col >= 0) {
			Square square = board.getSquare(row, col);
			if (!square.hasPiece() || (this.isWhite && !square.getPiece().isWhite) 
					|| (!this.isWhite() && square.getPiece().isWhite())) {
				possibleMoves.add(board.getSquare(row, col));
			}
		}
	}
	
	/**
	 * Adds all squares up/down and left/right to a piece's possible moves as long as it doesn't jump over another piece.
	 */
	protected void addHorizontalandVerticalMoves(Board board, Collection<Square> possibleMoves) {				
		
		Square square = this.getSquare();
		
		int x = square.getRow(); 
		int y = square.getCol();
		
		int i; 
		
		// add squares to left/right
		for (i = y-1; i >= 0 && !board.getSquare(x, i).hasPiece(); i--) {
			possibleMoves.add(board.getSquare(x, i));
		}
		addMove(x, i, board, possibleMoves);

		for (i = y+1; i <= 7 && !board.getSquare(x, i).hasPiece(); i++) {
			possibleMoves.add(board.getSquare(x, i));
		}
		addMove(x, i, board, possibleMoves);
		
		
		// add squares above/below
		for (i = x-1; i >= 0 && !board.getSquare(i, y).hasPiece(); i--) {
			possibleMoves.add(board.getSquare(i, y));
		}
		addMove(i, y, board, possibleMoves);
		
		for (i = x+1; i <= 7 && !board.getSquare(i, y).hasPiece(); i++) {
			possibleMoves.add(board.getSquare(i, y));
		}
		addMove(i, y, board, possibleMoves);
	}
	
	/**
	 * Adds all squares that run diagonal to a piece to its possible moves as long as it doesn't jump over another piece.
	 */
	protected void addDiagonalMoves(Board board, Collection<Square> possibleMoves) {				
		Square square = this.getSquare();
		
		int x = square.getRow(); 
		int y = square.getCol();
		
		int i, j; 

		// add NW (top-left diagonal)/NE (top-right diagonal) squares
		i = x-1; j = y-1; 
		while (i >= 0 && j >= 0 && !board.getSquare(i, j).hasPiece()) {
			possibleMoves.add(board.getSquare(i, j));
			i--; j--; 
		}
		addMove(i, j, board, possibleMoves);	
		
		i = x-1; j = y+1; 
		while (i >= 0 && j <= 7 && !board.getSquare(i, j).hasPiece()) {
			possibleMoves.add(board.getSquare(i, j));
			i--; j++; 
		}
		addMove(i, j, board, possibleMoves);
		
		
		// add SW (bottom-left diagonal)/SE (bottom-right diagonal) squares
		i = x+1; j = y-1; 
		while (i <= 7 && j >= 0 && !board.getSquare(i, j).hasPiece()) {
			possibleMoves.add(board.getSquare(i, j));
			i++; j--;
		}
		addMove(i, j, board, possibleMoves);
		
		i = x+1; j = y+1; 
		while (i <= 7 && j <= 7 && !board.getSquare(i, j).hasPiece()) {
			possibleMoves.add(board.getSquare(i, j));
			i++; j++;
		}
		addMove(i, j, board, possibleMoves);
	}
	
	/**
	 * Returns a collection of squares a piece can move to from its current location.
	 */
	public Collection<Square> getPossibleMoves(Board board) {
		Collection<Square> possibleMoves = new LinkedList<>();
		
		// pieces can go back to the square they were originally on
		possibleMoves.add(this.getSquare());
		
		return possibleMoves; 
	}
	
	public Square getSquare() {
		return this.square;
	}
	
	public ImageIcon getIcon() {
		return img; 
	}

	public boolean isWhite() {
		return isWhite; 
	}
	
	public String getType() {
		return type;
	}
	

}