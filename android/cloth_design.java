package com.cookandroid.iot;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.data.Entry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class cloth_design extends AppCompatActivity implements Serializable {
    int top_image = 0, bot_image = 0, outer_image = 0;
    final String[] SummerTop_List = {"shortt"};
    final String[] SummerBot_List = {"shortpants"};
    final String[] WinterTop_List = {"longt", "hoodt"};
    final String[] WinterBot_List = {"pants"};
    final String[] Spring_FallTop_List = {"longt", "hoodt", "shortt"};
    final String[] Spring_FallBot_List = {"pants", "shortpants"};
    final String[] Outer_List = {"padding", "jacket", "coat", "furcoat"};

    String[] SummerTop_Category_Trans = {};
    String[] SummerBot_Category_Trans = {};
    String[] WinterTop_Category_Trans = {};
    String[] WinterBot_Category_Trans = {};
    String[] Spring_FallTop_Category_Trans = {};
    String[] Spring_FallBot_Category_Trans = {};
    String[] Outer_Category_Trans = {};

    String[] SummerTop_Name_Trans = {};
    String[] SummerBot_Name_Trans = {};
    String[] WinterTop_Name_Trans = {};
    String[] WinterBot_Name_Trans = {};
    String[] Spring_FallTop_Name_Trans = {};
    String[] Spring_FallBot_Name_Trans = {};
    String[] Outer_Name_Trans = {};

    Double temper;

    private ArrayList<String> Outer_name = new ArrayList<String>();
    private ArrayList<String> SummerTop_name = new ArrayList<String>();
    private ArrayList<String> WinterTop_name = new ArrayList<String>();
    private ArrayList<String> Spring_FallTop_name = new ArrayList<String>();
    private ArrayList<String> SummerBot_name = new ArrayList<String>();
    private ArrayList<String> WinterBot_name = new ArrayList<String>();
    private ArrayList<String> Spring_FallBot_name = new ArrayList<String>();

    ArrayList<String> Outer_list = new ArrayList<>(Arrays.asList(Outer_List));
    ArrayList<String> SummerTop_list = new ArrayList<>(Arrays.asList(SummerTop_List));
    ArrayList<String> SummerBot_list = new ArrayList<>(Arrays.asList(SummerBot_List));
    ArrayList<String> WinterTop_list = new ArrayList<>(Arrays.asList(WinterTop_List));
    ArrayList<String> WinterBot_list = new ArrayList<>(Arrays.asList(WinterBot_List));
    ArrayList<String> Spring_FallTop_list = new ArrayList<>(Arrays.asList(Spring_FallTop_List));
    ArrayList<String> Spring_FallBot_list = new ArrayList<>(Arrays.asList(Spring_FallBot_List));

    final String TAG = this.getClass().getSimpleName();
    LinearLayout home_ly; BottomNavigationView bottom_menu;

    static String[] Split_Data = {};
    double[] nums;
    int che = 1;
    int check = 0;
    int time = 0;
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloth_design_main);
        Button Outer_Last, Outer_Next, TOP_Last, TOP_Next, BOT_Last, BOT_Next, Search;
        final String test;
        String receive_data;
        String receive_name;

        Search = (Button) findViewById(R.id.serach);
        Outer_Last = (Button) findViewById(R.id.Outer_Last);
        Outer_Next = (Button) findViewById(R.id.Outer_Next);

        TOP_Last = (Button) findViewById(R.id.TOP_Last);
        TOP_Next = (Button) findViewById(R.id.TOP_Next);

        BOT_Last = (Button) findViewById(R.id.BOT_Last);
        BOT_Next = (Button) findViewById(R.id.BOT_Next);

        ImageView Outer_Cloth = (ImageView) findViewById(R.id.Outer_Cloth);
        ImageView TOP_Cloth = (ImageView) findViewById(R.id.TOP_Cloth);
        ImageView BOT_Cloth = (ImageView) findViewById(R.id.BOT_Cloth);

        ImageView Outer_Character = (ImageView) findViewById(R.id.Outer_Character);
        ImageView Top_Character = (ImageView) findViewById(R.id.Top_Character);
        ImageView Bot_Character = (ImageView) findViewById(R.id.Bot_Character);

        TextView Outer_name_Text = (TextView) findViewById(R.id.Outer_name);
        TextView Outer_Category_Text = (TextView) findViewById(R.id.Outer_category);
        TextView Top_name_Text = (TextView) findViewById(R.id.Top_name);
        TextView Top_Category_Text = (TextView) findViewById(R.id.Top_category);
        TextView Bot_name_Text = (TextView) findViewById(R.id.Bot_name);
        TextView Bot_Category_Text = (TextView) findViewById(R.id.Bot_category);
        EditText temperatures = (EditText) findViewById(R.id.Temperatures);

        Intent receive = getIntent();
        Intent receive_temper = getIntent();

        init();
        SettingListener();
        bottom_menu.setSelectedItemId(R.id.home);

        text();
