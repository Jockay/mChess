package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jockay.mchess.R;

import controller.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    private  GridView gvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gvMain = (GridView) findViewById(R.id.gwMain);
        this.gvMain.setAdapter(new ImageAdapter(this));
        this.gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), "You clicked on image " + i, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
