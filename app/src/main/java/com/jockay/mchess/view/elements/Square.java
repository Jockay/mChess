package com.jockay.mchess.view.elements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jockay.mchess.R;
import com.jockay.mchess.model.Place;

import static com.jockay.mchess.controller.Util.dpToPixels;
import static com.jockay.mchess.controller.Util.getScreenSize;
import static com.jockay.mchess.controller.Util.isOdd;
import static com.jockay.mchess.controller.Util.isEven;
import static com.jockay.mchess.controller.Util.numToPlace;
import static com.jockay.mchess.model.Constants.BRIGHT_SQUARE_COLOR;
import static com.jockay.mchess.model.Constants.DARK_SQUARE_COLOR;
import static com.jockay.mchess.model.Constants.IMAGE_SIZE;
import static com.jockay.mchess.model.Constants.SQUARE_PADDING;


/**
 * Created by kerekgyarto.jozsef on 2017.09.22..
 */

public class Square extends LinearLayout {

    private int color;
    private Place place;
    private View content;

    public Square(Context context) {
        super(context);
        setPadding(0, 0, 0, 0);
        setGravity(Gravity.CENTER);
        setMinimumWidth(0);
        setMinimumHeight(0);
    }

    public Square(Context context, Place place) {
        super(context);
        setPadding(0, 0, 0, 0);
        setGravity(Gravity.CENTER);
        setMinimumWidth(0);
        setMinimumHeight(0);
//        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        content = inflate(context, R.layout.square, null);
        addView(content);
//        initComponent(context);
        Place screenSize = getScreenSize(context);
        content.setLayoutParams(new LayoutParams(
                screenSize.getX() / 8,
                screenSize.getX() / 8
        ));
        getRootView().setLayoutParams(new android.widget.AbsListView.LayoutParams(screenSize.getX() / 8, screenSize.getX() / 8));

//        iv.setBackgroundResource(android.R.color.background_dark);
        this.place = place;
        int dark = DARK_SQUARE_COLOR;
        int bright = BRIGHT_SQUARE_COLOR;

        int x = place.getX();
        int y = place.getY();
        if(isEven(y)) {
            if( isOdd(x)) {
                this.color = dark;
                content.findViewById(R.id.square_background).setBackgroundColor(dark);
            } else {
                this.color = bright;
                content.findViewById(R.id.square_background).setBackgroundColor(bright);
            }
        } else {
            if(isEven(x)) {
                this.color = dark;
                content.findViewById(R.id.square_background).setBackgroundColor(dark);
            } else {
                this.color = bright;
                content.findViewById(R.id.square_background).setBackgroundColor(bright);
            }
        }

//        GridView.LayoutParams gl = new GridView.LayoutParams(
//                (int) (context.getResources().getDimension(R.dimen.square_size) / context.getResources().getDisplayMetrics().density),
//                (int) (context.getResources().getDimension(R.dimen.square_size) / context.getResources().getDisplayMetrics().density),
//                ViewGroup.LayoutParams.WRAP_CONTENT);

//        GridView.LayoutParams gl = new GridView.LayoutParams(
//                GridView.AUTO_FIT,
//                (int) context.getResources().getDimension(R.dimen.square_size),
//                ViewGroup.LayoutParams.WRAP_CONTENT);

//        setLayoutParams(gl);
//        setPadding(SQUARE_PADDING, SQUARE_PADDING, SQUARE_PADDING, SQUARE_PADDING);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        setBackgroundColor(this.color);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setSize(int newSize) {
        setLayoutParams(new LayoutParams(newSize, newSize));
    }

    private void initComponent(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.square, null, false);
        this.addView(v);

    }

}
