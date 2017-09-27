package com.jockay.mchess.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.jockay.mchess.R;

import com.jockay.mchess.controller.BoardAdapter;
import com.jockay.mchess.controller.Util;
import com.jockay.mchess.model.Constants;
import com.jockay.mchess.model.Place;

import static com.jockay.mchess.controller.Util.getScreenSize;
import static com.jockay.mchess.controller.Util.numToPlace;

public class MainActivity extends AppCompatActivity {

    private GridView gvMain;
    private BoardAdapter ba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.setConstants(getApplicationContext());
        this.ba = new BoardAdapter(this);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
        fl.setMargins(1, 1, 1, 1);
//        this.ba.getBoardElement(1, 1).getChildAt(0).findViewById(R.id.square_background).setLayoutParams(fl);
//        this.ba.getBoardElement(1, 1).getChildAt(0).findViewById(R.id.square_background).setBackgroundResource(R.drawable.border_dark);
        Place screenSize = getScreenSize(getApplicationContext());

        this.gvMain = (GridView) findViewById(R.id.gwMain);
        this.gvMain.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(screenSize.getX(), screenSize.getX()));
        this.gvMain.setAdapter(ba);
//        this.gvMain.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        this.gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Place clickedPlace = numToPlace(i);
                view.setBackgroundResource(Util.getPlaceBackgroundResource(clickedPlace));
                Toast.makeText(getBaseContext(), "You clicked on image " + i, Toast.LENGTH_SHORT).show();
            }
        });
        for(int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                Log.i("(" + i + ", " + j + ")", "" + ba.getBoardElement(i, j));
            }
        }
    }
}
