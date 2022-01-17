package game;

import java.util.Collection;

public class Pawn extends Piece {
			
	public Pawn(String imgPath, boolean isWhite) {
		super(imgPath, isWhite);
	}
	
	/**
	 * Pawn can move forward one space onto an empty square normally, but on its first move, it can move 
	 * two spaces as long as it doesn't jump over another piece.
	 */
	private void addForwardMove(int row, int col, Board board, Collection<Square> possibleMoves) {
		if (row <= 7 && row >= 0 && col <= 7 && col >= 0 && !board.getSquare(row, col).hasPiece()) {
			possibleMoves.add(board.getSquare(row, col));
			
			if (row == 5 && this.isWhite() && !board.getSquare(row-1, col).hasPiece()) {
				possibleMoves.add(board.getSquare(row-1, col));
			} else if (row == 2 && !this.isWhite() && !board.getSquare(row+1, col).hasPiece()) {
				possibleMoves.add(board.getSquare(row+1, col));
			}
		}
	}
	
	/**
	 * If the square has a piece of another color, it is added to the pawn's possible moves.
	 */
	protected void addCaptureMove(int row, int col, Board board, Collection<Square> possibleMoves) {
		if (row <= 7 && row >= 0 && col <= 7 && col >= 0 && board.getSquare(row,col).hasPiece() 
				&& ((this.isWhite() && !board.getSquare(row, col).getPiece().isWhite()) 
						|| (!this.isWhite() && board.getSquare(row, col).getPiece().isWhite()))){
			possibleMoves.add(board.getSquare(row, col));
		}
	}

	@Override
	public Collection<Square> getPossibleMoves(Board board) {
		
		Collection<Square> possibleMoves = super.getPossibleMoves(board);
		
		Square square = this.getSquare();
		
		int x = square.getRow(); 
		int y = square.getCol();
		
		if (this.isWhite()) {
			addForwardMove(x-1, y, board, possibleMoves);
			addCaptureMove(x-1, y-1, board, possibleMoves);
			addCaptureMove(x-1, y+1, board, possibleMoves);
		} else {
			addForwardMove(x+1, y, board, possibleMoves);
			addCaptureMove(x+1, y-1, board, possibleMoves);
			addCaptureMove(x+1, y+1, board, possibleMoves);
		}
		
		return possibleMoves;
	}


}
