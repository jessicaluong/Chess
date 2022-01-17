package game;

import java.util.Collection;

public class King extends Piece {

	public King(String imgPath, boolean isWhite) {
		super(imgPath, isWhite);
	}
	
	@Override
	public Collection<Square> getPossibleMoves(Board board) {
				
		Collection<Square> possibleMoves = super.getPossibleMoves(board);
		
		Square square = this.getSquare();

		int x = square.getRow(); 
		int y = square.getCol();
		
		addMove(x+1, y, board, possibleMoves);
		addMove(x-1, y, board, possibleMoves);
		addMove(x, y+1, board, possibleMoves);
		addMove(x, y-1, board, possibleMoves);
		addMove(x-1, y-1, board, possibleMoves);
		addMove(x-1, y+1, board, possibleMoves);
		addMove(x+1, y-1, board, possibleMoves);
		addMove(x+1, y+1, board, possibleMoves);

		return possibleMoves;
	}

}