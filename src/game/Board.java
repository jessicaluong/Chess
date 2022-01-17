package game;

import java.awt.Color; 
import java.awt.GridLayout;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
	
	private Square squares[][];
	private JFrame frame;
	private JPanel panel;
    private MoveListener moveListener; 
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;


	public Board() {
		squares = new Square[8][8];
		moveListener = new MoveListener(this);
		whitePieces = new LinkedList<>(); 
		blackPieces = new LinkedList<>(); 
		setBoard();
		addSquares();
		displayBoard();	
	}
	
	/**
	 * GUI setup.
	 */
	private void setBoard() {
		frame = new JFrame();
		panel = new JPanel();
		frame.setSize(500,500);
		panel.setLayout(new GridLayout(8, 8));
		panel.setBackground(Color.WHITE);
	}
	
	/**
	 * Create squares and adds them and a move listener to the board. 
	 */
	private void addSquares() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i+j) % 2 == 0) {
					this.squares[i][j] = new Square(i, j, Color.WHITE); // black squares
				} else {
					this.squares[i][j] = new Square(i, j, Color.GRAY); // white squares 
				}	
				squares[i][j].addMouseListener(moveListener);
				panel.add(this.squares[i][j]);
			}
		}
	}
	
	/**
	 * GUI setup.
	 */
	private void displayBoard() {
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chess");
		frame.setVisible(true);
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	/**
	 * Creates pieces and sets them up on the board.
	 */
	public void initializePieces() {
		
		Rook br1 = new Rook("Rook", false);
		setUpPiece(0, 0, br1);
		
		Rook br2 = new Rook("Rook", false);
		setUpPiece(0, 7, br2);
		
		Rook wr1 = new Rook("Rook", true);
		setUpPiece(7, 0, wr1);
		
		Rook wr2 = new Rook("Rook", true);
		setUpPiece(7, 7, wr2);

		Knight bk1 = new Knight("Knight", false);
		setUpPiece(0, 1, bk1);
		
		Knight bk2 = new Knight("Knight", false);
		setUpPiece(0, 6, bk2);
		
		Knight wk1 = new Knight("Knight", true);
		setUpPiece(7, 1, wk1);
		
		Knight wk2 = new Knight("Knight", true);
		setUpPiece(7, 6, wk2); 
		
		Bishop bb1 = new Bishop("Bishop", false);
		setUpPiece(0, 2, bb1);
		
		Bishop bb2 = new Bishop("Bishop", false);
		setUpPiece(0, 5, bb2);
		
		Bishop wb1 = new Bishop("Bishop", true);
		setUpPiece(7, 2, wb1);
		
		Bishop wb2 = new Bishop("Bishop", true);
		setUpPiece(7, 5, wb2);
		
		Queen bq = new Queen("Queen", false);
		setUpPiece(0, 3, bq);
		
		Queen wq = new Queen("Queen", true);
		setUpPiece(7, 3, wq);
		
		King bking = new King("King", false);
		setUpPiece(0, 4, bking);

		King wking = new King("King", true);
		setUpPiece(7, 4, wking);
		
		for (int i = 0; i < 8; i++) {
			Pawn bp = new Pawn("Pawn", false);
			Pawn wp = new Pawn("Pawn", true);
			setUpPiece(1, i, bp); 
			setUpPiece(6, i, wp);
		}
	}
	
	/**
	 * Add piece to a its collection and to the board.
	 */
	public void setUpPiece(int row, int col, Piece piece) {
		if (!piece.isWhite()) {
			blackPieces.add(piece);
			squares[row][col].setPiece(piece);
		} else {
			whitePieces.add(piece);
			squares[row][col].setPiece(piece); 
		}
	}
	
	/**
	 * Removes a piece from its collection .
	 */
	public void removePieceFromBoard(Piece piece) {
		if (piece.isWhite()) {
			whitePieces.remove(piece); 
		} else {
			blackPieces.remove(piece); 
		}
	}
	
	public Square getSquare(int row, int col) {
		return squares[row][col];
	}

	public Collection<Piece> getBlackPieces() {
		return blackPieces; 
	}
	
	public Collection<Piece> getWhitePieces() {
		return whitePieces; 
	}	

	public MoveListener getMoveListener() {
		return moveListener;
	}
	
}
