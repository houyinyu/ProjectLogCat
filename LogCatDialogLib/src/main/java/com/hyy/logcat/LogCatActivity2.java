package com.hyy.logcat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.opengles.GL;

/**
 * @Author : Hou
 * @Time : 2022/9/30 9:58
 * @Description :
 */
public class LogCatActivity2 extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "LogCatActivity";
    String cmds = "";
    StringBuilder Sb = new StringBuilder("this is log");
    private BufferedReader mReader = null;
    private Process exec;
    private int mPId;
    private String mPID;
    private boolean mRunning = true;
    TextView etlog;
    Button btnStop;
    TextView btnSave, btnClear;
    RandomAccessFile randomFile = null;
    private String DIR = Environment.getExternalStorageDirectory().getAbsolutePath() +
            "/AndroidLog";

    private Context mContext;
    private WindowManager.LayoutParams lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log2);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        lp = getWindow().getAttributes();
        lp.width = (int) (metrics.widthPixels * 0.7);
        lp.height = (int) (metrics.heightPixels * 0.5);
        getWindow().setAttributes(lp);

        setFinishOnTouchOutside(false);

        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        mContext = this;
//        toDispPageInfo("日志查看");
        etlog = findViewById(R.id.logCatEdit);
//        btnStop =  findViewById(R.id.btn_stop);
//        btnStop.setOnClickListener(this);
        btnSave = findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(this);
        btnClear = findViewById(R.id.clearBtn);
        btnClear.setOnClickListener(this);
        mPId = android.os.Process.myPid();
        mPID = String.valueOf(mPId);
        cmds = "logcat  *:e *:d | grep \"(" + mPID + ")\"";
        ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            exec = Runtime.getRuntime().exec(cmds);
            mReader = new BufferedReader(new InputStreamReader(exec.getInputStream()), 1024);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String line = "";
                    try {
                        while (mRunning && (line = mReader.readLine()) != null) {
                            Sb.append(line);
                            if (!mRunning) {
                                break;
                            }
                            if (line.length() == 0) {
                                continue;
                            }
                            final Message msg = Message.obtain();
                            msg.what = 2;
                            msg.obj = line + "\n";
                            mhandler.sendMessage(msg);
                            Thread.sleep(100);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    } finally {
                        Log.e(TAG, "finally");
                        Log.e(TAG, "" + mRunning);
                        if (line == null) {
                            Log.e(TAG, "line is null");
                        }
                        if (exec != null) {
                            exec.destroy();
                        }
                        if (mReader != null) {
                            try {
                                mReader.close();
                                mReader = null;
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //startShowLog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRunning = false;
        if (exec != null) {
            exec.destroy();
        }
        if (mReader != null) {
            try {
                mReader.close();
                mReader = null;
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();//            case R.id.btn_stop:
//                mRunning = false;
//                break;
        if (id == R.id.saveBtn) {
            File file = new File(DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-ddHHmmss", Locale.CHINA);
            String date = format1.format(new Date(System.currentTimeMillis()));
            try {
                String path = DIR + "/" + date + ".log";
                randomFile = new RandomAccessFile(path, "rw");
                randomFile.write(etlog.getText().toString().getBytes());
                Toast t = Toast.makeText(mContext, "日志保存成功,path=" + path, Toast.LENGTH_SHORT);
                t.show();
            } catch (IOException e) {
                //e.printStackTrace();
                Log.d(TAG, e.toString());
            }
        } else if (id == R.id.clearBtn) {
            etlog.setText("");
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mhandler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    //显示日志
                    etlog.setMovementMethod(ScrollingMovementMethod.getInstance());
//                    etlog.setSelection(etlog.getText().length(), etlog.getText().length());
                    etlog.setText(etlog.getText() + msg.obj.toString());
                    break;
            }
        }
    };
}
