package model.pieces;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Constants;
import model.Place;
import model.Player;

public abstract class Piece {
	
	private int     id;
	private int     valueGroup;
	private int     value;
	private boolean isFirstMove;
	private String color;
	private String side;
	private Place position;
	private List<Place> stepables;
	private List<Place> hitables;
	private List<Place> hitablePlaces;
	
	public Piece(Place position, String color, int id) {
		super();
		this.id = id;
		this.color = color;
		this.side = this.color.equals(Constants.WHITE) ? Constants.DOWN : Constants.UP;
		this.position = position;
		this.stepables = new ArrayList<Place>();
		this.hitables = new ArrayList<Place>();
		this.hitablePlaces = new ArrayList<Place>();
		this.isFirstMove = true;
		this.value = this.color.equals(Constants.WHITE) ? this.id + 10 : this.id + 20;
		setValueGroup(color);
	}
	
	public boolean isHitable(Place b, Board board) {
		return false;
	}
	
	public boolean isHitable(Player notActual, Place b, Board board) {
		return false;
	}

	public boolean isStepable(Place b, Board board) {
		return false;
	}
	
	public boolean isStepable(Player notActual, Place b, Board board) {
		return false;
	}
	
	public boolean isPossibleHitablePlace(Place b, Board board) {
		return false;
	}
	
	public boolean isPossibleHitablePlace(Player notActual, Place b, Board board) {
		return false;
	}
	
	public void setValueGroup(String color) {
		if(color.equals(Constants.WHITE)) {
			this.valueGroup = 10;
		} else if(color.equals(Constants.BLACK)) {
			this.valueGroup = 20;
		}
	}
	
	public int getValueGroup() {
		return valueGroup;
	}
	
	public boolean isInSameColorValueGroup(int placeValue) {
		return placeValue > valueGroup && 
				placeValue < (valueGroup + 7);
	}

	public void clearHitAbles() {
		this.hitables.clear();
	}
	
	public void clearStepAbles() {
		this.stepables.clear();
	}
	
	public void clearLogicalStepAbles() {
		this.hitablePlaces.clear();
	}

	public void clearLists() {
		this.stepables.clear();
		this.hitables.clear();
		this.hitablePlaces.clear();
	}
	
	public void refreshLists(Player notActual, Board board) {
		clearLists();
		Place place = null;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				place = new Place(i, j);
				if(isStepable(place, board)) {
					if( ! getStepables().contains(place))
						getStepables().add(place);
				}
				if(isStepable(notActual, place, board)) {
					if( ! getStepables().contains(place))
						getStepables().add(place);
				}
				if(isHitable(place, board)) {
					if( ! getHitables().contains(place))
						getHitables().add(place);
				}
				if(isHitable(notActual, place, board)) {
					if( ! getHitables().contains(place))
						getHitables().add(place);
				}
				if(isPossibleHitablePlace(place, board)) {
					if( ! getPossibleHitables().contains(place))
						getPossibleHitables().add(place);
				}
				if(isPossibleHitablePlace(notActual, place, board)) {
					if( ! getPossibleHitables().contains(place))
						getPossibleHitables().add(place);
				}
			}
		}
	}
	
	// nincs király lépés frissítés
	public void refreshLists(Board board) {
		clearLists();
		Place place = null;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				place = new Place(i, j);
				if(isStepable(place, board)) {
					if( ! getStepables().contains(place))
						getStepables().add(place);
				}
				if(isHitable(place, board)) {
					if( ! getHitables().contains(place))
						getHitables().add(place);
				}
				if(isPossibleHitablePlace(place, board)) {
					if( ! getPossibleHitables().contains(place))
						getPossibleHitables().add(place);
				}
			}
		}
	}

	public boolean isKingByPlaceValue(int placeValue) {
		if( (placeValue - 10) == 6
				|| (placeValue - 20) == 6) {
			return true;
		}
		return false;
	}
	
	public boolean isBasicallyHitable(int placeValue) {
		if(placeValue == 0 
				|| isInSameColorValueGroup(placeValue)
				|| isKingByPlaceValue(placeValue)) {
			return false;
		}
		return true;
	}
	
	// GETTERS / SETTERS
	public List<Place> getStepables() {
		return stepables;
	}

	public void setStepAbles(List<Place> stepAbles) {
		this.stepables = stepAbles;
	}

	public List<Place> getHitables() {
		return hitables;
	}

	public void setHitAbles(List<Place> hitAbles) {
		this.hitables = hitAbles;
	}

	public List<Place> getPossibleHitables() {
		return hitablePlaces;
	}

	public void setPossibleHitables(List<Place> logicalStepAbles) {
		this.hitablePlaces = logicalStepAbles;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Place> getStepables(Board board) {
		return null;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public Place getPosition() {
		return position;
	}

	public void setPosition(Place position) {
		this.position = position;
	}

	public boolean isFirstMove() {
		return isFirstMove;
	}

	public void setIsFirstMove(boolean isFirstMove) {
		this.isFirstMove = isFirstMove;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int valueOnBoard) {
		this.value = valueOnBoard;
	}
	
}
