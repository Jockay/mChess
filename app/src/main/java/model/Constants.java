package model;

public class Constants {
	
	public static final String WHITE = "white";
	public static final String BLACK = "black";
	public static final String UP   = "up";
	public static final String DOWN = "down";
	
	public static final int PAWN   = 1;
	public static final int ROOK   = 2;
	public static final int KNIGHT = 3;
	public static final int BISHOP = 4;
	public static final int QUEEN  = 5;
	public static final int KING   = 6;

	public static final int PROMOTION_DONE      = 3;
	public static final int PROMOTION_REQUIERED = 2;
	public static final int HIT_DONE            = 1;
	public static final int STEP_DONE           = 0;	
	public static final int NO_PIECE_FOUND      = -1;
	public static final int NOT_MOVEABLE        = -2;
	public static final int CHECK               = -3;
	public static final int DRAWN               = -5;
	public static final int CHECKMATE           = -6;
	
}
