package controller;

import model.Place;

public class Util {
	
	public static void printMatrix(Integer[][] matrix) {
		for(int i = 0; i < 8; i++) {
			System.out.print("{ ");
			for(int j = 0; j < 8; j++) {
				if(j != 7 ) {
					System.out.printf("%2d, ", matrix[i][j]);
				} else {
					System.out.printf("%2d", matrix[i][j]);
				}
			}
			System.out.print(" }\n");
		}
	}
	
	public static boolean isOnBoard(Place a) {
		if(a == null) {
			return false;
		}
		
		int ax = a.getX();
		int ay = a.getY();
		
		if(ax > 7 || ax < 0 || ay > 7 || ay < 0) {
			return false;
		}
		
		return true;
	}
	
	public static void printTryResult(int tryResult) {
		switch (tryResult) {
		case -6:
			System.out.println("CHECKMATE");
			break;
		case -5:
			System.out.println("DRAWN");
			break;
		case -4:
			System.out.println("");
			break;
		case -3:
			System.out.println("CHECK");
			break;
		case -2:
			System.out.println("NOT_MOVEABLE");
			break;
		case -1:
			System.out.println("NO_PIECE_FOUND");
			break;
		case 0:
			System.out.println("STEP_DONE");
			break;
		case 1:
			System.out.println("HIT_DONE");
			break;
		case 2:
			System.out.println("");
			break;
		default:
			break;
		}
		
	}
	
//	public static String getTryResultString(int tryResult) {
//		switch (tryResult) {
//		case -6:
//			return "CHECKMATE";
//		case -5:
//			return "DRAWN";
//		case -4:
//			return "";
//		case -3:
//			return "CHECK";
//		case -2:
//			System.out.println("NOT_MOVEABLE");
//			break;
//		case -1:
//			System.out.println("NO_PIECE_FOUND");
//			break;
//		case 0:
//			System.out.println("STEP_DONE");
//			break;
//		case 1:
//			System.out.println("HIT_DONE");
//			break;
//		case 2:
//			System.out.println("");
//			break;
//		default:
//			break;
//		}
//		
//	}
	
}
