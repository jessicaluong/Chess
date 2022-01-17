package game;

import java.util.Collection;

public class Queen extends Piece {

	public Queen(String imgPath, boolean isWhite) {
		super(imgPath, isWhite);
	}

	@Override
	public Collection<Square> getPossibleMoves(Board board) {
		
		Collection<Square> possibleMoves = super.getPossibleMoves(board);
		
		addHorizontalandVerticalMoves(board, possibleMoves); 	
		addDiagonalMoves(board, possibleMoves); 	

		return possibleMoves;
	}

}