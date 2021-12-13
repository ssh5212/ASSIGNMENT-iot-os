package com.cookandroid.iot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

public class cloth_add extends AppCompatActivity implements Serializable {
    RadioButton outer_check, top_check, bot_check;
    RadioButton Summer, Winter, Spring_Fall;
    RadioGroup cloth_value, cloth_season;
    Spinner spinner;
    EditText cloth_name;
    private final int REQUEST_CODE_BRAVO = 100;

    int set;

    final String[] check = new String[2];
    final String[] Summer_TopArr = {"반팔 티셔츠"};
    final String[] Summer_BotArr = {"반바지", "긴바지"};
    final String[] Spring_Fall_TopArr = {"긴팔 티셔츠", "후드", "반팔 티셔츠"};
    final String[] Spring_Fall_BotArr = {"반바지", "긴바지"};
    final String[] Winter_TopArr = {"긴팔 티셔츠", "후드"};
    final String[] Winter_BotArr = {"긴바지"};
    final String[] OuterArr = {"패딩", "자켓", "코트", "털옷"};

    final String[] Spinner_list ={};
    private boolean isFirstSelected = true; // 전역변수로 선언
    private Intent intent;
    public Spinner getSpinner() {
        return spinner;
    }

    final String TAG = this.getClass().getSimpleName();
    LinearLayout home_ly; BottomNavigationView bottom_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloth_add_main);
        outer_check = (RadioButton) findViewById(R.id.outer_check);
        top_check = (RadioButton) findViewById(R.id.top_check);
        bot_check = (RadioButton) findViewById(R.id.bot_check);
        Summer = (RadioButton) findViewById(R.id.summer);
        Winter = (RadioButton) findViewById(R.id.winter);
        Spring_Fall = findViewById(R.id.spring_fall);
        spinner = (Spinner) findViewById(R.id.spinner);
        cloth_value = (RadioGroup) findViewById(R.id.cloth_value);
        cloth_season = (RadioGroup) findViewById(R.id.cloth_season);
        EditText cloth_name = (EditText) findViewById(R.id.cloth_name);
        String cloth_check;

        init();
        SettingListener();
        bottom_menu.setSelectedItemId(R.id.home);

        Summer.setChecked(true);
        top_check.setChecked(true);


        check[0] = "summer";
        check[1] = "top";
        setSpinner();

        cloth_season.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.winter:
                        check[0] = "winter";
                        setSpinner();
                        break;
                    case R.id.summer:
                        check[0] = "summer";
                        setSpinner();
                        break;
                    case R.id.spring_fall:
                        check[0] = "spring_fall";
                        setSpinner();
                        break;
                    default:
                        break;
                }
            }
        });

        cloth_value.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.top_check:
                        check[1] = "top";
                        setSpinner();
                        break;
                    case R.id.bot_check:
                        check[1] = "bot";
                        setSpinner();
                        break;
                    case R.id.outer_check:
                        check[1] = "outer";
                        Summer.setChecked(false);
                        Winter.setChecked(false);
                        Spring_Fall.setChecked(false);
                        setSpinner();
                        break;
                    default:
                        break;
                }

            }
        });

        Button btnSend = (Button) findViewById(R.id.btnSend);
        TextView send = (TextView) findViewById((R.id.behind));
        Intent Send_Intent = new Intent(cloth_add.this, cloth_design.class);
        btnSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(set){
                    case 1:
                        send.setText(spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("outer", (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    case 2:
                        send.setText((String) spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("SummerTop", (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    case 3:
                        send.setText((String) spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("WinterTop",  (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    case 4:
                        send.setText((String) spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("Spring_FallTop",  (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    case 5:
                        send.setText((String) spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("SummerBot",  (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    case 6:
                        send.setText((String) spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("WinterBot",  (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    case 7:
                        send.setText((String) spinner.getSelectedItem().toString());
                        Send_Intent.putExtra("Spring_FallBot",  (String) send.getText());
                        Send_Intent.putExtra("case", set);
                        Send_Intent.putExtra("name", cloth_name.getText().toString());
                        startActivity(Send_Intent);
                        finish();
                        break;
                    default:
                        finish();
                        break;
                }
                //startActivity(Send_Intent);
            }
        });
    }
    public void setSpinner() {
        this.spinner = spinner;
        if(check[1].equals("outer")) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, OuterArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            set = 1;
            spinner.setAdapter(adapter);
        } else if(check[0].equals("summer")  == true && check[1].equals("top") == true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, Summer_TopArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            set = 2;
        } else if(check[0].equals("winter")  == true && check[1].equals("top") == true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, Winter_TopArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            set = 3;
        } else if(check[0].equals("spring_fall")  == true && check[1].equals("top") == true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, Spring_Fall_TopArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            set = 4;
        } else if(check[0].equals("summer")  == true && check[1].equals("bot") == true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, Summer_BotArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            set = 5;
        } else if(check[0].equals("winter")  == true && check[1].equals("bot") == true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, Winter_BotArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            set = 6;
        } else if(check[0].equals("spring_fall")  == true && check[1].equals("bot") == true) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_dropdown_item, Spring_Fall_BotArr);
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            set = 7;
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isFirstSelected) {
                    isFirstSelected = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottom_menu = findViewById(R.id.bottom_menu);
    }

    private void SettingListener() {
        bottom_menu.setOnItemSelectedListener(new cloth_add.TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnItemSelectedListener {
        Intent menu;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_graph: {
                    menu = new Intent(cloth_add.this, MainActivity.class);
                    startActivity(menu);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new graphFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_cloth: {
                    menu = new Intent(cloth_add.this, cloth_design.class);
                    startActivity(menu);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new clothFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_creator: {
                    menu = new Intent(cloth_add.this, creator.class);
                    startActivity(menu);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new creatorFragment()).commit();
                    finish();
                    return true;
                }
            }
            return false;
        }
    }
}