package model;

import static model.Constants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;
import model.pieces.Rook;

public class Player {
	
	private String name;
	private String color;
	private List<Piece> piecesOnBoard;
	private List<Piece> piecesTakenDown;
	private boolean isActual;
	private String side;
	private boolean isFirstMove;
	private boolean isInCheck;
	
	public Player(String name, String color, Board board) {
		super();
		this.name = name;
		this.color = color;
		this.side = this.color.equals(WHITE) ? DOWN : UP;
		
		if(side.equals(DOWN)) {
			this.piecesOnBoard = new ArrayList<>(Arrays.asList(
					new Pawn(new Place(0, 6), color),
					new Pawn  (new Place(1, 6), color),
					new Pawn  (new Place(2, 6), color),
					new Pawn  (new Place(3, 6), color),
					new Pawn  (new Place(4, 6), color),
					new Pawn  (new Place(5, 6), color),
					new Pawn  (new Place(6, 6), color),
					new Pawn  (new Place(7, 6), color),
					
					new Rook(new Place(0, 7), color),
					new Knight(new Place(1, 7), color),
					new Bishop(new Place(2, 7), color),
					new Queen(new Place(3, 7), color),
					new King(new Place(4, 7), color),
					new Bishop(new Place(5, 7), color),
					new Knight(new Place(6, 7), color),
					new Rook  (new Place(7, 7), color)
					));
		} else {
			this.piecesOnBoard = new ArrayList<>(Arrays.asList(
					new Pawn  (new Place(0, 1), color),
					new Pawn  (new Place(1, 1), color),
					new Pawn  (new Place(2, 1), color),
					new Pawn  (new Place(3, 1), color),
					new Pawn  (new Place(4, 1), color), 
					new Pawn  (new Place(5, 1), color),
					new Pawn  (new Place(6, 1), color),
					new Pawn  (new Place(7, 1), color),
					                         
					new Rook  (new Place(0, 0), color),
					new Knight(new Place(1, 0), color),
					new Bishop(new Place(2, 0), color),
					new Queen (new Place(3, 0), color),
					new King  (new Place(4, 0), color),
					new Bishop(new Place(5, 0), color),
					new Knight(new Place(6, 0), color),
					new Rook  (new Place(7, 0), color)
					));
		}
		this.piecesTakenDown = new ArrayList<>();
		this.isActual = color.equals(WHITE) ? true : false;
		this.isFirstMove = true;
		
//		for(Piece p : this.piecesOnBoard) {
//			p.refreshLists(board, isFirstMove);
//		}
	}
	
	public Piece getKing() {
		for(Piece piece : getPiecesOnBoard()) {
			if(piece instanceof King) {
				return piece;
			}
		}
		return null;
	}
	
//	public void refreshKingList(Player notActual, Board board) {
//		for(Piece piece : getPiecesOnBoard()) {
//			if(piece instanceof King) {
//				piece.refreshLists(notActual, board);
//			}
//		}
//	}
//	
	public void refreshPiecesOnBoardLists(Player notActual, Board board) {
		for(Piece piece : getPiecesOnBoard()) {
			piece.refreshLists(notActual, board);
		}
	}
	
	public void clearLists() {
		getPiecesOnBoard().clear();
		getPiecesTakenDown().clear();
	}
	
	public void addPieceTakenDown(Piece piece) {
		getPiecesTakenDown().add(piece);
	}
	
	public void removePieceTakenDown(Piece piece) {
		getPiecesTakenDown().remove(piece);
	}
	
	public void removePieceOnBoard(Piece piece) {
		getPiecesOnBoard().remove(piece);
	}
	
	// GETTERS and SETTERS
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}
	public void setPiecesOnBoard(List<Piece> piecesOnBoard) {
		this.piecesOnBoard = piecesOnBoard;
	}
	public List<Piece> getPiecesTakenDown() {
		return piecesTakenDown;
	}
	public void setPiecesTakenDown(List<Piece> piecesTakenDown) {
		this.piecesTakenDown = piecesTakenDown;
	}
	public boolean isActual() {
		return isActual;
	}
	public void setActual(boolean isActual) {
		this.isActual = isActual;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public boolean isFirstMove() {
		return isFirstMove;
	}

	public void setFirstMove(boolean isFirstMove) {
		this.isFirstMove = isFirstMove;
	}

	public boolean isInCheck() {
		return isInCheck;
	}

	public void setInCheck(boolean isInCheck) {
		this.isInCheck = isInCheck;
	}

	public void addPieceOnBoard(Piece piece) {
		getPiecesOnBoard().add(piece);
	}
	
}

