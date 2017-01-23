package com.example.webview;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/1/12.
 */

public class FanshapedView extends android.support.v7.widget.AppCompatImageView {
    private Context context;
    private int minAngle = 10;
    private int mRadius = 150; //外园的半径

    public FanshapedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化
        Paint p = new Paint();
        //分别是左上右下的顺序参数，这里是一个300×300的正方形
        RectF r = new RectF(50, 50, 350, 350);
        p.setColor(Color.BLACK);
        p.setTextSize(18f);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("水平百分比", 100, 20, p);
        //用来分别给不同等级的扇形做底色
        int[] color = {
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent,
                R.color.colorPrimaryDark,
        };
        //访问SharePreferences存储的records
        SharedPreferences sp = context.getSharedPreferences("records", context.MODE_PRIVATE);
        //默认值设为1，防止第一次没有sum报错，这是作为分母
        int sum = sp.getInt("sum", 1);
        //设置旋转角度
        int range = 0;
        //和初始角度
        int ori = 0;
        //规范百分比
        DecimalFormat df = new DecimalFormat("#");
        for (int i = 0; i < 4; i++) {
            p.setColor(getResources().getColor(color[i]));
            int level = sp.getInt("test" + (i + 1), 0);
            //当前的初始角度是在旋转的角度加上一个数据的初始角度
            ori = ori + range;
            range = (level * 360) / sum;
            //计算百分比
            String percent = df.format((range / 360f) * 100);
            //对应颜色显示比例
//            canvas.drawText("test" + i + "目前所占的百分比" + percent + "%", 180, 270 + 20 * i, p);
            //绘制扇形，起始角度，旋转角度
            canvas.drawArc(r, ori, range-1, true, p);
//            float needDrawAngle = level * 1.0f / sum * 360;

            Log.d("test","ori="+ori);
            Log.d("test","range="+range);


            float textAngle = range / 2 + ori;

            drawText(canvas, textAngle, level+"", range);
        }

    }

    //画文字
    private void drawText(Canvas mCanvas, float textAngle, String kinds, float needDrawAngle) {
        Rect rect = new Rect();
        Paint mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mTextPaint.setTextSize(sp2px(15));
        mTextPaint.getTextBounds(kinds, 0, kinds.length(), rect);

        Log.d("test","needDrawAngle="+needDrawAngle);
        Log.d("test","textAngle="+textAngle);

        if (textAngle >= 0 && textAngle <= 90) { //画布坐标系第一象限(数学坐标系第四象限)
            if (needDrawAngle < minAngle) { //如果小于某个度数,就把文字画在饼状图外面
                mCanvas.drawText(kinds, (float) (mRadius * 1.2 * Math.cos(Math.toRadians(textAngle))), (float) (mRadius * 1.2 * Math.sin(Math.toRadians(textAngle)))+rect.height()/2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, (float) (mRadius * 0.5 * Math.cos(Math.toRadians(textAngle))), (float) (mRadius * 0.5 * Math.sin(Math.toRadians(textAngle)))+rect.height()/2, mTextPaint);
            }
        } else if (textAngle > 90 && textAngle <= 180) { //画布坐标系第二象限(数学坐标系第三象限)
            if (needDrawAngle < minAngle) {
                mCanvas.drawText(kinds, (float) (-mRadius * 1.2 * Math.cos(Math.toRadians(180 - textAngle))), (float) (mRadius * 1.2 * Math.sin(Math.toRadians(180 - textAngle)))+rect.height()/2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, (float) (-mRadius * 0.5 * Math.cos(Math.toRadians(180 - textAngle))), (float) (mRadius * 0.5 * Math.sin(Math.toRadians(180 - textAngle)))+rect.height()/2, mTextPaint);
            }
        } else if (textAngle > 180 && textAngle <= 270) { //画布坐标系第三象限(数学坐标系第二象限)
            if (needDrawAngle < minAngle) {
                mCanvas.drawText(kinds, (float) (-mRadius * 1.2 * Math.cos(Math.toRadians(textAngle - 180))), (float) (-mRadius * 1.2 * Math.sin(Math.toRadians(textAngle - 180)))+rect.height()/2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, (float) (-mRadius * 0.5 * Math.cos(Math.toRadians(textAngle - 180))), (float) (-mRadius * 0.5 * Math.sin(Math.toRadians(textAngle - 180)))+rect.height()/2, mTextPaint);
            }
        } else { //画布坐标系第四象限(数学坐标系第一象限)
            if (needDrawAngle < minAngle) {
                mCanvas.drawText(kinds, (float) (mRadius * 1.2 * Math.cos(Math.toRadians(360 - textAngle))), (float) (-mRadius * 1.2 * Math.sin(Math.toRadians(360 - textAngle)))+rect.height()/2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, (float) (mRadius * 0.5 * Math.cos(Math.toRadians(360 - textAngle))), (float) (-mRadius * 0.5 * Math.sin(Math.toRadians(360 - textAngle)))+rect.height()/2, mTextPaint);
            }
        }

    }

    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }

}
