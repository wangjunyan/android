package com.example.wangjunyan.myfirstapp;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by wangjunyan on 17/5/4.
 */

public class StatsValueLayout extends FrameLayout implements View.OnClickListener {

  private final static String TAG = "StatsValueLayout";

  private final static int NUMBER_NOT_SPECIFIED = -1;
  // private static final int ITEM_SIZE = dip2pixel(32f);
  private int ITEM_SIZE;
  private int MARGIN_SIZE;

  private int value = NUMBER_NOT_SPECIFIED;
  private List<StatsValue> statsValues;

  public StatsValueLayout(Context context) {
    super(context);
    init(context);
  }

  public StatsValueLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    ITEM_SIZE = dip2pixel(context, 32f);
    MARGIN_SIZE = dip2pixel(context, 42f);
  }

  public void setStatsValues(List<StatsValue> statsValues) {
    this.statsValues = statsValues;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
    refreshView();
  }

  private void refreshView() {
    if (statsValues != null) {
      removeAllViews();
      for (int i = 0; i < statsValues.size(); i++) {
        StatsValue statsValue = statsValues.get(i);
        TextView tv = new TextView(getContext());
        tv.setTag(i);
        tv.setOnClickListener(this);

        tv.setGravity(Gravity.CENTER);
        tv.setText(statsValue.text);
        tv.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        FrameLayout.LayoutParams flp = new LayoutParams(ITEM_SIZE, ITEM_SIZE);
        // tv.setLayoutParams(new ViewGroup.LayoutParams(ITEM_SIZE, ITEM_SIZE));
        // MarginLayoutParams lp = new MarginLayoutParams(ITEM_SIZE, ITEM_SIZE);
        // lp.leftMargin = i * MARGIN_SIZE;
        // tv.setLayoutParams(lp);

        if (value < 0) {
          flp.leftMargin = i * MARGIN_SIZE;
          tv.setBackgroundResource(statsValue.normalResId);
          tv.setTextColor(getContext().getResources().getColor(R.color.bg_mask));
        } else {
          flp.leftMargin = (statsValues.size() - 1) * MARGIN_SIZE;
          boolean isSelected = (statsValue.value == value);
          tv.setBackgroundResource(isSelected ? statsValue.selectResId : statsValue.normalResId);
          tv.setTextColor(getContext().getResources()
              .getColor(isSelected ? R.color.rippelColor : R.color.bg_mask));
          tv.setVisibility(isSelected ? VISIBLE : INVISIBLE);
        }
        addView(tv, flp);
      }
    }
  }

  @Override
  public void onClick(View v) {
    int index = (Integer) v.getTag();
    Log.i(TAG, "Click child view " + index);
    if (value < 0) {
      contractAnimation(index);
      value = statsValues.get(index).value;
    } else {
      expandAnimation(index);
      value = NUMBER_NOT_SPECIFIED;
    }
  }

  private void contractAnimation(int index) {
    for (int i = 0; i < getChildCount(); i++) {
      final boolean isSelected = (i == index);
      final TextView tv = (TextView) getChildAt(i);
      AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
      fadeOut.setDuration(200);
      fadeOut.setFillAfter(true);
      fadeOut.setInterpolator(new AccelerateInterpolator());
      TranslateAnimation moveRight =
          new TranslateAnimation(0, (getChildCount() - 1 - i) * MARGIN_SIZE, 0, 0);
      moveRight.setDuration(200);
      moveRight.setFillAfter(true);
      moveRight.setInterpolator(new AccelerateInterpolator());
      AnimationSet animationSet = new AnimationSet(true);
      if (isSelected) {
        tv.setTextColor(getContext().getResources().getColor(R.color.rippelColor));
        tv.setBackgroundResource(statsValues.get(index).selectResId);
      } else {
        animationSet.addAnimation(fadeOut);
      }
      animationSet.addAnimation(moveRight);
      animationSet.setAnimationListener(new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
          FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) tv.getLayoutParams();
          flp.leftMargin = (getChildCount() - 1) * MARGIN_SIZE;
          tv.setLayoutParams(flp);
          tv.setVisibility(isSelected ? VISIBLE : INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
      tv.startAnimation(animationSet);
    }
  }

  private void expandAnimation(int index) {
    for (int i = 0; i < getChildCount(); i++) {
      final boolean isUnselected = (i == index);
      final TextView tv = (TextView) getChildAt(i);
      AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
      fadeIn.setDuration(200);
      fadeIn.setFillAfter(true);
      fadeIn.setInterpolator(new AccelerateInterpolator());
      TranslateAnimation moveLeft =
          new TranslateAnimation(0, -1 * (getChildCount() - 1 - i) * MARGIN_SIZE, 0, 0);
      moveLeft.setDuration(200);
      moveLeft.setFillAfter(true);
      moveLeft.setInterpolator(new AccelerateInterpolator());
      AnimationSet animationSet = new AnimationSet(true);
      if (isUnselected) {
        tv.setTextColor(getContext().getResources().getColor(R.color.bg_mask));
        tv.setBackgroundResource(statsValues.get(index).normalResId);
      } else {
        animationSet.addAnimation(fadeIn);
      }
      animationSet.addAnimation(moveLeft);
      animationSet.setAnimationListener(new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
          tv.setVisibility(VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
          FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) tv.getLayoutParams();
          flp.leftMargin = ((Integer) tv.getTag()) * MARGIN_SIZE;
          tv.setLayoutParams(flp);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
      });
      tv.startAnimation(animationSet);
    }
  }

  public static class StatsValue {

    public int value;
    public String text;
    public int normalResId;
    public int selectResId;

    StatsValue(int value, String text, int normalResId, int selectResId) {
      this.value = value;
      this.text = text;
      this.normalResId = normalResId;
      this.selectResId = selectResId;
    }
  }

  public static int dip2pixel(Context context, float dipValue) {
    return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
        context.getResources().getDisplayMetrics()) + 0.5);
  }
}
