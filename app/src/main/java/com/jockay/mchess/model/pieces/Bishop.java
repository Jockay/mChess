package com.jockay.mchess.model.pieces;

import com.jockay.mchess.model.Board;
import com.jockay.mchess.model.Place;

import static java.lang.Math.abs;
import static com.jockay.mchess.model.Constants.*;

public class Bishop extends Piece {

	public Bishop(Place position, String color) {
		super(position, color, BISHOP);
	}

	@Override
	public boolean isHitable(Place b, Board board) {
		int placeValue = board.getValue(b);
		int ax = getPosition().getX();
		int ay = getPosition().getY();
		int bx = b.getX();
		int by = b.getY();
		
		if( ! isBasicallyHitable(placeValue)) {
			return false;
		}
		
		if( ! (abs(ax - bx) == abs(ay - by))) { 
			return false;
		}
		
		return isNoPieceOnPathDiagonal(board, ax, ay, bx, by);
	}
	
	@Override
	public boolean isStepable(Place b, Board board) {
		int placeValue = board.getValue(b);
		int ax = getPosition().getX();
		int ay = getPosition().getY();
		int bx = b.getX();
		int by = b.getY();
		
		if(placeValue != 0 ) {
			return false;
		}
		
		if( ! (abs(ax - bx) == abs(ay - by))) { 
			return false;
		}
		
		return isNoPieceOnPathDiagonal(board, ax, ay, bx, by);
	}

	@Override
	public boolean isPossibleHitablePlace(Place b, Board board) {
		Place a  = getPosition();
		int   ax = a.getX();
		int   ay = a.getY();
		int   bx = b.getX();
		int   by = b.getY();
		
		if(a.equals(b)) {
			return false;
		}
		
		if( ! (abs(ax - bx) == abs(ay - by))) { 
			return false;
		}
		
		return isNoPieceOnPathDiagonal(board, ax, ay, bx, by);
	};
	
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
	
}
