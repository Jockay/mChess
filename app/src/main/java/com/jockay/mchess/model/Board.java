package com.jockay.mchess.model;

import com.jockay.mchess.model.pieces.Piece;

public class Board {
	private Integer[][] board;
	
	public Board() {
//		if(downsideColor.equals(WHITE)) {
			board = new Integer[][] {
	//			    0   1   2   3   4   5   6   7
					{22, 23, 24, 25, 26, 24, 23, 22}, // 0
					{21, 21, 21, 21, 21, 21, 21, 21}, // 1
					{ 0,  0,  0,  0,  0,  0,  0,  0}, // 2
					{ 0,  0,  0,  0,  0,  0,  0,  0}, // 3
					{ 0,  0,  0,  0,  0,  0,  0,  0}, // 4
					{ 0,  0,  0,  0,  0,  0,  0,  0}, // 5
					{11, 11, 11, 11, 11, 11, 11, 11}, // 6
					{12, 13, 14, 15, 16, 14, 13, 12}  // 7
				};
		/*} else if(downsideColor.equals(BLACK)) {
			board = new Integer[][] {
//			    	  0   1   2   3   4   5   6   7
					{12, 13, 14, 16, 15, 14, 13, 12},  // 0
					{11, 11, 11, 11, 11, 11, 11, 11},  // 1
					{ 0,  0,  0,  0,  0,  0,  0,  0},  // 2
					{ 0,  0,  0,  0,  0,  0,  0,  0},  // 3
					{ 0,  0,  0,  0,  0,  0,  0,  0},  // 4
					{ 0,  0,  0,  0,  0,  0,  0,  0},  // 5
					{21, 21, 21, 21, 21, 21, 21, 21},  // 6
					{22, 23, 24, 26, 25, 24, 23, 22}   // 7
				};
		}*/
	}

	public Integer[][] getBoard() {
		return board;
	}

	public void setBoard(Integer[][] board) {
		this.board = board;
	}
	
	public Integer getValue(Place p) {
		return board[p.getY()][p.getX()];
	}
	
	public Integer setValue(int value, int x, int y) {
		return board[y][x] = value;
	}
	
	public void setValue(Piece piece, Place place) {
		board[place.getY()][place.getX()] = piece.getValue();
		piece.setPosition(place);
	}

	public void setValueZero(Place p) {
		board[p.getY()][p.getX()] = 0;
	}
}
