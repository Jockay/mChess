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
import com.jockay.mchess.view.elements.Square;

import static com.jockay.mchess.controller.Util.getPlaceBackgroundResource;
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
        this.ba = createBoardAdapter();
        this.gvMain = createBoard();

        this.ba.notifyDataSetChanged();
        this.gvMain.setAdapter(this.ba);

        this.ba.getBoardElement(1, 1).getChildAt(0).findViewById(R.id.square_background).setBackgroundResource(R.drawable.dark);
        clearButtons();
//        this.gvMain.invalidateViews();
//        this.gvMain.invalidate();

    }

    public void clearButtons() {
        for(Square s : ba.getSquares()) {
            Place actualPlace = s.getPlace();
//            s.getContent().findViewById(R.id.square_background).setBackgroundResource(getPlaceBackgroundResource(actualPlace, false));
            s.getContent().findViewById(R.id.square_background).setBackgroundResource(getPlaceBackgroundResource(actualPlace));
        }
    }

    public GridView createBoard() {
        Place screenSize = getScreenSize(getApplicationContext());
        this.gvMain = (GridView) findViewById(R.id.gwMain);
        this.gvMain.setLayoutParams(new android.widget.LinearLayout.LayoutParams(screenSize.getX(), screenSize.getX()));
//        this.gvMain.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(screenSize.getX(), screenSize.getX()));
        this.gvMain.setAdapter(ba);
//        this.gvMain.setDrawSelectorOnTop(true);
        this.gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clearButtons();
                Toast.makeText(getBaseContext(), "You clicked on image " + numToPlace(i), Toast.LENGTH_SHORT).show();
                Place clickedPlace = numToPlace(i);
//                view.setBackgroundResource(Util.getPlaceBackgroundResource(clickedPlace, true));
                view.setBackgroundResource(Util.getPlaceBackgroundResource(clickedPlace));
                view.setSelected(true);
                refreshBoard();
            }
        });
        return this.gvMain;
    }

    public BoardAdapter createBoardAdapter() {
        this.ba = new BoardAdapter(this);
//        ba.set
        return this.ba;
    }

    public void refreshBoard() {
        ba.notifyDataSetChanged();
        gvMain.invalidateViews();
        gvMain.invalidate();
        ba.notifyDataSetChanged();
    }

}
