package com.example.wangjunyan.myfirstapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjunyan on 16/3/22.
 */
public class LightWaveOvalView extends View {
    private static final String TAG = "LightWaveOvalView";
    public final static float OVAL_WAVE_HEIGHT_WIDTH_RATIO = 0.21f; // 椭圆波纹高宽比，height = width *
    // WIDTH_HEIGHT_RATIO
    private final static float MAX_ALPHA = 70.0f;
    private final static float OVAL_ALPHA_ANIM_VALUE = 0.30f;
    private final static float MAX_ANIM_VALUE = 1.0f + OVAL_ALPHA_ANIM_VALUE;

    private static int OVAL_STROKE_WIDTH;
    public int mWidth = 0;
    public int mHeight = 0;
    private int mOvalCenterH = 0;
    private Paint mPaint;
    private ValueAnimator mAnimator = ValueAnimator.ofFloat(0, MAX_ANIM_VALUE);
    private boolean mbInit = false;
    private boolean mbStop = true;
    private List<Oval> mOvalList = new ArrayList<Oval>();

    public LightWaveOvalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        final float scale = context.getResources().getDisplayMetrics().density;
        OVAL_STROKE_WIDTH =  (int)(1.0f * scale + 0.5f);

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#0000ff"));
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 绘制空心圆或 空心矩形

        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (mbInit) {
                    return true;
                }
                mbInit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initAnimParams();
                    }
                }, 1);
                return true;
            }
        });
    }

    public void startWave() {
        mbStop = false;
        for (Oval oval : mOvalList) {
            oval.reset();
        }

        mAnimator.setFloatValues(0, MAX_ANIM_VALUE);
        mAnimator.setDuration(2500);

        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(-1);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                updateOvalList(value);
            }
        });
        mAnimator.start();
        invalidate();
    }

    public void stopWave() {
        mbStop = true;
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator.removeAllListeners();
        mAnimator.removeAllUpdateListeners();
        invalidate();
    }

    private void initAnimParams() {
        initHeight();
        mOvalCenterH = mHeight / 2;
        iniOvalList();
    }

    private void initHeight() {
        mWidth = getWidth();
        mHeight = (int) (mWidth * OVAL_WAVE_HEIGHT_WIDTH_RATIO);
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
        lp.height = mHeight;
        setLayoutParams(lp);
    }

    private void iniOvalList() {
        float startValue = 0.0f;
        for (int i = 0; i < 3; i++) {
            mOvalList.add(new Oval(startValue, mWidth, mOvalCenterH));
            startValue -= 0.4f;
        }
    }

    private void updateOvalList(float value) {
        for (Oval oval : mOvalList) {
            oval.update(value);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mbStop) {
            drawOvalList(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawOval(Canvas canvas, Oval oval) {
        mPaint.setARGB(oval.alpha, 255, 255, 255);
        mPaint.setStrokeWidth(oval.strokeWidth);
        canvas.drawOval(oval.rectF, mPaint);
    }

    private void drawOvalList(Canvas canvas) {
        for (Oval oval : mOvalList) {
            if (oval.bShow) {
                drawOval(canvas, oval);
            }
        }
        invalidate();
    }

    public static class Oval {
        public int alpha; // 线条颜色的alpha值
        public int strokeWidth = OVAL_STROKE_WIDTH; // 线条宽度
        public RectF rectF = new RectF();
        public float startValue = 0.0f;
        public boolean bShow = false;
        private int maxWidth = 0;
        private int ovalCenterH = 0;

        public Oval(float startValue, int maxWidth, int ovalCenterH) {
            this.startValue = startValue;
            this.maxWidth = maxWidth;
            this.ovalCenterH = ovalCenterH;
        }

        public void reset() {
            bShow = false;
        }

        public void update(float value) {
            value += startValue;
            if (value >= 0) {
                bShow = true;
            }
            if (value < 0) {
                value += MAX_ANIM_VALUE;
            }
            int height = 0;
            int width = 0;
            if (value < 1.0f) {
                width = (int) (maxWidth * value);
                alpha = (int) MAX_ALPHA;
            } else {
                value = value - 1.0f;
                width = maxWidth;
                alpha = (int) (MAX_ALPHA * (1.0f - value / OVAL_ALPHA_ANIM_VALUE));
            }
            height = (int) (width * OVAL_WAVE_HEIGHT_WIDTH_RATIO);
            rectF.left = (maxWidth - width) / 2;
            rectF.right = rectF.left + width;
            rectF.bottom = ovalCenterH + height / 2;
            rectF.top = ovalCenterH - height / 2;
        }
    }
}