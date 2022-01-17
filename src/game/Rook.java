package game;

import java.util.Collection;

public class Rook extends Piece {

	public Rook(String imgPath, boolean isWhite) {
		super(imgPath, isWhite);
	}

	@Override
	public Collection<Square> getPossibleMoves(Board board) {
		
		Collection<Square> possibleMoves = super.getPossibleMoves(board);

		addHorizontalandVerticalMoves(board, possibleMoves); 		
	
		return possibleMoves;
	}

}