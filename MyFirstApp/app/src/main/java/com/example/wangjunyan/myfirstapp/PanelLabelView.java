package com.example.wangjunyan.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangjunyan on 17/9/29.
 */

public class PanelLabelView extends View {

  private static float ARC_DEGREE_HALF = 36f;
  private static int PROGRESS_THICKNESS = 2;
  private static int START_LENGTH = 4;
  private static int END_RADIUS = 3;
  private static int TEXT_MARGIN = 5;

  private boolean hideClubRecommend;
  private boolean hideElevation;
  private int powerPercent = 0;
  private double distance;
  private double deltaElevation;
  private String elevation = "14 ft\u2191";
  private String desc = "Mid";
  private String dist = "125";
  private String unit = "y";
  private String clubRecommend = "3W 59%";

  private int defaultSize;
  private Paint labelP;
  private Paint blackP;
  private Paint whiteP;
  private Paint grayP;
  private Paint blueP;
  private Rect labelTextRect;
  private RectF oval;

  public PanelLabelView(Context context) {
    super(context);
    init(context);
  }

  public PanelLabelView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public PanelLabelView(Context context, AttributeSet attrs, int defaultStyle) {
    super(context, attrs, defaultStyle);
    init(context);
  }

  private void init(Context context) {
    defaultSize = dip2px(context, 200f);

    labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
    labelP.setColor(Color.WHITE);
    labelTextRect = new Rect();

    blackP = new Paint(Paint.ANTI_ALIAS_FLAG);
    blackP.setColor(Color.parseColor("#ff242c31"));
    blackP.setStyle(Paint.Style.FILL);

    whiteP = new Paint(Paint.ANTI_ALIAS_FLAG);
    whiteP.setColor(Color.WHITE);
    whiteP.setStrokeWidth(2f);
    whiteP.setStyle(Paint.Style.STROKE);

    grayP = new Paint(Paint.ANTI_ALIAS_FLAG);
    grayP.setColor(Color.parseColor("#99ffffff"));
    grayP.setStrokeWidth(dip2px(context, PROGRESS_THICKNESS));
    grayP.setStyle(Paint.Style.STROKE);

    blueP = new Paint(Paint.ANTI_ALIAS_FLAG);
    blueP.setColor(Color.parseColor("#ff10acff"));
    blueP.setStrokeWidth(dip2px(context, PROGRESS_THICKNESS));
    blueP.setStyle(Paint.Style.STROKE);

    oval = new RectF();
    //backgroud #374213
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int defaultWidth = defaultSize + getPaddingLeft() + getPaddingRight();
    int defaultHeight = defaultSize + getPaddingTop() + getPaddingBottom();
    int width = measureSize(widthMeasureSpec, defaultWidth);
    int height = measureSize(heightMeasureSpec, defaultHeight);
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int w = getMeasuredWidth();
    int h = getMeasuredHeight();
    int d = Math.min(w, h);
    int px = d / 2;
    int py = d / 2;
    int radius = d / 2 - dip2px(getContext(), END_RADIUS);
    double radians = Math.toRadians(ARC_DEGREE_HALF);
    int arcH = (int)(radius + radius * Math.cos(radians));
    int deltaH = radius - arcH / 2;
    float startAngle = 90f + ARC_DEGREE_HALF;
    float sweepAngle = 360f - ARC_DEGREE_HALF * 2;
    canvas.drawColor(Color.TRANSPARENT);
    oval.set(px - radius, py - radius, px + radius, px + radius);
    canvas.drawArc(oval, startAngle, sweepAngle, false, blackP);
    blueP.setStyle(Paint.Style.STROKE);
    canvas.drawArc(oval, startAngle, sweepAngle, false, grayP);
    canvas.drawArc(oval, startAngle, sweepAngle * powerPercent / 100, false, blueP);
    int startX = px - (int) (radius * Math.sin(radians));
    int startY = py + (int) (radius * Math.cos(radians));
    int deltaX = (int) (dip2px(getContext(), START_LENGTH / 2) * Math.sin(radians));
    int deltaY = (int) (dip2px(getContext(), START_LENGTH / 2) * Math.cos(radians));
    canvas.drawLine(startX - deltaX, startY + deltaY, startX + deltaX, startY - deltaY,
        blueP);
    float endDegree = 360 - (startAngle + sweepAngle * powerPercent / 100);
    int endX = (int) (px + radius * Math.cos(Math.toRadians(endDegree)));
    int endY = (int) (py - radius * Math.sin(Math.toRadians(endDegree)));
    blueP.setStyle(Paint.Style.FILL);
    canvas.drawCircle(endX, endY, dip2px(getContext(), END_RADIUS), blueP);

    labelP.setTextSize(dip2px(getContext(), 14f));
    labelP.getTextBounds(elevation, 0, elevation.length(), labelTextRect);
    int elevationW = labelTextRect.width();
    int elevationH = labelTextRect.height();
    labelP.getTextBounds(desc, 0, desc.length(), labelTextRect);
    int descW = labelTextRect.width();
    int descH = labelTextRect.height();

    labelP.setTextSize(dip2px(getContext(), 32f));
    labelP.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    labelP.getTextBounds(dist, 0, dist.length(), labelTextRect);
    int distanceW = labelTextRect.width();
    int distanceH = labelTextRect.height();
    labelP.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

    labelP.setTextSize(dip2px(getContext(), 11f));
    labelP.getTextBounds(unit, 0, unit.length(), labelTextRect);
    int unitW = labelTextRect.width();
    int unitH = labelTextRect.height();
    labelP.getTextBounds(clubRecommend, 0, clubRecommend.length(), labelTextRect);
    int clubW = labelTextRect.width();
    int clubH = labelTextRect.height();

    int marginH = dip2px(getContext(), TEXT_MARGIN);
    int totalH = elevationH + distanceH + descH + 2 * marginH;
    int topH = py - deltaH - totalH / 2;
    labelP.setTextSize(dip2px(getContext(), 14f));
    canvas.drawText(elevation, px - elevationW / 2, topH + elevationH, labelP);

    labelP.setTextSize(dip2px(getContext(), 32f));
    labelP.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    canvas
        .drawText(dist, px - (distanceW + unitW) / 2 - dip2px(getContext(), 2f),
            topH + elevationH + marginH + distanceH,
            labelP);
    labelP.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

    labelP.setTextSize(dip2px(getContext(), 11f));
    canvas.drawText(unit, px + (distanceW - unitW) / 2 + dip2px(getContext(), 2f),
        topH + elevationH + marginH + distanceH, labelP);

    labelP.setTextSize(dip2px(getContext(), 11f));
    canvas.drawText(desc, px - descW / 2, topH + elevationH + marginH + distanceH + marginH + descH,
        labelP);

    labelP.setShadowLayer(dip2px(getContext(), 2), 0f, dip2px(getContext(), 2), Color.LTGRAY);
    canvas.drawText(clubRecommend, px - clubW / 2, py + arcH - radius + clubH + dip2px(getContext(), 2f), labelP);
    labelP.setShadowLayer(0f, 20f, 20f, Color.LTGRAY);
  }

  private int measureSize(int measureSpec, int defaultValue) {
    int result;
    int measureMode = MeasureSpec.getMode(measureSpec);
    int measureSize = MeasureSpec.getSize(measureSpec);
    if (measureMode == MeasureSpec.UNSPECIFIED) {
      result = defaultValue;
    } else if (measureMode == MeasureSpec.AT_MOST) {
      result = Math.min(defaultValue, measureSize);
    } else {
      result = measureSize;
    }
    return result;
  }

  private int dip2px(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }

  public void setPowerPercent(int percent) {
    this.powerPercent = percent;
    invalidate();
  }
}
