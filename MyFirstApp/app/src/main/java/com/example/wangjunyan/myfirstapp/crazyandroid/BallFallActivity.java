package com.example.wangjunyan.myfirstapp.crazyandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.example.wangjunyan.myfirstapp.R;

import java.util.ArrayList;

public class BallFallActivity extends Activity {
    static final float BALL_SIZE = 50f;
    static final float FULL_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_fall);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        container.addView(new MyAnimationView(this));
    }

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener{
        public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();

        public MyAnimationView(Context context) {
            super(context);
            setBackgroundColor(Color.WHITE);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            if(event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE){
                return false;
            }
            ShapeHolder newBall = addBall(event.getX(), event.getY());
            float startY = newBall.getY();
            float endY = getHeight() - BALL_SIZE;
            float h = (float)getHeight();
            float eventY = event.getY();
            int duration = (int)(FULL_TIME*((h-eventY)/h));
            ValueAnimator fallAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY);
            fallAnim.setDuration(duration);
            fallAnim.setInterpolator(new AccelerateInterpolator());
            fallAnim.addUpdateListener(this);
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
            fadeAnim.setDuration(250);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    balls.remove(((ObjectAnimator) animator).getTarget());
                }
            });
            fadeAnim.addUpdateListener(this);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(fallAnim).before(fadeAnim);
            animatorSet.start();
            return true;
        }

        private ShapeHolder addBall(float x, float y){
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - BALL_SIZE / 2);
            shapeHolder.setY(y - BALL_SIZE / 2);
            int red = (int)(Math.random()) * 255;
            int green = (int)(Math.random()) * 255;
            int blue = (int)(Math.random()) * 255;
            int color = 0xff000000 + red<<16 | green<<8 | blue;
            Paint paint = drawable.getPaint();
            int darkColor = 0xff000000 | red/4<<16 |green/4<<8 |blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, BALL_SIZE, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;

        }

        @Override
        protected void onDraw(Canvas canvas){
            for(ShapeHolder shapeHolder : balls){
                canvas.save();
                canvas.translate(shapeHolder.getX(), shapeHolder.getY());
                shapeHolder.getShape().draw(canvas);
                canvas.restore();
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            this.invalidate();
        }
    }
}
