package com.example.wangjunyan.myfirstapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangjunyan on 16/1/12.
 */
public class ProgressView extends View {

    private int progress;
    private Bitmap background;
    private Bitmap mask;
    private Paint paint;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progress = 0;
        background = BitmapFactory.decodeResource(getResources(), R.drawable.progress1);
        mask = BitmapFactory.decodeResource(getResources(), R.drawable.progress2);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(background, 0, 0, null);
        paint.setFilterBitmap(false);
        int w = background.getWidth();
        int h = background.getHeight();
        int pw = w * progress / 100;
        int sc = canvas.saveLayer(0, 0, w, h, null,
                Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawRect(0, 0, pw, h, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mask, 0f, 0f, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    public void setProgress(int progress){
        this.progress = progress;
        invalidate();
    }
}