/*
        String[] Split_Data;
        double[] nums = null;
        FileInputStream GET_Data;
        String tmp = null;
        try {
            GET_Data = openFileInput("GetData");
            byte[] txt = new byte[500];
            GET_Data.read(txt);
            GET_Data.close();
            tmp = (new String(txt)).trim();
            Split_Data = tmp.split(" ");
            nums = Arrays.stream(Split_Data).mapToDouble(Double::parseDouble).toArray();
        } catch (IOException e) {

        }
 */

        time = Integer.parseInt(temperatures.getText().toString());
        String title;
        ArrayList<Entry> values = new ArrayList<>();
        title = receive_temper.getStringExtra("title");
        Split_Data = title.split(" ");
        nums = Arrays.stream(Split_Data).mapToDouble(Double::parseDouble).toArray();
        for (int i = 0; i < nums.length; i++) {
            values.add((new Entry(i, (float) nums[i])));
        }
        //temper = nums[time - 1];
        temper = Double.valueOf(Split_Data[time - 1]);

        if(temper > 5.0) {
            Outer_Last.setEnabled(false);
            Outer_Next.setEnabled(false);
            Outer_Character.setVisibility(View.INVISIBLE);
            Outer_Cloth.setImageResource(R.drawable.nullimage);
            Top_Character.setVisibility(View.VISIBLE);
        }
        if(temper <= 5.0){
            Outer_Last.setEnabled(true);
            Outer_Next.setEnabled(true);
            Top_Character.setVisibility(View.VISIBLE);
            Outer_Cloth.setImageResource(R.drawable.nullimage);
            Top_Character.setVisibility(View.INVISIBLE);
        }

        Search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                time = Integer.parseInt(temperatures.getText().toString());
                String title;
                ArrayList<Entry> values = new ArrayList<>();
                title = receive_temper.getStringExtra("title");
                Split_Data = title.split(" ");
                nums = Arrays.stream(Split_Data).mapToDouble(Double::parseDouble).toArray();
                for (int i = 0; i < nums.length; i++) {
                    values.add((new Entry(i, (float) nums[i])));
                }
                //temper = nums[time - 1];
                temper = Double.valueOf(Split_Data[time - 1]);
                if(temper > 5.0) {
                    Outer_Last.setEnabled(false);
                    Outer_Next.setEnabled(false);
                    Outer_Character.setVisibility(View.INVISIBLE);
                    Outer_Cloth.setImageResource(R.drawable.nullimage);
                    Top_Character.setVisibility(View.VISIBLE);
                }
                if(temper <= 5.0){
                    Outer_Last.setEnabled(true);
                    Outer_Next.setEnabled(true);
                    Top_Character.setVisibility(View.VISIBLE);
                    Outer_Cloth.setImageResource(R.drawable.nullimage);
                    Top_Character.setVisibility(View.INVISIBLE);
                }
            }
        });

        che = receive.getIntExtra("case", -1);
        FileInputStream inFs;
        switch(che) {
            case 1:
                receive_data = receive.getStringExtra("outer");
                String Outer_Category = new String();
                receive_name = receive.getStringExtra("name");
                String Outer_NameArr = new String();
                Outer_Category = "nullimage,";
                Outer_NameArr = "선택되지 않았습니다.,";

                //Outer_name.add((String)receive_data);
                try {
                    inFs = openFileInput("OuterList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    Outer_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                Outer_Category = Outer_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("OuterList",
                            Context.MODE_PRIVATE);
                    String str = Outer_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("OuterName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    Outer_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                Outer_NameArr = Outer_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("OuterName",
                            Context.MODE_PRIVATE);
                    String str = Outer_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                finish();
                text();
                break;
            case 2:
                receive_data = receive.getStringExtra("SummerTop");
                String SummerTop_Category = new String();
                receive_name = receive.getStringExtra("name");
                String SummerTop_NameArr = new String();
                SummerTop_Category = "nullimage,";
                SummerTop_NameArr = "선택되지 않았습니다.,";
                SummerTop_name.add((String)receive_data);

                try {
                    inFs = openFileInput("SummerTopList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    SummerTop_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                SummerTop_Category = SummerTop_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("SummerTopList",
                            Context.MODE_PRIVATE);
                    String str = SummerTop_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }

//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("SummerTopName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    SummerTop_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                SummerTop_NameArr = SummerTop_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("SummerTopName",
                            Context.MODE_PRIVATE);
                    String str = SummerTop_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                finish();
                break;
            case 3:
                receive_data = receive.getStringExtra("WinterTop");
                String WinterTop_Category = new String();
                receive_name = receive.getStringExtra("name");
                String WinterTop_NameArr = new String();
                WinterTop_Category = "nullimage,";
                WinterTop_NameArr = "선택되지 않았습니다.,";
                WinterTop_name.add((String)receive_data);

                try {
                    inFs = openFileInput("WinterTopList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    WinterTop_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                WinterTop_Category = WinterTop_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("WinterTopList",
                            Context.MODE_PRIVATE);
                    String str = WinterTop_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }

//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("WinterTopName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    WinterTop_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                WinterTop_NameArr = WinterTop_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("WinterTopName",
                            Context.MODE_PRIVATE);
                    String str = WinterTop_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                finish();;
                break;
            case 4:
                receive_data = receive.getStringExtra("Spring_FallTop");
                String Spring_FallTop_Category = new String();
                receive_name = receive.getStringExtra("name");
                String Spring_FallTop_NameArr = new String();
                Spring_FallTop_Category = "nullimage,";
                Spring_FallTop_NameArr = "선택되지 않았습니다.,";
                Spring_FallTop_name.add((String)receive_data);

                try {
                    inFs = openFileInput("Spring_FallTopList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    Spring_FallTop_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                Spring_FallTop_Category = Spring_FallTop_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("Spring_FallTopList",
                            Context.MODE_PRIVATE);
                    String str = Spring_FallTop_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }

