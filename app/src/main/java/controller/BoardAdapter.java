package controller;

import static model.Constants.*;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by kerekgyarto.jozsef on 2017.09.21..
 */

public class BoardAdapter extends BaseAdapter {

    private Context CTX;

    private Integer imageId[] = {

            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,



            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,


            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
            android.R.color.black,
            android.R.color.darker_gray,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black,
                    android.R.color.darker_gray,
                    android.R.color.black
    };

    public BoardAdapter(Context CTX) {
        this.CTX = CTX;
    }

    @Override
    public int getCount() {
        return imageId.length;
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
        ImageView iv;
        if(view == null) {
            iv = new ImageView(CTX);
            iv.setLayoutParams(new GridView.LayoutParams(IMAGE_SIZE, IMAGE_SIZE));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(SQUARE_PADDING, SQUARE_PADDING, SQUARE_PADDING, SQUARE_PADDING);
        } else {
            iv = (ImageView) view;
        }
        iv.setImageResource(getBoardElement(i));
        return iv;
    }

    public Integer getBoardElement(int index) {
//        int i = (index / 8);
//        int j = (index % 8);
        return imageId[index];
    }

    public Integer getBoardElement(int i, int j) {
        return imageId[(i * 8) + (j)];
    }
}
