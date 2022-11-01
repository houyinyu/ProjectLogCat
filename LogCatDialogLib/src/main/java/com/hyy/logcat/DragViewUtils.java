package com.hyy.logcat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
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
    private static DragViewUtils viewUtils;
    private Context context;

    public static DragViewUtils getInstance() {
        if (viewUtils == null) {
            synchronized (DragViewUtils.class) {
                if (viewUtils == null) {
                    viewUtils = new DragViewUtils();
                }
            }
        }
        return viewUtils;
    }

    private DragFloatActionButton actionButton;

    public DragViewUtils() {
    }


    public DragViewUtils addDrag(ViewGroup groupLayout, Context context) {
        actionButton = new DragFloatActionButton(context);
        actionButton.setBackgroundResource(R.drawable.shape_yellow_back);
        actionButton.setImageResource(R.drawable.yellow_log);
        actionButton.setScaleType(ImageView.ScaleType.CENTER);

        actionButton.setOnClickListener(new DragFloatActionButton.OnClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(context, LogCatActivity1.class);
                context.startActivity(intent);
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100, 100);
        groupLayout.addView(actionButton, params);

        return this;
    }


    public DragViewUtils setBack(int backRes) {
        if (actionButton != null) {
            actionButton.setBackgroundResource(backRes);
        }
        return this;
    }

    public DragViewUtils setBack(Drawable backDraw) {
        if (actionButton != null) {
            actionButton.setBackground(backDraw);
        }
        return this;
    }

    public DragViewUtils setImage(int imageRes) {
        if (actionButton != null) {
            actionButton.setImageResource(imageRes);
        }
        return this;
    }

    public void showDrag() {
        if (actionButton != null) {
            actionButton.setVisibility(View.VISIBLE);
        }
    }

    public void hideDrag() {
        if (actionButton != null) {
            actionButton.setVisibility(View.GONE);
        }
    }

}