//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("Spring_FallTopName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    Spring_FallTop_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                Spring_FallTop_NameArr = Spring_FallTop_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("Spring_FallTopName",
                            Context.MODE_PRIVATE);
                    String str = Spring_FallTop_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                break;
            case 5:
                receive_data = receive.getStringExtra("SummerBot");
                String SummerBot_Category = new String();
                receive_name = receive.getStringExtra("name");
                String SummerBot_NameArr = new String();
                SummerBot_Category = "nullimage,";
                SummerBot_NameArr = "선택되지 않았습니다.,";
                SummerBot_name.add((String)receive_data);

                try {
                    inFs = openFileInput("SummerBotList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    SummerBot_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                SummerBot_Category = SummerBot_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("SummerBotList",
                            Context.MODE_PRIVATE);
                    String str = SummerBot_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }

//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("SummerBotName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    SummerBot_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                SummerBot_NameArr = SummerBot_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("SummerBotName",
                            Context.MODE_PRIVATE);
                    String str = SummerBot_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                finish();
                break;
            case 6:
                receive_data = receive.getStringExtra("WinterBot");
                String WinterBot_Category = new String();
                receive_name = receive.getStringExtra("name");
                String WinterBot_NameArr = new String();
                WinterBot_Category = "nullimage,";
                WinterBot_NameArr = "선택되지 않았습니다.,";
                WinterBot_name.add((String)receive_data);

                try {
                    inFs = openFileInput("WinterBotList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    WinterBot_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                WinterBot_Category = WinterBot_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("WinterBotList",
                            Context.MODE_PRIVATE);
                    String str = WinterBot_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }

