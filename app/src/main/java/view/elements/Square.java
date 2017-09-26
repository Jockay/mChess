package view.elements;

import android.content.Context;
import android.view.View;

import model.Place;

import static controller.Util.isOdd;

/**
 * Created by kerekgyarto.jozsef on 2017.09.22..
 */

public class Square extends View {

    private int color;
    private Place place;

    public Square(Context context) {
        super(context);
    }

    public Square(Context context, Place place) {
        super(context);
        this.place = place;
        if(isOdd(place.getX())) {
            this.color = android.R.color.black;
        } else {
            this.color = android.R.color.darker_gray;
        }
    }



}
