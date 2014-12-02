package com.example.apidemowjy;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimationActivity extends Activity implements OnClickListener {

	private Button btnAnimation1;
	private Button btnAnimation2;
	private Button btnAnimation3;
	private Button btnAnimation4;
	private Button btnAnimation5;
	private TextView txtAnimation1;
	private ImageView imgAnimation;
	private Button btnAnimation6;
	private Button btnAnimation7;
	private Button btnAnimation8;
	private Button btnAnimation9;
	private Button btnAnimation10;

	private Button mMenuButton;
	private Button mItemButton1;
	private Button mItemButton2;
	private Button mItemButton3;
	private Button mItemButton4;
	private Button mItemButton5;

	private boolean mIsMenuOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(MyApplication.TAG, "onCreate");
		setContentView(R.layout.animation_test);

		btnAnimation1 = (Button) findViewById(R.id.btn_anim1);
		btnAnimation2 = (Button) findViewById(R.id.btn_anim2);
		btnAnimation3 = (Button) findViewById(R.id.btn_anim3);
		btnAnimation4 = (Button) findViewById(R.id.btn_anim4);
		btnAnimation5 = (Button) findViewById(R.id.btn_anim5);
		txtAnimation1 = (TextView) findViewById(R.id.txt_anim1);
		imgAnimation = (ImageView) findViewById(R.id.img_anim);
		btnAnimation6 = (Button) findViewById(R.id.btn_anim6);
		btnAnimation7 = (Button) findViewById(R.id.btn_anim7);
		btnAnimation8 = (Button) findViewById(R.id.btn_anim8);
		btnAnimation9 = (Button) findViewById(R.id.btn_anim9);
		btnAnimation10 = (Button) findViewById(R.id.btn_anim10);

		btnAnimation1.setOnClickListener(this);
		btnAnimation2.setOnClickListener(this);
		btnAnimation3.setOnClickListener(this);
		btnAnimation4.setOnClickListener(this);
		btnAnimation5.setOnClickListener(this);
		btnAnimation6.setOnClickListener(this);
		btnAnimation7.setOnClickListener(this);
		btnAnimation8.setOnClickListener(this);
		btnAnimation9.setOnClickListener(this);
		btnAnimation10.setOnClickListener(this);

		imgAnimation.setBackgroundResource(R.drawable.animated_rocket);
		AnimationDrawable anim = (AnimationDrawable) imgAnimation
				.getBackground();
		anim.start();

		mMenuButton = (Button) findViewById(R.id.menu);
		mMenuButton.setOnClickListener(this);
		mItemButton1 = (Button) findViewById(R.id.item1);
		mItemButton1.setOnClickListener(this);
		mItemButton2 = (Button) findViewById(R.id.item2);
		mItemButton2.setOnClickListener(this);
		mItemButton3 = (Button) findViewById(R.id.item3);
		mItemButton3.setOnClickListener(this);
		mItemButton4 = (Button) findViewById(R.id.item4);
		mItemButton4.setOnClickListener(this);
		mItemButton5 = (Button) findViewById(R.id.item5);
		mItemButton5.setOnClickListener(this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d(MyApplication.TAG, "onNewIntent");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(MyApplication.TAG, "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(MyApplication.TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(MyApplication.TAG, "onResume");
	}

	@Override
	protected void onPause() {
		Log.d(MyApplication.TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(MyApplication.TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop() {
		Log.d(MyApplication.TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d(MyApplication.TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Animation anim;
		switch (v.getId()) {
		case R.id.btn_anim1:
			anim = AnimationUtils.loadAnimation(AnimationActivity.this,
					R.anim.translate_demo);
			txtAnimation1.startAnimation(anim);
			break;
		case R.id.btn_anim2:
			anim = AnimationUtils.loadAnimation(AnimationActivity.this,
					R.anim.rotate_demo);
			txtAnimation1.startAnimation(anim);
			break;
		case R.id.btn_anim3:
			anim = AnimationUtils.loadAnimation(AnimationActivity.this,
					R.anim.scale_demo);
			txtAnimation1.startAnimation(anim);
			break;
		case R.id.btn_anim4:
			anim = AnimationUtils.loadAnimation(AnimationActivity.this,
					R.anim.alpha_demo);
			txtAnimation1.startAnimation(anim);
			break;
		case R.id.btn_anim5:
			anim = AnimationUtils.loadAnimation(AnimationActivity.this,
					R.anim.set_demo);
			txtAnimation1.startAnimation(anim);
			break;
		case R.id.btn_anim6:
			ObjectAnimator.ofFloat(txtAnimation1, "translationY",
					-txtAnimation1.getHeight()).start();
			break;
		case R.id.btn_anim7:
			ValueAnimator colorAnim = ObjectAnimator.ofInt(txtAnimation1,
					"backgroundColor", 0xFFFF8080, 0xFF8080FF);
			colorAnim.setDuration(3000);
			colorAnim.setEvaluator(new ArgbEvaluator());
			colorAnim.setRepeatCount(ValueAnimator.INFINITE);
			colorAnim.setRepeatMode(ValueAnimator.REVERSE);
			colorAnim.start();
			break;
		case R.id.btn_anim8:
			AnimatorSet set = new AnimatorSet();
			set.playTogether(ObjectAnimator.ofFloat(txtAnimation1, "rotationX",
					0, 360), ObjectAnimator.ofFloat(txtAnimation1, "rotationY",
					0, 180), ObjectAnimator.ofFloat(txtAnimation1, "rotation",
					0, -90), ObjectAnimator.ofFloat(txtAnimation1,
					"translationX", 0, 90), ObjectAnimator.ofFloat(
					txtAnimation1, "translationY", 0, 90), ObjectAnimator
					.ofFloat(txtAnimation1, "scaleX", 1, 1.5f), ObjectAnimator
					.ofFloat(txtAnimation1, "scaleY", 1, 0.5f), ObjectAnimator
					.ofFloat(txtAnimation1, "alpha", 1, 0.25f, 1));
			set.setDuration(5 * 1000).start();
			break;
		case R.id.btn_anim9:
			anim = AnimationUtils.loadAnimation(AnimationActivity.this,
					R.anim.scale_demo2);
			btnAnimation6.startAnimation(anim);
			break;
		case R.id.btn_anim10:
			//ObjectAnimator.ofInt(btnAnimation6, "width", 800).setDuration(5000).start();
			ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
			valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

	            //持有一个IntEvaluator对象，方便下面估值的时候使用
	            private IntEvaluator mEvaluator = new IntEvaluator();

	            @Override
	            public void onAnimationUpdate(ValueAnimator animator) {
	                //获得当前动画的进度值，整型，1-100之间
	                int currentValue = (Integer)animator.getAnimatedValue();
	                float fraction = currentValue / 100f;
	                btnAnimation6.getLayoutParams().width = mEvaluator.evaluate(fraction, btnAnimation6.getWidth(), 800);
	                btnAnimation6.requestLayout();
	            }
	        });
			valueAnimator.setDuration(5000).start();
			break;
		case R.id.menu:
			if (!mIsMenuOpen) {
				mIsMenuOpen = true;
				doAnimateOpen(mItemButton1, 0, 5, 300);
				doAnimateOpen(mItemButton2, 1, 5, 300);
				doAnimateOpen(mItemButton3, 2, 5, 300);
				doAnimateOpen(mItemButton4, 3, 5, 300);
				doAnimateOpen(mItemButton5, 4, 5, 300);
			} else {
				mIsMenuOpen = false;
				doAnimateClose(mItemButton1, 0, 5, 300);
				doAnimateClose(mItemButton2, 1, 5, 300);
				doAnimateClose(mItemButton3, 2, 5, 300);
				doAnimateClose(mItemButton4, 3, 5, 300);
				doAnimateClose(mItemButton5, 4, 5, 300);
			}
			break;
		default:
			break;
		}
	}

	private void doAnimateOpen(View view, int index, int total, int radius) {
		if (view.getVisibility() != View.VISIBLE) {
			view.setVisibility(View.VISIBLE);
		}
		double degree = Math.PI * index / ((total - 1) * 2);
		int translationX = (int) (radius * Math.cos(degree));
		int translationY = (int) (radius * Math.sin(degree));
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
				ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
				ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
				ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
				ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
		set.setDuration(1 * 500).start();
	}

	private void doAnimateClose(final View view, int index, int total,
			int radius) {
		if (view.getVisibility() != View.VISIBLE) {
			view.setVisibility(View.VISIBLE);
		}
		double degree = Math.PI * index / ((total - 1) * 2);
		int translationX = (int) (radius * Math.cos(degree));
		int translationY = (int) (radius * Math.sin(degree));
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
				ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
				ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
				ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
				ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
		// 为动画加上事件监听，当动画结束的时候，我们把当前view隐藏
		set.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animator) {
			}

			@Override
			public void onAnimationRepeat(Animator animator) {
			}

			@Override
			public void onAnimationEnd(Animator animator) {
				view.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationCancel(Animator animator) {
			}
		});

		set.setDuration(1 * 500).start();
	}
}
