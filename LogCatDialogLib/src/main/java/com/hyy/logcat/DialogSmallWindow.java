package com.hyy.logcat;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @Author : Hou
 * @Time : 2022/9/28 13:19
 * @Description :
 */
public class DialogSmallWindow {
    private Window win;
    private WindowManager.LayoutParams lp;
    public AlertDialog dialog;
    private int viewX = 10;
    private int viewY = 300;
    private Context mContext;

    public static DialogSmallWindow instance;

    public static DialogSmallWindow getInstance() {
        if (instance == null) {
            instance = new DialogSmallWindow();
        }
        return instance;
    }

    protected void initDialog(Context context) {
        mContext = context;
        dialog = new AlertDialog.Builder(context).create();
        win = dialog.getWindow(); //获取Window对象
        //getDecorView()函数 -- 获取顶层View
        win.getDecorView().setOnTouchListener(new FloatingOnTouchListener()); //设置拖动响应
        lp = win.getAttributes();
        lp.gravity = Gravity.LEFT | Gravity.TOP;  //设置参考系
        lp.x = viewX;  //设置起始位置
        lp.y = viewY;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;  //设置不聚焦,不会阻碍下层控件响应
        win.setAttributes(lp);  //将参数设置回去
        win.setLayout(120, 120); //设置显示的宽高
        dialog.show();
    }


    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private int downX;
        private int downY;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:  //按下
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    downX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    Log.i("hc", "onTouch:ACTION_DOWN ");
                    break;
                case MotionEvent.ACTION_MOVE:  //移动
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    viewX = viewX + movedX;
                    viewY = viewY + movedY;
                    lp.x = viewX;
                    lp.y = viewY;
                    win.setAttributes(lp);
                    dialog.show();
                    Log.i("hc", "onTouch:ACTION_MOVE ");
                    break;
                case MotionEvent.ACTION_UP:  //单击事件
                    int upX = (int) event.getRawX();
                    int upY = (int) event.getRawY();
                    if (downX == upX && downY == upY) {
                        Log.i("hc", "onTouch:onClick ");
                    }
                    Log.i("hc", "onTouch:ACTION_UP ");
                    break;
                default:
                    break;
            }
            return false;
        }
    }

}

