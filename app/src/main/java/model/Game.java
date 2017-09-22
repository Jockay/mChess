package model;

import static model.Constants.BLACK;
import static model.Constants.CHECK;
import static model.Constants.CHECKMATE;
import static model.Constants.DRAWN;
import static model.Constants.HIT_DONE;
import static model.Constants.NOT_MOVEABLE;
import static model.Constants.NO_PIECE_FOUND;
import static model.Constants.PROMOTION_REQUIERED;
import static model.Constants.STEP_DONE;
import static model.Constants.WHITE;

import java.util.ArrayList;
import java.util.List;

import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;
import model.pieces.Rook;

public class Game {
	private Board   board;
	private Player  p1;
	private Player  p2;
	private boolean isCheckmate;
	
	public Game(Board board, Player playerA, Player playerB) {
		super();
		this.board = board;
		this.p1 = playerA;
		this.p2 = playerB;
		this.isCheckmate = false;
		refreshPiecesOnBoard();
	}
	
	/**
	 * 
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int tryMove(Place a, Place b) {
		Piece  piece = searchPieceOnBoard(a);
		if(piece == null) 
		{ return NO_PIECE_FOUND; }
		Place positionSave = piece.getPosition();
		
		if(isDrawn()) 
		{ return DRAWN; }
		
		if(isCheckmate()) 
		{ return CHECKMATE; }
		
		if(piece instanceof King) {
			Player notActual = getNotActualPlayer();
			return tryKingMove(notActual, piece, b);
		} else {
			if(piece.isStepable(b, board)) {
//				if(isCheck()) {
//					return CHECK;
//				}
				doStep(piece, b);
				if(isCheck()) {
					// visszalépés
					doStep(piece, positionSave);
					return CHECK;
				}
				
				if(piece instanceof Pawn) {
					if(isPromotionRequiered(piece)) {
						return PROMOTION_REQUIERED;
					}
					piece.setIsFirstMove(false);
				}
				
//				if(isCheckmate()) {
//					return CHECKMATE;
//				}
				
//				if(isCheck()) {
//					return CHECK;
//				}
				switchActualPlayer();
//				if(isCheck()) {
//					refreshPiecesOnBoard();
//					return CHECK;
//				}
				refreshPiecesOnBoard();
				return STEP_DONE;
			} else if(piece.isHitable(b, board)) {
				Piece toHit = searchPieceOnBoard(b);
				doHit(piece, b);
				if(isCheck()) {
					revertHit(positionSave, piece, toHit);
					return CHECK;
				}
				if(piece instanceof Pawn) {
					if(isPromotionRequiered(piece)) {
						return PROMOTION_REQUIERED;
					}
				}
//				if(isCheckmate()) {
//					setIsCheckmate(true);
//					return CHECKMATE;
//				}
				
//				if(isCheck()) {
//					return CHECK;
//				}
				switchActualPlayer();
				
//				if(isCheck()) {
////					refreshPiecesOnBoard();
//					return CHECK;
//				}
				refreshPiecesOnBoard();
				return HIT_DONE;
			}
			
			return NOT_MOVEABLE;
		}
	}
	
	private void refreshPiecesOnBoardWithoutActualKing(Piece piece) {
		if((piece instanceof King) == false )
			throw new IllegalArgumentException();
		
		Piece pieceSave = piece;
		Place placeSave = piece.getPosition();
		
		board.setValueZero(piece.getPosition());
		getActualPlayer().getPiecesOnBoard().remove(piece);
		refreshPiecesOnBoard();
		getActualPlayer().getPiecesOnBoard().add(pieceSave);
		board.setValue(pieceSave, placeSave);
	}
	
	private int tryKingMove(Player notActuall, Piece piece, Place b) {
		if( ! (piece instanceof King)) {
			throw new IllegalArgumentException();
		}
		refreshPiecesOnBoardWithoutActualKing(piece);
		if(piece.isStepable(notActuall, b, board)) {
			doStep(piece, b);
			switchActualPlayer();
			return STEP_DONE;
		} else if(piece.isHitable(notActuall, b, board)) {
			doHit(piece, b);
			switchActualPlayer();
			return HIT_DONE;
		} 
		
		refreshPiecesOnBoard();
		
		if(isCheckmate()) {
			return CHECKMATE;
		}
		
		if(isCheck()) {
			return CHECK;
		} else {
			return NOT_MOVEABLE;
		}
	}

	
	public void doStep(Piece piece, Place b) {
		board.setValueZero(piece.getPosition());
		board.setValue(piece, b);
		refreshPiecesOnBoard();
	}
	
	public void doHit(Piece piece, Place b) {
		Piece toHit = searchPieceOnBoard(b);
		Place pieceLastPlace = piece.getPosition();
		getActualPlayer().addPieceTakenDown(toHit);
		getNotActualPlayer().removePieceOnBoard(toHit);
		board.setValue(piece, b);
		board.setValueZero(pieceLastPlace);
		refreshPiecesOnBoard();
	}
	
	public void revertHit(Place lastOnBoardPosition, Piece pOnBoard, Piece pTakenDown) {
		Player actual = getActualPlayer();
		Player notActual = getNotActualPlayer();
		
		board.setValue(pOnBoard,   lastOnBoardPosition);
		board.setValue(pTakenDown, pTakenDown.getPosition());
		
		notActual.getPiecesOnBoard().add(pTakenDown);
		actual.getPiecesTakenDown().remove(pTakenDown);
		
		refreshPiecesOnBoard();
	}
	
	
	public boolean isPromotionRequiered(Piece piece) {
		Place b = piece.getPosition();
		if( (piece instanceof Pawn) == false ) {
			return false;
		}
		
		if(piece.getColor().equals(WHITE)) {
			if(b.getY() == 0) {
				return true;
			}
		} else if(piece.getColor().equals(BLACK)) {
			if(b.getY() == 7) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCheck() {
		Piece king = getActualPlayer().getKing();
		for(Piece piece : getNotActualPlayerPiecesOnBoard()) {
			if(piece.getPossibleHitables().contains(king.getPosition())) {
				getActualPlayer().setInCheck(true);
				return true;
			}
		}
		getActualPlayer().setInCheck(false);
		return false;
	}
	
//	public boolean isResolveCheck(Piece piece, Place b) {
//		Game g = this;
//		Piece p = g.searchPieceOnBoard(piece.getPosition());
//		
////		if(g.tryMove(piece.getPosition(), b));
//		return false;
//	}
	
	public List<Piece> getPiecesChecks() {
		List<Piece>	result = new ArrayList<Piece>();
		Piece king = getActualPlayer().getKing();
		for(Piece piece : getNotActualPlayerPiecesOnBoard()) {
			if(piece.getHitables().contains(king.getPosition())) {
				result.add(piece);
			}
		}
		return result;
	}
	
	public boolean isPiecesChecksHitable() {
		List<Piece> piecesChecks = getPiecesChecks(); 
		for(Piece piece : getActualPlayerPiecesOnBoard()) {
			for(Piece checkerPiece : piecesChecks) {
				if(piece.getHitables().contains(checkerPiece.getPosition())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isCheckmate() {
		Piece king = getActualPlayer().getKing();
		if(isCheck() 
				&& king.getStepables().size() == 0
				&& (isPiecesChecksHitable() == false)) {
			setIsCheckmate(true);
			return true;
		}
		
//		king = getNotActualPlayer().getKing();
//		if(isCheck() 
//				&& king.getStepables().size() == 0) {
//			setIsCheckmate(true);
//			return true;
//		}
		
		return false;
	}
	
	public boolean isDrawn() {
		if(getActualPlayer().getPiecesOnBoard().size() == 1 
				&& getNotActualPlayer().getPiecesOnBoard().size() == 1) {
			return true;
		}
		return false;
	}
	
	public Piece searchPieceOnBoard(Place a) {
		List<Piece> playerAPieces = getPlayerA().getPiecesOnBoard();
		List<Piece> playerBPieces = getPlayerB().getPiecesOnBoard();
		
		for(Piece piece : playerAPieces) {
			if(piece.getPosition().equals(a)) {
				return piece;
			}
		}
		
		for(Piece piece : playerBPieces) {
			if(piece.getPosition().equals(a)) {
				return piece;
			}
		}
		
		return null;
	}
	
	public List<Piece> getPromotionList(Piece pawn) {
		List<Piece> result = new ArrayList<>();
		String actColor = getActualPlayerColor();
		
		result.add(new Rook(new Place(0, 0), actColor));
		result.add(new Knight(new Place(0, 0), actColor));
		result.add(new Bishop(new Place(0, 0), actColor));
		result.add(new Queen(new Place(0, 0), actColor));
		
		return result;
	}
	
	public void doPromotion(Piece pawn, Piece pieceToPromote) {
		getActualPlayerPiecesOnBoard().remove(pawn);
		
//		Piece pieceToPromote = getPromotionList().get(index);
//		getActualPlayerPiecesTakenDown().remove(pieceToPromote);
		
		pieceToPromote.setPosition(pawn.getPosition());
		getActualPlayerPiecesOnBoard().add(pieceToPromote);
		
		board.setValue(pieceToPromote, pieceToPromote.getPosition());
		switchActualPlayer();
	}
	
	public void refreshPiecesOnBoard() {
		getNotActualPlayer().refreshPiecesOnBoardLists(getActualPlayer(), board);
		getActualPlayer().refreshPiecesOnBoardLists(getNotActualPlayer(), board);
//		getNotActualPlayer().refreshPiecesOnBoardLists(getActualPlayer(), board);
//		getActualPlayer().refreshPiecesOnBoardLists(getNotActualPlayer(), board);
	}
	
	public void switchActualPlayer() {
		p1.setActual( ! p1.isActual());
		p2.setActual( ! p2.isActual());
	}
	
	public Player getActualPlayer() {
		return p1.isActual() ? p1 : p2;
	}
	public String getActualPlayerName() {
		return getActualPlayer().getName();
	}
	public List<Piece> getActualPlayerPiecesOnBoard() {
		return getActualPlayer().getPiecesOnBoard();
	}
	public List<Piece> getActualPlayerPiecesTakenDown() {
		return getNotActualPlayer().getPiecesTakenDown();
	}
	
	public String getActualPlayerColor() {
		return getActualPlayer().getColor();
	}
	
	public Player getNotActualPlayer() {
		return p1.isActual() ? p2 : p1;
	}
	public String getNotActualPlayerName() {
		return getNotActualPlayer().getName();
	}
	public List<Piece> getNotActualPlayerPiecesOnBoard() {
		return getNotActualPlayer().getPiecesOnBoard();
	}
	public List<Piece> getNotActualPlayerPiecesTakenDown() {
		return getActualPlayer().getPiecesTakenDown();
	}
	
	public int getBoardValue(Place place) {
		return getBoard().getValue(place);
	}
	
	public boolean isInActualValueGroup(int value) {
		String actColor = getActualPlayerColor(); 
		if(actColor.equals(WHITE)
				&& (value >= 11 && value <= 16) ) {
				return true;
		} else if(actColor.equals(BLACK)
				&& (value >= 21 && value <= 26) ) {
			return true;
		}
		return false;
	}
	
	public void newGame() {
		Board boardNew = new Board();
		setBoard(boardNew);
		setP1(new Player(getP1().getName(), getP1().getColor(), boardNew));
		setP2(new Player(getP2().getName(), getP2().getColor(), boardNew));
		refreshPiecesOnBoard();
	}
	
	public Piece createPiece(int value, int x, int y) {
		switch (value) {
			case 11:
				return new Pawn(new Place(x, y), WHITE);
			case 12:
				return new Rook(new Place(x, y), WHITE);
			case 13:
				return new Knight(new Place(x, y), WHITE);
			case 14:
				return new Bishop(new Place(x, y), WHITE);
			case 15:
				return new Queen(new Place(x, y), WHITE);
			case 16:
				return new King  (new Place(x, y), WHITE);
			case 21:
				return new Pawn  (new Place(x, y), BLACK);
			case 22:
			    return new Rook  (new Place(x, y), BLACK);
			case 23:
				return new Knight(new Place(x, y), BLACK);
			case 24:
				return new Bishop(new Place(x, y), BLACK);
			case 25:
				return new Queen(new Place(x, y), BLACK);
			case 26:
				return new King(new Place(x, y), BLACK);
			default:
				return null;
		}
	}
	
	public Piece createPiece(int value, int x, int y, boolean isFirstMove) {
		Piece piece = null;
		switch (value) {
			case 11:
				piece = new Pawn(new Place(x, y), WHITE);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 12:
				piece = new Rook(new Place(x, y), WHITE);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 13:
				piece = new Knight(new Place(x, y), WHITE);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 14:
				piece = new Bishop(new Place(x, y), WHITE);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 15:
				piece = new Queen(new Place(x, y), WHITE);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 16:
				piece = new King  (new Place(x, y), WHITE);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 21:
				piece = new Pawn  (new Place(x, y), BLACK);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 22:
				piece = new Rook  (new Place(x, y), BLACK);
			    piece.setIsFirstMove(isFirstMove);
				return piece;
			case 23:
				piece = new Knight(new Place(x, y), BLACK);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 24:
				piece = new Bishop(new Place(x, y), BLACK);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 25:
				piece = new Queen(new Place(x, y), BLACK);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			case 26:
				piece = new King(new Place(x, y), BLACK);
				piece.setIsFirstMove(isFirstMove);
				return piece;
			default:
				return piece;
		}
	}
	
	public void clearGame() {
		Integer[][] emptyBoardMatrix =  new Integer[][] {
//    		  0   1   2   3   4   5   6   7
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 0
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 1
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 2
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 3
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 4
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 5
			{ 0,  0,  0,  0,  0,  0,  0,  0}, // 6
			{ 0,  0,  0,  0,  0,  0,  0,  0}  // 7
		};
		Board emptyBoard = new Board();
		emptyBoard.setBoard(emptyBoardMatrix);
		setBoard(emptyBoard);
		
		getP1().getPiecesOnBoard().clear();
		getP1().getPiecesTakenDown().clear();
		
		getP2().getPiecesOnBoard().clear();
		getP2().getPiecesTakenDown().clear();
	}
	
	// GETTERS and SETTERS
	public Player getP1() {
		return this.p1;
	}
	
	public Player getP2() {
		return this.p2;
	}
	
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Player getPlayerA() {
		return p1;
	}
	public void setPlayerA(Player playerA) {
		this.p1 = playerA;
	}
	public Player getPlayerB() {
		return p2;
	}
	public void setPlayerB(Player playerB) {
		this.p2 = playerB;
	}
	public boolean getIsCheckmate() {
		return isCheckmate;
	}
	public void setIsCheckmate(boolean isGameOver) {
		this.isCheckmate = isGameOver;
	}
	
}
