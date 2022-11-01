package com.hyy.logcat;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author : Hou
 * @Time : 2022/9/29 14:25
 * @Description :
 */
public class LogCatActivity1 extends AppCompatActivity {
    private Context context;

    private WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log);
        context = this;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        lp = getWindow().getAttributes();
        lp.width = (int) (metrics.widthPixels * 0.7);
        lp.height = (int) (metrics.heightPixels * 0.5);
        getWindow().setAttributes(lp);

        setFinishOnTouchOutside(false);


        ConstraintLayout rootLayout = findViewById(R.id.coordinatorLayout);
        TextView clearBtn = findViewById(R.id.clearBtn);
        TextView saveBtn = findViewById(R.id.saveBtn);
        TextView dismissBtn = findViewById(R.id.dismissBtn);
        LogcatFragment firstFragment = LogcatFragment.Companion.newInstance("logcat.log", "search"
                + " " + "logcat", "");

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                firstFragment).commit();

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "哈哈", Toast.LENGTH_SHORT).show();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstFragment.clearLog();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    writeToTxt(firstFragment.getLogList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String DIR = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/AndroidLog";

    RandomAccessFile randomFile = null;

    public void writeToTxt(List<String> list) throws Exception {

        File file = new File(DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHHmmss", Locale.CHINA);
        String date = format1.format(new Date(System.currentTimeMillis()));
        try {
            String path = DIR + "/" + date + ".txt";
            randomFile = new RandomAccessFile(path, "rw");
            for (String logStr : list) {
                randomFile.write((logStr + "\n").getBytes());
            }
            Toast.makeText(context, "日志保存成功,path=" + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d("LogCatActivity", e.toString());
        }
    }

}
