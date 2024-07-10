package com.example.exe1.Fragments;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exe1.Utillities.Player;
import com.example.exe1.R;

public class fragmentActivity extends AppCompatActivity implements ListFragment.OnPlayerSelectedListener {

    private FrameLayout main_FRAME_map;
    private FrameLayout main_FRAME_list;
    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment);
        findView();
        initView();
    }

    private void findView() {
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        listFragment = new ListFragment();
        listFragment.setOnPlayerSelectedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }

    private void initView() {
    }

    @Override
    public void onPlayerSelected(Player player) {
        if (mapFragment != null) {
            mapFragment.zoom(player.getLatitude(), player.getLongitude());
        }
    }
}
