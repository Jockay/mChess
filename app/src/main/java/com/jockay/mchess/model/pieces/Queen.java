package com.jockay.mchess.model.pieces;

import com.jockay.mchess.model.Board;
import com.jockay.mchess.model.Place;

import static java.lang.Math.abs;
import static com.jockay.mchess.model.Constants.*;

public class Queen extends Piece {

	public Queen(Place position, String color) {
		super(position, color, QUEEN);
	}

	@Override
	public boolean isStepable(Place b, Board board) {
		int placeValue = board.getValue(b);
		int   ax = getPosition().getX();
		int   ay = getPosition().getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if(placeValue != 0) {
			return false;
		}
		
		boolean horizontalDirection = (abs(ax - bx) > 0  && abs(ay - by) == 0);
		boolean verticalDirection   = (abs(ax - bx) == 0 && abs(ay - by) > 0 );
		
		if ( ( ! ( horizontalDirection || verticalDirection )) 
				&& ( ! (abs(ax - bx) == abs(ay - by))) )  {
			return false;
		}
		
		if( ! isNoPieceOnPathCross(board, ax, ay, bx, by, verticalDirection, horizontalDirection)) {
			return false;
		}
		
		if( ! isNoPieceOnPathDiagonal(board, ax, ay, bx, by)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isHitable(Place b, Board board) {
		int placeValue = board.getValue(b);
		int   ax = getPosition().getX();
		int   ay = getPosition().getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if( ! isBasicallyHitable(placeValue)) {
			return false;
		}
		
		boolean horizontalDirection = (abs(ax - bx) > 0  && abs(ay - by) == 0);
		boolean verticalDirection   = (abs(ax - bx) == 0 && abs(ay - by) > 0 );
		
		if ( ( ! ( horizontalDirection || verticalDirection )) 
				&& ( ! (abs(ax - bx) == abs(ay - by))) )  {
			return false;
		}
		
		if( ! isNoPieceOnPathCross(board, ax, ay, bx, by, verticalDirection, horizontalDirection)) {
			return false;
		}
		
		if( ! isNoPieceOnPathDiagonal(board, ax, ay, bx, by)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean isPossibleHitablePlace(Place b, Board board) {
		int   ax = getPosition().getX();
		int   ay = getPosition().getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if(b.equals(getPosition())) {
			return false;
		}
		
		boolean horizontalDirection = (abs(ax - bx) > 0  && abs(ay - by) == 0);
		boolean verticalDirection   = (abs(ax - bx) == 0 && abs(ay - by) > 0 );
		
		if ( ( ! ( horizontalDirection || verticalDirection )) 
				&& ( ! (abs(ax - bx) == abs(ay - by))) )  {
			return false;
		}
		
		if( ! isNoPieceOnPathCross(board, ax, ay, bx, by, verticalDirection, horizontalDirection)) {
			return false;
		}
		
		if( ! isNoPieceOnPathDiagonal(board, ax, ay, bx, by)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isNoPieceOnPathDiagonal(Board board, int ax, int ay, int bx, int by) {
		// check directions for piece on path
		// direction: up && left
		if(by < ay && bx < ax) {
			for (int i = (ay - 1), j = (ax - 1); (i > by) && (j > bx); i--, j--) {
				if(board.getValue(new Place(j, i)) != 0) {
					return false;
				}
			}
		}
		// direction: up && right
		else if(by < ay && bx > ax) {
			for (int i = (ay - 1), j = (ax + 1); (i > by) && (j < bx); i--, j++) {
				if(board.getValue(new Place(j, i)) != 0) {
					return false;
				}
			}
		}
		// direction: down && left
		else if(by > ay && bx < ax) {
			for (int i = (ay + 1), j = (ax - 1); (i < by) && (j > bx); i++, j--) {
				if(board.getValue(new Place(j, i)) != 0) {
					return false;
				}
			}
		}
		// direction: down && right
		else if(by > ay && bx > ax) {
			for (int i = (ay + 1), j = (ax + 1); (i < by) && (j < bx); i++, j++) {
				if(board.getValue(new Place(j, i)) != 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean isNoPieceOnPathCross(Board board, int ax, int ay, int bx, int by, boolean verticalDirection, boolean horizontalDirection) {
		if(verticalDirection) {
			if(ay > by) {
				for (int i = (ay - 1); i > by; i--) {
					if(board.getValue(new Place(ax, i)) != 0) {
						return false;
					}
				}
			} else {
				for (int i = (ay + 1); i < by; i++) {
					if(board.getValue(new Place(ax, i)) != 0) {
						return false;
					}
				}
			}
		} else if( horizontalDirection ) {
			if(ax > bx) {
				for (int i = (ax - 1); i > bx; i--) {
					if(board.getValue(new Place(i, ay)) != 0) {
						return false;
					}
				}
			} else {
				for (int i = (ax + 1); i < bx; i++) {
					if(board.getValue(new Place(i, ay)) != 0) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
}
