package com.jockay.mchess.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.jockay.mchess.R;
import com.jockay.mchess.model.Constants;
import com.jockay.mchess.model.Place;

import java.lang.reflect.Method;

import static com.jockay.mchess.model.Constants.BRIGHT_SQUARE_COLOR;
import static com.jockay.mchess.model.Constants.DARK_SQUARE_COLOR;

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

	public static boolean isOdd(int n) {
		return ((n % 2) == 1);
	}


	public static boolean isEven(int number) {
		return (number % 2) == 0;
	}

	public static Place numToPlace(int index) {
        int i = (index / 8);
        int j = (index % 8);
		return new Place(i, j);
	}

	public static int dpToPixels(Context context, int sizeInDp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return ((int) (sizeInDp * scale + 0.5f));
	}

	public static Place getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int realWidth;
        int realHeight;

        if (Build.VERSION.SDK_INT >= 17){
            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            //reflection for this weird in-between time
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                //this may not be 100% accurate, but it's all we've got
                realWidth = display.getWidth();
                realHeight = display.getHeight();
                Log.e("Display Info", "Couldn't use reflection to get the real display metrics.");
            }

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            realWidth = display.getWidth();
            realHeight = display.getHeight();
        }

        return new Place(realWidth, realHeight);
    }


    public static int getSquareSize(Context context) {
        Place maxScreePoint = getScreenSize(context);
        return maxScreePoint.getX() / 8;
    }

    public static void setConstants(Context context) {
        Constants.SQUARE_SIZE = getSquareSize(context);
    }

    public static int getPlaceColor(Place place) {
        int x = place.getX();
        int y = place.getY();

        if(isEven(y)) {
            if( isOdd(x)) {
                return DARK_SQUARE_COLOR;
            } else {
                return BRIGHT_SQUARE_COLOR;
            }
        } else {
            if(isEven(x)) {
                return DARK_SQUARE_COLOR;
            } else {
                return BRIGHT_SQUARE_COLOR;
            }
        }
    }

    public static int getPlaceBackgroundResource(Place place) {
        int x = place.getX();
        int y = place.getY();

        if(isEven(y)) {
            if( isOdd(x)) {
                return R.drawable.border_dark;
            } else {
                return R.drawable.border_bright;
            }
        } else {
            if(isEven(x)) {
                return R.drawable.border_dark;
            } else {
                return R.drawable.border_bright;
            }
        }
    }

}
