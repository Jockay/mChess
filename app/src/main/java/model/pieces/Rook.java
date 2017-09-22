package model.pieces;

import model.Board;
import model.Place;

import static java.lang.Math.abs;
import static model.Constants.*;

public class Rook extends Piece {

	public Rook(Place position, String color) {
		super(position, color, ROOK);
	}
	
	@Override
	public boolean isHitable(Place b, Board board) {
		int placeValue = board.getValue(b);
		Place a  = getPosition();
		int   ax = a.getX();
		int   ay = a.getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if( ! isBasicallyHitable(placeValue)) {
			return false;
		}
		
		boolean horizontalDirection = (abs(ax - bx) > 0  && abs(ay - by) == 0);
		boolean verticalDirection   = (abs(ax - bx) == 0 && abs(ay - by) > 0 );
		
		if ( ! ( horizontalDirection || verticalDirection ) )  {
			return false;
		}
		
		return isNoPieceOnPathCross(board, ax, ay, bx, by, verticalDirection, horizontalDirection);
	}
	
	
	@Override
	public boolean isPossibleHitablePlace(Place b, model.Board board) {
		int   ax = getPosition().getX();
		int   ay = getPosition().getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if(getPosition().equals(b)) {
			return false;
		}
		
		boolean horizontalDirection = (abs(ax - bx) > 0  && abs(ay - by) == 0);
		boolean verticalDirection   = (abs(ax - bx) == 0 && abs(ay - by) > 0 );
		
		if ( ! ( horizontalDirection || verticalDirection ) )  {
			return false;
		}
		
		return isNoPieceOnPathCross(board, ax, ay, bx, by, verticalDirection, horizontalDirection);
	}

	@Override
	public boolean isStepable(Place b, Board board) {
		int placeValue = board.getValue(b);
		Place a  = getPosition();
		int   ax = a.getX();
		int   ay = a.getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if(placeValue != 0) {
			return false;
		}
		
		boolean horizontalDirection = (abs(ax - bx) > 0  && abs(ay - by) == 0);
		boolean verticalDirection   = (abs(ax - bx) == 0 && abs(ay - by) > 0 );
		
		if ( ! ( horizontalDirection || verticalDirection ) )  {
			return false;
		}
		
		return isNoPieceOnPathCross(board, ax, ay, bx, by, verticalDirection, horizontalDirection);
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
