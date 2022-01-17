package game;

import java.util.Collection;

public class Bishop extends Piece{

	public Bishop(String imgPath, boolean isWhite) {
		super(imgPath, isWhite);
	}

	@Override
	public Collection<Square> getPossibleMoves(Board board) {
		
		Collection<Square> possibleMoves = super.getPossibleMoves(board);

		addDiagonalMoves(board, possibleMoves); 	

		return possibleMoves;
	}
	
}