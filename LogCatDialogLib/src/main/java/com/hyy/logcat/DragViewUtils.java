package com.hyy.logcat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.core.content.ContextCompat;

/**
 * @Author : Hou
 * @Time : 2022/10/13 16:00
 * @Description :
 */
public class DragViewUtils {
    public static void addDrag(ViewGroup groupLayout, Context context) {
        DragFloatActionButton actionButton = new DragFloatActionButton(context);
        actionButton.setBackgroundResource(R.drawable.shape_yellow_back);
        actionButton.setImageResource(R.drawable.yellow_log);
        actionButton.setScaleType(ImageView.ScaleType.CENTER);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100, 100);
        groupLayout.addView(actionButton, params);

        actionButton.setOnClickListener(new DragFloatActionButton.OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(context, LogCatActivity1.class);
                context.startActivity(intent);
            }
        });
    }
}