//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("WinterBotName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    WinterBot_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                WinterBot_NameArr = WinterBot_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("WinterBotName",
                            Context.MODE_PRIVATE);
                    String str = WinterBot_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                finish();
                break;
            case 7:
                receive_data = receive.getStringExtra("Spring_FallBot");
                String Spring_FallBot_Category = new String();
                receive_name = receive.getStringExtra("name");
                String Spring_FallBot_NameArr = new String();
                Spring_FallBot_Category = "nullimage,";
                Spring_FallBot_NameArr = "선택되지 않았습니다.,";
                Spring_FallBot_name.add((String)receive_data);

                try {
                    inFs = openFileInput("Spring_FallBotList");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    Spring_FallBot_Category = (new String(txt)).trim();
                } catch (IOException e) {

                }

                Spring_FallBot_Category = Spring_FallBot_Category + receive_data + ",";
                try {
                    FileOutputStream outFs = openFileOutput("Spring_FallBotList",
                            Context.MODE_PRIVATE);
                    String str = Spring_FallBot_Category.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }

//-------------------------------------------------------------------------------------------
                try {
                    inFs = openFileInput("Spring_FallBotName");
                    byte[] txt = new byte[500];
                    inFs.read(txt);
                    inFs.close();
                    Spring_FallBot_NameArr = (new String(txt)).trim();
                } catch (IOException e) {

                }

                Spring_FallBot_NameArr = Spring_FallBot_NameArr + receive_name + ",";
                try {
                    FileOutputStream outFs = openFileOutput("Spring_FallBotName",
                            Context.MODE_PRIVATE);
                    String str = Spring_FallBot_NameArr.toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                } catch (IOException e) {
                }
                finish();
                break;
            default:
        }
        try {
            Outer_Last.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (outer_image == 0) {
                        outer_image = Outer_Category_Trans.length - 1;
                        check = getResources().getIdentifier(Outer_Category_Trans[outer_image], "drawable", "com.cookandroid.iot");
                        Top_Character.setVisibility(View.INVISIBLE);
                    } else {
                        outer_image--;
                        check = getResources().getIdentifier(Outer_Category_Trans[outer_image], "drawable", "com.cookandroid.iot");
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    Outer_Cloth.setImageResource(check);
                    if(outer_image == 0) {
                        Outer_Character.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Outer_Character.setVisibility(View.VISIBLE);
                        Outer_Character.setImageResource(check);
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    Outer_Cloth.setImageResource(check);
                    Outer_name_Text.setText(Outer_Name_Trans[outer_image]);
                    Outer_Category_Text.setText(Outer_Category_Trans[outer_image]);
                    Top_Character.setVisibility(View.INVISIBLE);
                }
            });

            Outer_Next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (outer_image == Outer_Category_Trans.length - 1) {
                        outer_image = 0;
                        check = getResources().getIdentifier(Outer_Category_Trans[outer_image], "drawable", "com.cookandroid.iot");
                        Top_Character.setVisibility(View.INVISIBLE);
                    } else {
                        outer_image++;
                        check = getResources().getIdentifier(Outer_Category_Trans[outer_image], "drawable", "com.cookandroid.iot");
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    if(outer_image == 0) {
                        Outer_Character.setVisibility(View.INVISIBLE);
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Outer_Character.setVisibility(View.VISIBLE);
                        Outer_Character.setImageResource(check);
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    Outer_Cloth.setImageResource(check);
                    Outer_name_Text.setText(Outer_Name_Trans[outer_image]);
                    Outer_Category_Text.setText(Outer_Category_Trans[outer_image]);
                    Top_Character.setVisibility(View.INVISIBLE);

                }
            });

            TOP_Last.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(temper < 5.0) {
                        if (top_image == 0) {
                            top_image = WinterTop_Category_Trans.length - 1;
                            check = getResources().getIdentifier(WinterTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                            Top_Character.setVisibility(View.INVISIBLE);
                        } else {
                            top_image--;
                            check = getResources().getIdentifier(WinterTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        }
                        if(top_image == 0) {
                            Top_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Top_Character.setVisibility(View.VISIBLE);
                            Top_Character.setImageResource(check);
                        }
                        TOP_Cloth.setImageResource(check);
                        Top_name_Text.setText(WinterTop_Name_Trans[top_image]);
                        Top_Category_Text.setText(WinterTop_Category_Trans[top_image]);
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    else if(temper < 21.0) {
                        if (top_image == 0) {
                            top_image = Spring_FallTop_Category_Trans.length - 1;
                            check = getResources().getIdentifier(Spring_FallTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        } else {
                            top_image--;
                            check = getResources().getIdentifier(Spring_FallTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        }
                        if(top_image == 0) {
                            Top_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Top_Character.setVisibility(View.VISIBLE);
                            Top_Character.setImageResource(check);
                        }
                        TOP_Cloth.setImageResource(check);
                        Top_name_Text.setText(Spring_FallTop_Name_Trans[top_image]);
                        Top_Category_Text.setText(Spring_FallTop_Category_Trans[top_image]);
                        Top_Character.setVisibility(View.VISIBLE);
                    }
                    else {
                        if (top_image == 0) {
                            top_image = SummerTop_Category_Trans.length - 1;
                            check = getResources().getIdentifier(SummerTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        } else {
                            top_image--;
                            check = getResources().getIdentifier(SummerTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        }
                        if(top_image == 0) {
                            Top_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Top_Character.setVisibility(View.VISIBLE);
                            Top_Character.setImageResource(check);
                        }
                        TOP_Cloth.setImageResource(check);
                        Top_name_Text.setText(SummerTop_Name_Trans[top_image]);
                        Top_Category_Text.setText(SummerTop_Category_Trans[top_image]);
                        Top_Character.setVisibility(View.VISIBLE);
                    }
                }
            });

            BOT_Last.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(temper < 5.0) {
                        if (bot_image == 0) {
                            bot_image = WinterBot_Category_Trans.length - 1;
                            check = getResources().getIdentifier(WinterBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        } else {
                            bot_image--;
                            check = getResources().getIdentifier(WinterBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        }
                        if(bot_image == 0) {
                            Bot_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Bot_Character.setVisibility(View.VISIBLE);
                            Bot_Character.setImageResource(check);
                        }
                        BOT_Cloth.setImageResource(check);
                        Bot_name_Text.setText(WinterBot_Name_Trans[bot_image]);
                        Bot_Category_Text.setText(WinterBot_Category_Trans[bot_image]);
                    }
                    else if(temper < 21.0) {
                        if (bot_image == 0) {
                            bot_image = Spring_FallBot_Category_Trans.length - 1;
                            check = getResources().getIdentifier(Spring_FallBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        } else {
                            bot_image--;
                            check = getResources().getIdentifier(Spring_FallBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        }
                        if(bot_image == 0) {
                            Bot_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Bot_Character.setVisibility(View.VISIBLE);
                            Bot_Character.setImageResource(check);
                        }
                        BOT_Cloth.setImageResource(check);
                        Bot_name_Text.setText(Spring_FallBot_Name_Trans[bot_image]);
                        Bot_Category_Text.setText(Spring_FallBot_Category_Trans[bot_image]);
                    }
                    else {
                        if (bot_image == 0) {
                            bot_image = SummerBot_Category_Trans.length - 1;
                            check = getResources().getIdentifier(SummerBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        } else {
                            bot_image--;
                            check = getResources().getIdentifier(SummerBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        }
                        if(bot_image == 0) {
                            Bot_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Bot_Character.setVisibility(View.VISIBLE);
                            Bot_Character.setImageResource(check);
                        }
                        BOT_Cloth.setImageResource(check);
                        Bot_name_Text.setText(SummerBot_Name_Trans[bot_image]);
                        Bot_Category_Text.setText(SummerBot_Category_Trans[bot_image]);
                    }
                }
            });

            //하의
            BOT_Next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(temper < 5.0) {
                        if (bot_image == WinterBot_Category_Trans.length - 1) {
                            bot_image = 0;
                            check = getResources().getIdentifier(WinterBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        } else {
                            bot_image++;
                            check = getResources().getIdentifier(WinterBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        }
                        if(bot_image == 0) {
                            Bot_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Bot_Character.setVisibility(View.VISIBLE);
                            Bot_Character.setImageResource(check);
                        }
                        BOT_Cloth.setImageResource(check);
                        Bot_name_Text.setText(WinterBot_Name_Trans[bot_image]);
                        Bot_Category_Text.setText(WinterBot_Category_Trans[bot_image]);
                    }
                    else if(temper < 21.0) {
                        if (bot_image == Spring_FallBot_Category_Trans.length - 1) {
                            bot_image = 0;
                            check = getResources().getIdentifier(Spring_FallBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        } else {
                            bot_image++;
                            check = getResources().getIdentifier(Spring_FallBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        }
                        if(bot_image == 0) {
                            Bot_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Bot_Character.setVisibility(View.VISIBLE);
                            Bot_Character.setImageResource(check);
                        }
                        BOT_Cloth.setImageResource(check);
                        Bot_name_Text.setText(Spring_FallBot_Name_Trans[bot_image]);
                        Bot_Category_Text.setText(Spring_FallBot_Category_Trans[bot_image]);
                    }
                    else {
                        if (bot_image == SummerBot_Category_Trans.length - 1) {
                            bot_image = 0;
                            check = getResources().getIdentifier(SummerBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        } else {
                            bot_image++;
                            check = getResources().getIdentifier(SummerBot_Category_Trans[bot_image], "drawable", "com.cookandroid.iot");
                        }
                        if(bot_image == 0) {
                            Bot_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Bot_Character.setVisibility(View.VISIBLE);
                            Bot_Character.setImageResource(check);
                        }
                        BOT_Cloth.setImageResource(check);
                        Bot_name_Text.setText(SummerBot_Name_Trans[bot_image]);
                        Bot_Category_Text.setText(SummerBot_Category_Trans[bot_image]);
                    }
                }
            });

            TOP_Next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(temper < 5.0) {
                        if (top_image == WinterTop_Category_Trans.length - 1) {
                            top_image = 0;
                            check = getResources().getIdentifier(WinterTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        } else {
                            top_image++;
                            check = getResources().getIdentifier(WinterTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        }
                        if(top_image == 0) {
                            Top_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Top_Character.setVisibility(View.VISIBLE);
                            Top_Character.setImageResource(check);
                        }
                        TOP_Cloth.setImageResource(check);
                        Top_name_Text.setText(WinterTop_Name_Trans[top_image]);
                        Top_Category_Text.setText(WinterTop_Category_Trans[top_image]);
                        Top_Character.setVisibility(View.INVISIBLE);
                    }
                    else if(temper < 21.0) {
                        if (top_image == Spring_FallTop_Category_Trans.length - 1) {
                            top_image = 0;
                            check = getResources().getIdentifier(Spring_FallTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        } else {
                            top_image++;
                            check = getResources().getIdentifier(Spring_FallTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        }
                        if(top_image == 0) {
                            Top_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Top_Character.setVisibility(View.VISIBLE);
                            Top_Character.setImageResource(check);
                        }
                        TOP_Cloth.setImageResource(check);
                        Top_name_Text.setText(Spring_FallTop_Name_Trans[top_image]);
                        Top_Category_Text.setText(Spring_FallTop_Category_Trans[top_image]);
                        Top_Character.setVisibility(View.VISIBLE);
                    }
                    else {
                        if (top_image == SummerTop_Category_Trans.length - 1) {
                            top_image = 0;
                            check = getResources().getIdentifier(SummerTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        } else {
                            top_image++;
                            check = getResources().getIdentifier(SummerTop_Category_Trans[top_image], "drawable", "com.cookandroid.iot");
                        }
                        if(top_image == 0) {
                            Top_Character.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Top_Character.setVisibility(View.VISIBLE);
                            Top_Character.setImageResource(check);
                        }
                        TOP_Cloth.setImageResource(check);
                        Top_name_Text.setText(SummerTop_Name_Trans[top_image]);
                        Top_Category_Text.setText(SummerTop_Category_Trans[top_image]);
                        Top_Character.setVisibility(View.VISIBLE);
                    }
                }
            });

        }catch (Exception a) {

        }
        Button button_add = (Button) findViewById(R.id.add) ;
        button_add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cloth_design.this, cloth_add.class);

                startActivity(intent);
            }
        });
    }


    public void text() {
        FileInputStream translation;
        //SummerTop_Category_Trans
        String trans = null;
        try {
            translation = openFileInput("OuterList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("패딩", "padding");
            trans = trans.replaceAll("자켓", "jacket");
            trans = trans.replaceAll("코트", "coat");
            trans = trans.replaceAll("털옷", "furcoat");
            Outer_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("OuterName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            Outer_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }


        try {
            translation = openFileInput("SummerTopList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("반팔 티셔츠", "longt");
            SummerTop_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("SummerTopName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            SummerTop_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("SummerBotList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("긴바지", "pants");
            trans = trans.replaceAll("반바지", "shortpants");
            SummerBot_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("SummerBotName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            SummerBot_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("WinterTopList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("긴팔 티셔츠", "longt");
            trans = trans.replaceAll("후드", "hoodt");
            WinterTop_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }


        try {
            translation = openFileInput("WinterTopName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            WinterTop_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("WinterBotList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("긴바지", "pants");
            WinterBot_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("WinterBotName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            WinterBot_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("Spring_FallTopList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("긴팔 티셔츠", "longt");
            trans = trans.replaceAll("후드", "hoodt");
            trans = trans.replaceAll("반팔 티셔츠", "shortt");
            Spring_FallTop_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("Spring_FallTopName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            Spring_FallTop_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }
        try {
            translation = openFileInput("Spring_FallBotList");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            trans = trans.replaceAll("반바지", "shortpants");
            trans = trans.replaceAll("긴바지", "pants");
            Spring_FallBot_Category_Trans = trans.split(",");
        } catch (IOException e) {

        }

        try {
            translation = openFileInput("Spring_FallBotName");
            byte[] txt = new byte[500];
            translation.read(txt);
            translation.close();
            trans = (new String(txt)).trim();
            Spring_FallBot_Name_Trans = trans.split(",");
        } catch (IOException e) {

        }
    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottom_menu = findViewById(R.id.bottom_menu);
    }

    private void SettingListener() {
        bottom_menu.setOnItemSelectedListener(new cloth_design.TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnItemSelectedListener {
        Intent menu;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.tab_graph: {
                    menu = new Intent(cloth_design.this, MainActivity.class);
                    startActivity(menu);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new graphFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_cloth: {
                    menu = new Intent(cloth_design.this, cloth_design.class);
                    startActivity(menu);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new clothFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_creator: {
                    menu = new Intent(cloth_design.this, creator.class);
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