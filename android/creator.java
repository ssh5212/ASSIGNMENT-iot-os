package com.cookandroid.iot;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class creator extends AppCompatActivity {

    Intent intent;
    final String TAG = this.getClass().getSimpleName();
    LinearLayout home_ly; BottomNavigationView bottom_menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creator_main);

        init();
        SettingListener();
        bottom_menu.setSelectedItemId(R.id.home);
    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottom_menu = findViewById(R.id.bottom_menu);
    }

    private void SettingListener() {
        bottom_menu.setOnItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_graph: {
                    intent = new Intent(creator.this, MainActivity.class);
                    startActivity(intent);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new graphFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_cloth: {
                    intent = new Intent(creator.this, cloth_design.class);
                    startActivity(intent);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new clothFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_creator: {
                    intent = new Intent(creator.this, creator.class);
                    startActivity(intent);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new creatorFragment()).commit();
                    finish();
                    return true;
                }
            }
            return false;
        }
    }
}

