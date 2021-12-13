package com.cookandroid.iot;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.DIRECTORY_PICTURES;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    Intent intent;
    final String TAG = this.getClass().getSimpleName();
    LinearLayout home_ly; BottomNavigationView bottom_menu;
    static String[] Split_Data = {};
    static String title = null;
    String data;
    String send;
    float get_split_data;
    ArrayList<Entry> values = new ArrayList<>();
    LineChart chart;
    double[] nums;
    private LineChart lineChart1;
    private String URL = "https://raw.githubusercontent.com/ssh5212/IoT_OS/main/output2.txt";

    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis() + 100;
    float x = 0.0f; float y = 0.0f;
    int metaState = 0;
    MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, x, y, metaState);
    Document d;
    int c = 1;
    Intent send_Data;
    Intent send_Data1;
    Intent Temper;
    //BackgroundThread thread = new BackgroundThread();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        init();
        SettingListener();
        bottom_menu.setSelectedItemId(R.id.home);
        send_Data = new Intent(MainActivity.this, cloth_design.class);


        //thread.start();

        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, cloth_design.class);
                startActivity(send_Data);
            }
        });

        new Thread() {
            public void run() {
                try {
                    d = Jsoup.connect(URL).get();
                    Elements data = d.select(".blob-code-inner").select("ul").select("li");
                    int size = data.size();
                    Elements temple = d.select("body");
                    //title = temple.select("body").text();
                    title = temple.get(0).select("body").text().substring(1);
                    title = title.trim();
                    send = title;
                    Split_Data = title.split(" ");
                    nums = Arrays.stream(Split_Data).mapToDouble(Double::parseDouble).toArray();
                    for (int i = 0; i < nums.length; i++) {
                        values.add((new Entry(i, (float) nums[i])));
                    }
                    send_Data.putExtra("title", send);
                    //send_Data1.putExtra("data", nums);

                    DrawGraph();
                    lineChart1.dispatchTouchEvent(motionEvent);
                } catch (IOException e) {
                }
            }
        }.start();

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        //startActivity(send_Data);
        // black lines and points
        //Intent a = new Intent(MainActivity.this, MainActivity.class);
        // set data
        //startActivity(a);
        //finish();

        FileInputStream inFst;
        try {
            data = title;
            inFst = openFileInput("GetData");
            byte[] txt = new byte[500];
            inFst.read(txt);
            inFst.close();
            data = (new String(txt)).trim();
        } catch (IOException e) {

        }
        FileOutputStream outFst;
        try {
            outFst = openFileOutput("GetData",
                    Context.MODE_PRIVATE);
            String str = data.toString();
            outFst.write(str.getBytes());
            outFst.close();
        } catch (IOException e) {
        }
        //DrawGraph();
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
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new graphFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_cloth: {
                    intent = new Intent(MainActivity.this, cloth_design.class);
                    send_Data.putExtra("title", title);
                    startActivity(send_Data);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new clothFragment()).commit();
                    finish();
                    return true;
                }
                case R.id.tab_creator: {
                    intent = new Intent(MainActivity.this, creator.class);
                    startActivity(intent);
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_ly, new creatorFragment()).commit();
                    finish();
                    return true;
                }
            }
            return false;
        }
    }
    public void DrawGraph() {
        lineChart1 = (LineChart) findViewById(R.id.linechart);
        LineDataSet set1 = new LineDataSet(values, "");

        set1.setLineWidth(2);
        set1.setCircleRadius(2);
        set1.setCircleColor(Color.parseColor("#FFFF0000"));
        set1.setColor(Color.parseColor("#FFFFFF00"));
        set1.setDrawCircles(true);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawValues(false);

        LineData graph1 = new LineData(set1);
        lineChart1.setData(graph1);
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.N)
    class BackgroundThread extends Thread {
        public void run() {
            try {
                d = Jsoup.connect(URL).get();
                Elements data = d.select(".blob-code-inner").select("ul").select("li");
                int size = data.size();
                Elements temple = d.select("body");
                //title = temple.select("body").text();
                title = temple.get(0).select("body").text().substring(1);
                title = title.trim();
                Split_Data = title.split(" ");

                nums = Arrays.stream(Split_Data).mapToDouble(Double::parseDouble).toArray();
                for (int i = 0; i < nums.length; i++) {
                    //values.add((new Entry((float) nums[i], (j*i) + j)));
                    values.add((new Entry(i, (float) nums[i])));
                }

                send_Data.putExtra("data", nums);
                //startActivity(send_Data);

                for(;;) {
                    Thread.sleep(1000);
                    Log.v("thread", "zxczxc");
                    DrawGraph();
                }
            } catch (IOException e) {
            }
        }
    }
    */
}

