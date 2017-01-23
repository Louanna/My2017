package com.example.webview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by demi on 16/11/17.
 */

public class PieChatView extends View {
    private static final int DEFAULT_MINANGLE = 30;
    private int sum = 0;
    private int minAngle = DEFAULT_MINANGLE;
    /**
     * 存放事物的品种与其对应的数量
     */
    private Map kindsMap = new LinkedHashMap<String, Integer>();

    private ArrayList<Integer> colors = new ArrayList<>();

    private Paint mPaint;//饼状画笔
    private Paint mTextPaint; // 文字画笔
    private static final int DEFAULT_RADIUS = 50;
    private int mRadius = DEFAULT_RADIUS; //外园的半径
    private float animatedValue;
    private RectF oval;
    private String centerTitle;

    public int getMinAngle() {
        return minAngle;
    }

    public void setMinAngle(int minAngle) {
        this.minAngle = minAngle;
    }

    public int getmRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }

    public float getAnimatedValue() {
        return animatedValue;
    }

    public void setAnimatedValue(float animatedValue) {
        this.animatedValue = animatedValue;
    }

    /**
     * 格式化小数点
     */
    private DecimalFormat dff;

    public PieChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mTextPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(sp2px(15));

        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        dff = new DecimalFormat("0.0");
    }

    public PieChatView(Context context) {
        this(context, null, 0);

    }

    public PieChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
//        int wideMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int width, height;
//        if (wideMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
//            width = wideSize;
//        } else {
//            width = mRadius * 2 + getPaddingLeft() + getPaddingRight();
//            if (wideMode == MeasureSpec.AT_MOST) {
//                width = Math.min(width, wideSize);
//            }
//        }
//        if (heightMode == MeasureSpec.EXACTLY) { //精确值 或matchParent
//            height = heightSize;
//        } else {
//            height = mRadius * 2 + getPaddingTop() + getPaddingBottom();
//            if (heightMode == MeasureSpec.AT_MOST) {
//                height = Math.min(height, heightSize);
//            }
//
//        }
//        setMeasuredDimension(width, height);
//        mRadius = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
//                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);
//        oval = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        oval = new RectF(-150+-mRadius*2, -mRadius*2, -150, 0);
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);
        mCanvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) / 2, (getHeight() + getPaddingTop() - getPaddingBottom()) / 2);
        paintPie(mCanvas);
    }

    private void paintPie(final Canvas mCanvas) {
        if (kindsMap != null && centerTitle != null) {
            Set<Map.Entry<String, Integer>> entrySet = kindsMap.entrySet();
            Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();
            int i = 0;
            float currentAngle = 0.0f;
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                String kinds = entry.getKey();
                int num = entry.getValue();
                float needDrawAngle = num * 1.0f / sum * 360;
                String drawAngle = dff.format(needDrawAngle / 360 * 100);
//                kinds = kinds + "," + drawAngle + "%";
                float textAngle = needDrawAngle / 2 + currentAngle;
                if (Math.min(needDrawAngle, animatedValue - currentAngle) >= 0) {
                    mPaint.setColor(colors.get(i));
                    mCanvas.drawArc(oval, currentAngle, Math.min(needDrawAngle - 1, animatedValue - currentAngle), true, mPaint);
                    drawText(mCanvas, textAngle, num+"", needDrawAngle);

                    //对应颜色显示比例
                    mCanvas.drawText(kinds + "目前所占的百分比" + drawAngle + "%", -150+0+20, -mRadius*2+50 * (i+1), mPaint);
                }
                currentAngle = currentAngle + needDrawAngle;
                i++;
            }
        }
    }

    //画文字
    private void drawText(Canvas mCanvas, float textAngle, String kinds, float needDrawAngle) {
        Rect rect = new Rect();
        mTextPaint.setTextSize(sp2px(15));
        mTextPaint.getTextBounds(kinds, 0, kinds.length(), rect);
        if (textAngle >= 0 && textAngle <= 90) { //画布坐标系第一象限(数学坐标系第四象限)
            if (needDrawAngle < minAngle) { //如果小于某个度数,就把文字画在饼状图外面
                mCanvas.drawText(kinds, -150+-mRadius+(float) (mRadius * 1.2 * Math.cos(Math.toRadians(textAngle))), -mRadius+(float) (mRadius * 1.2 * Math.sin(Math.toRadians(textAngle))) + rect.height() / 2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (mRadius * 0.5 * Math.cos(Math.toRadians(textAngle))), -mRadius+(float) (mRadius * 0.5 * Math.sin(Math.toRadians(textAngle))) + rect.height() / 2, mTextPaint);
            }
        } else if (textAngle > 90 && textAngle <= 180) { //画布坐标系第二象限(数学坐标系第三象限)
            if (needDrawAngle < minAngle) {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (-mRadius * 1.2 * Math.cos(Math.toRadians(180 - textAngle))), -mRadius+(float) (mRadius * 1.2 * Math.sin(Math.toRadians(180 - textAngle))) + rect.height() / 2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (-mRadius * 0.5 * Math.cos(Math.toRadians(180 - textAngle))), -mRadius+(float) (mRadius * 0.5 * Math.sin(Math.toRadians(180 - textAngle))) + rect.height() / 2, mTextPaint);
            }
        } else if (textAngle > 180 && textAngle <= 270) { //画布坐标系第三象限(数学坐标系第二象限)
            if (needDrawAngle < minAngle) {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (-mRadius * 1.2 * Math.cos(Math.toRadians(textAngle - 180))), -mRadius+(float) (-mRadius * 1.2 * Math.sin(Math.toRadians(textAngle - 180))) + rect.height() / 2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (-mRadius * 0.5 * Math.cos(Math.toRadians(textAngle - 180))), -mRadius+(float) (-mRadius * 0.5 * Math.sin(Math.toRadians(textAngle - 180))) + rect.height() / 2, mTextPaint);
            }
        } else { //画布坐标系第四象限(数学坐标系第一象限)
            if (needDrawAngle < minAngle) {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (mRadius * 1.2 * Math.cos(Math.toRadians(360 - textAngle))), -mRadius+(float) (-mRadius * 1.2 * Math.sin(Math.toRadians(360 - textAngle))) + rect.height() / 2, mTextPaint);
            } else {
                mCanvas.drawText(kinds, -150+-mRadius+(float) (mRadius * 0.5 * Math.cos(Math.toRadians(360 - textAngle))), -mRadius+(float) (-mRadius * 0.5 * Math.sin(Math.toRadians(360 - textAngle))) + rect.height() / 2, mTextPaint);
            }
        }
    }

    public void setDataMap(LinkedHashMap<String, Integer> map) {
        this.kindsMap = map;
        sum = getSum(map);
    }

    public void startDraw() {
        if (kindsMap != null && colors != null && centerTitle != null) {
            initAnimator();
        }
    }

    private void initAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        //动画间隔
        anim.setDuration(0);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    private int getSum(Map<String, Integer> map) {
        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        int sum = 0;

        while (iterator.hasNext()) {
            String kinds = iterator.next();
            int num = map.get(kinds);
            sum = sum + num;
        }
        return sum;
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }
}
