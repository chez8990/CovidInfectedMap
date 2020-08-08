package com.example.covidinfrectedmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
//import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity{
//    private ViewPager viewPager;
//    private PagerAdapter pagerAdapter;

    private MapFragment mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Prevent keyboard popping up on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get fab button started
        FloatingActionButton fab = findViewById(R.id.toggle_list_button);
        fab.setOnClickListener(new onClickBehavior());

        // Populate infected building list on start
        ListView listView = findViewById(R.id.listView);

        UpdateInfectedList updateInfectedList = new UpdateInfectedList(this, listView);
        List<String> buildingList = updateInfectedList.makeRequest();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, buildingList);
        listView.setAdapter(arrayAdapter);

        // Search box functionality
//        EditText editText = findViewById(R.id.list_search);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                arrayAdapter.getFilter().filter(s);
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        // Mark locations on map
//        mMapFragment = new MapFragment();
////        ListFragment listFragment = new ListFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.map_output);
//        if (fragment == null){
//            Log.d("fragment", "Null");
//        } else {
//            fragmentManager.beginTransaction().remove(fragment).commit();
//        }
//        fragmentManager.beginTransaction().replace(R.id.map_output, mMapFragment).commit();
//        Log.d("locations", buildingList.toString());
//        mMapFragment.markLocations(buildingList);
    }
    private class onClickBehavior implements View.OnClickListener{
        Context mContext;
        public void onClickBehavior(Context context){
            mContext = context;
        }
        @Override
        public void onClick(View view) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int height = displayMetrics.heightPixels;
            int maxHeight = (int) (height*0.80);

            LinearLayout bottomSheet  = findViewById(R.id.bottom_sheet_behavior_id);
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//            bottomSheetBehavior.setPeekHeight(maxHeight);

            if (bottomSheetBehavior.getState() == bottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
            } else {
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_EXPANDED);
            }
        }
    }

}