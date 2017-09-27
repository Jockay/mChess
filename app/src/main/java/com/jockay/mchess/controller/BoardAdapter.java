package com.jockay.mchess.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jockay.mchess.model.Place;
import com.jockay.mchess.view.elements.Square;

import java.util.ArrayList;
import java.util.List;

import static com.jockay.mchess.controller.Util.numToPlace;
import static com.jockay.mchess.model.Constants.IMAGE_SIZE;
import static com.jockay.mchess.model.Constants.SQUARE_PADDING;

/**
 * Created by kerekgyarto.jozsef on 2017.09.26..
 */

public class BoardAdapter extends BaseAdapter {

    private Context CTX;

    private List<Square> squares;

    private void loadSquares() {
        this.squares = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                squares.add(new Square(CTX, new Place(i, j)));
            }
        }
    }

    public BoardAdapter(Context CTX) {
        this.CTX = CTX;
        loadSquares();
    }

    @Override
    public int getCount() {
        return squares.size();
    }

    @Override
    public Object getItem(int i) {
        try {
            return getBoardElement(i);
        } catch(Exception e) {
            e.printStackTrace();
            return getBoardElement(i - 1);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Square sq;
        if(view == null) {
            sq = new Square(CTX, numToPlace(i));
        } else {
            sq = getBoardElement(i);
        }
        return sq;
    }

    public Square getBoardElement(int index) {
        return squares.get(index);
    }

    public Square getBoardElement(int i, int j) {
        return squares.get((i * 8) + (j));
    }

}
