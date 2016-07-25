package com.example.wangjunyan.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;


/**
 * Created by wangjunyan on 16/1/11.
 */
public class GuideLayout extends FrameLayout {
    private WindowManager wm;
    private WindowManager.LayoutParams wmlp;
    private View fullView;
    private View topView;
    private View bottomView;
    private AnimatorSet animatorSet;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showAnimator();
                    break;
                default:
                    break;
            }
        }
    };

    public GuideLayout(Context context){
        this(context, null);
    }

    public GuideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public GuideLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        fullView = LayoutInflater.from(context).inflate(R.layout.activity_safe_box, this);
        topView = fullView.findViewById(R.id.view_top);
        bottomView = fullView.findViewById(R.id.view_bottom);
        topView.setVisibility(View.GONE);
        bottomView.setVisibility(View.GONE);
        setAnimator();
        getWindowManager(context);
    }

    private void getWindowManager(final Context context) {
        wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wmlp = new WindowManager.LayoutParams();
        wmlp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        wmlp.format = PixelFormat.TRANSPARENT;
        wmlp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    private void setAnimator() {
        animatorSet = new AnimatorSet();
        Animator[] animators = new Animator[2];
        animators[0] = ObjectAnimator.ofFloat(topView, "translationY", -800);
        animators[1] = ObjectAnimator.ofFloat(bottomView, "translationY", 800);
                animatorSet.playTogether(animators);
        animatorSet.setDuration(1000);
    }

    public void showAnimator() {
        topView.setVisibility(View.VISIBLE);
        bottomView.setVisibility(View.VISIBLE);
        animatorSet.start();
    }

    public void sendMessage() {
        handler.sendEmptyMessage(1);
        wm.addView(this, wmlp);
    }

}
