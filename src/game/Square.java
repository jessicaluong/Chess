package game;

import java.awt.Color; 

import javax.swing.JButton;

public class Square extends JButton {
	
	private static final long serialVersionUID = 1L;
	private int row; 
	private int col;
	private Color squareColor; 
	private Piece piece; 
	
	/**
	 * Constructor for Square. Sets up the square's position on the board and its color. 
	 * @param squareColor The square's original color; allows the square's color to be reverted to this on the GUI.
	 */
	public Square(int row, int col, Color squareColor) {
		this.row = row;
		this.col = col;
		this.squareColor = squareColor;
		setBackground(squareColor);
		setOpaque(true);
		setBorderPainted(false);
	}
	
	public void setPiece(Piece gamePiece) {
		this.piece = gamePiece; 
		
		if (this.piece == null) {
			return; 
		} else {
			piece.setSquare(this);
		}
	}
	
	/**
	 * Removes piece from the square and removes image of that piece from the GUI.
	 */
	public void removePieceFromSquare() {
		this.piece = null;
		this.setIcon(null);
	}
	
	public boolean hasPiece() {
		return piece != null; 
	}

	public Piece getPiece() {
		return this.piece;
	}
	
	public Color getSquareColor() {
		return squareColor;
	}

	public int getRow() {
		 return row;
	 }
	 
	 public int getCol() {
		 return col;
	 }
		

}
