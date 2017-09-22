package view;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jockay.mchess.R;

import controller.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    private  GridView gvMain;
    private ImageAdapter ia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ia = new ImageAdapter(this);
        this.gvMain = (GridView) findViewById(R.id.gwMain);
        this.gvMain.setAdapter(ia);
        this.gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "You clicked on image " + i, Toast.LENGTH_SHORT).show();
            }
        });
        for(int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                Log.i("(" + i + ", " + j + ")", "" + ia.getBoardElement(i, j));
            }
        }
    }
}
