package com.jockay.mchess.model.pieces;

import static com.jockay.mchess.model.Constants.*;
import static java.lang.Math.abs;

import com.jockay.mchess.controller.Util;
import com.jockay.mchess.model.Board;
import com.jockay.mchess.model.Place;

public class Pawn extends Piece {
	
	public Pawn(Place position, String color) {
		super(position, color, PAWN);
	}

	@Override
	public boolean isHitable(Place b, Board board) {
		int placeValue = board.getValue(b);
		Place currentPosistion = getPosition();
		int ax = currentPosistion.getX();
		int ay = currentPosistion.getY();
		int bx = b.getX();
		int by = b.getY();
		
		if( ! isBasicallyHitable(placeValue)) {
			return false;
		}
		
		if(getSide().equals(DOWN)) {
			return (((ay - by) == 1) && (abs(ax - bx) == 1));
		} else if(getSide().equals(UP)) {
			return ((by - ay == 1) && (abs(bx - ax) == 1));
		}
		
		return false;
	}
	
	@Override
	public boolean isStepable(Place b, Board board) {
		int placeValue = board.getValue(b);
		Place a = getPosition();
		int ax = a.getX();
		int ay = a.getY();
		int bx = b.getX();
		int by = b.getY();
		
		if(placeValue != 0 || ! Util.isOnBoard(b)) {
			return false;
		}
		
		if(getSide().equals(DOWN)) {
			if(isFirstMove()) {
				return ((ay - by == 2) || (ay - by == 1)) && ((ax - bx) == 0) && (board.getValue(new Place(ax, ay - 1)) == 0);
			} else {
				return (ay - by == 1) && ((ax - bx) == 0);
			}
		}
		else if(getSide().equals(UP)) {
			if(isFirstMove()) {
				return ((by - ay == 2) || (by - ay == 1)) && ((bx - ax) == 0) && (board.getValue(new Place(ax, ay + 1)) == 0);
			} else {
				return ((by - ay == 1) && ((bx - ax) == 0));
			}
		}
		return false;
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
		
		if(getSide().equals(DOWN)) {
			return (((ay - by) == 1) && (abs(ax - bx) == 1));
		} else if(getSide().equals(UP)) {
			return ((by - ay == 1) && (abs(bx - ax) == 1));
		} else {
			return false;
		}
	}
	
//	public void refreshHitables(Board board) {
//		clearHitAbles();
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 8; j++) {
//				Place place = new Place(i, j);
//				if(isHitable(place, board)) {
//					getHitAbles().add(place);
//				}
//			}
//		}
//	}
//	
//	public void refreshStepables(Board board, boolean isPlayersFirstMove) {
//		clearStepAbles();
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 8; j++) {
//				Place place = new Place(i, j);
//				if(isStepable(place, board, isPlayersFirstMove)) {
//					getPossibleHitables().add(place);
//				}
//			}
//		}
//	}
//	
//	public void refreshLists(Board board, boolean isPlayersFirstMove) {
//		refreshHitables(board);
//		refreshStepables(board, isPlayersFirstMove);
//	}	
}