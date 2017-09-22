package model.pieces;

import model.Board;
import model.Place;

import static java.lang.Math.abs;
import static model.Constants.*;

public class Knight extends Piece {

	public Knight(Place position, String color) {
		super(position, color, KNIGHT);
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
		
		return ((abs(ax - bx) == 2 && abs(ay - by) == 1) 
				|| (abs(ax - bx) == 1 && abs(ay - by) == 2));
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
		
		return (abs(ax - bx) == 2 && abs(ay - by) == 1) 
				|| (abs(ax - bx) == 1 && abs(ay - by) == 2);
	}

	@Override
	public boolean isPossibleHitablePlace(Place b, Board board) {
		int ax = getPosition().getX();
		int ay = getPosition().getY();
		int bx = b.getX();
		int by = b.getY();
		
		if(getPosition().equals(b)) {
			return false;
		}
		
		return (abs(ax - bx) == 2 && abs(ay - by) == 1) 
				|| (abs(ax - bx) == 1 && abs(ay - by) == 2);
	}
	
	@Override
	public void refreshLists(Board board) {
		clearLists();
		Place place = null;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				place = new Place(i, j);
				if(isStepable(place, board)) {
					if(place != null && ( ! getStepables().contains(place))) {
						getStepables().add(place);
					}
				}
				if(isHitable(place, board)) {
					if(place != null && ( ! getHitables().contains(place))) {
						getHitables().add(place);
					}
				}
				if(isPossibleHitablePlace(place, board)) {
					if(place != null && ( ! getPossibleHitables().contains(place))) {
						getPossibleHitables().add(place);
					}
				}
			}
		}
	}
}
