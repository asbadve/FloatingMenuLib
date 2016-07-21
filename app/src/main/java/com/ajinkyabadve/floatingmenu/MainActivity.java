package com.ajinkyabadve.floatingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ajinkyabadve.floatingmenulib.viewgroup.FabView;
import com.ajinkyabadve.floatingmenulib.viewgroup.FloatingActionMenuLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    FabView fabViewFirst, fabViewSecond, fabViewThird;
    FloatingActionMenuLayout floatingActionMenuLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_fab_view);

        fabViewFirst = (FabView) findViewById(R.id.fab_first);
        fabViewSecond = (FabView) findViewById(R.id.fab_second);
        fabViewThird = (FabView) findViewById(R.id.fab_third);

        fabViewFirst.setFabClickListener(new FabView.FabClickListener() {
            @Override
            public void onFabClick(View view) {
                Log.d(TAG, "onFabClick() fabViewFirst called with: " + "view = [" + view + "]");
                floatingActionMenuLayout.hideFabMenu();
            }
        });

        fabViewSecond.setFabClickListener(new FabView.FabClickListener() {
            @Override
            public void onFabClick(View view) {
                floatingActionMenuLayout.hideFabMenu();
                Log.d(TAG, "onFabClick() fabViewSecond called with: " + "view = [" + view + "]");
            }
        });

        floatingActionMenuLayout = (FloatingActionMenuLayout) findViewById(R.id.fab_menu);
        floatingActionMenuLayout.setFabMenuClickListener(new FloatingActionMenuLayout.FabMenuClickListener() {
            @Override
            public void onFabClick(View view, boolean isMenuExpand) {
                Log.d(TAG, "onFabClick() called with: " + "view = [" + view + "], isMenuExpand = [" + isMenuExpand + "]");
            }
        });


    }
}
