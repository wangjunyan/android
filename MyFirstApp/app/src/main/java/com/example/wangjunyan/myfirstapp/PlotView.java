package com.example.wangjunyan.myfirstapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangjunyan on 17/5/26.
 */

public class PlotView extends View {

  private Paint redP;
  private Paint greenP;
  private Paint blueP;
  private Paint grayP;
  private int bear = 0;
  private int tilt = 0;
  private float transX = 0f;
  private float transY = 0f;
  private int rotateZ = 0;

  public PlotView(Context context) {
    super(context);
    init();
  }

  public PlotView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PlotView(Context context, AttributeSet attrs, int defaultStyle) {
    super(context, attrs, defaultStyle);
    init();
  }

  private void init() {
    setFocusable(true);

    redP = new Paint(Paint.ANTI_ALIAS_FLAG);
    redP.setColor(Color.RED);
    redP.setStrokeWidth(2f);
    redP.setStyle(Paint.Style.STROKE);

    greenP = new Paint(Paint.ANTI_ALIAS_FLAG);
    greenP.setColor(Color.GREEN);
    greenP.setStrokeWidth(2f);
    greenP.setStyle(Paint.Style.STROKE);

    blueP = new Paint(Paint.ANTI_ALIAS_FLAG);
    blueP.setColor(Color.BLUE);
    blueP.setStrokeWidth(2f);
    blueP.setStyle(Paint.Style.STROKE);

    grayP = new Paint(Paint.ANTI_ALIAS_FLAG);
    grayP.setColor(Color.GRAY);
    grayP.setStrokeWidth(1f);

  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int measureWidth = measure(widthMeasureSpec);
    int measureHeight = measure(heightMeasureSpec);
    int d = Math.min(measureWidth, measureHeight);
    setMeasuredDimension(d, d);
  }

  private int measure(int measureSpec) {
    int result;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);
    if (specMode == MeasureSpec.UNSPECIFIED) {
      result = 500;
    } else {
      result = specSize;
    }
    return result;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int w = getMeasuredWidth();
    int h = getMeasuredHeight();
    int n = 100;
    float delta = 1.0f / n;
    float scale = w / 4.0f;

    canvas.drawLine(0, h, w, h, grayP);
    canvas.drawLine(0, h / 2, w, h / 2, grayP);
    canvas.drawLine(w / 2, 0, w / 2, h, grayP);

    Path path = new Path();
    Path pathL = new Path();
    Path pathR = new Path();

    float[] m32 = new float[16];
    Matrix.setIdentityM(m32, 0);
    Matrix.scaleM(m32, 0, scale, scale, scale);
    float[] m21 = new float[16];
    Matrix.setIdentityM(m21, 0);
    Matrix.rotateM(m21, 0, 180f, 1f, 0f, 0f);
    float[] m10 = new float[16];
    Matrix.setIdentityM(m10, 0);
    Matrix.translateM(m10, 0, w / 2f, w / 2f, 0f);
    float[] m = new float[16];
    Matrix.multiplyMM(m, 0, m10, 0, m21, 0);
    Matrix.multiplyMM(m, 0, m, 0, m32, 0);

    float[] mL = new float[16];
    Matrix.setIdentityM(mL, 0);
    Matrix.rotateM(mL, 0, 30f, 1f, 0f, 0f);
    float[] mR = new float[16];
    Matrix.setIdentityM(mR, 0);
    Matrix.rotateM(mR, 0, -30f, 1f, 0f, 0f);

    float[] mC = new float[16];
    Matrix.setIdentityM(mC, 0);
    // Matrix.setLookAtM(mC, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f);

    float[] mTransXY = new float[16];
    Matrix.setIdentityM(mTransXY, 0);
    Matrix.translateM(mTransXY, 0, transX, transY, 0);
    float[] mRotateZ = new float[16];
    Matrix.setIdentityM(mRotateZ, 0);
    Matrix.rotateM(mRotateZ, 0, rotateZ, 0f, 0f, 1f);

    float xc = (float) (Math.cos(Math.toRadians(bear)) * Math.sin(Math.toRadians(tilt)));
    float yc = (float) (Math.sin(Math.toRadians(bear)) * Math.sin(Math.toRadians(tilt)));
    float zc = (float) Math.cos(Math.toRadians(tilt));

    Matrix.setLookAtM(mC, 0, xc, yc, zc, 0f, 0f, 0f, 0f, 1f, 0f);
    float[] mP = new float[16];
    Matrix.setIdentityM(mP, 0);
    Matrix.orthoM(mP, 0, -1f, 1f, -1f, 1f, 1f, -1f);
    float[] mCP = new float[16];
    Matrix.multiplyMM(mCP, 0, mP, 0, mC, 0);

    mCP = new float[] { 1, 0, 0, 0,
            0, 1, 0, 0,
            xc / zc, yc / zc, 0, 0,
            0, 0, 0, 1 };

    for (int i = 0; i <= n; i++) {
      // float x = i * delta;
      // float y = (float) ((x - w / 2.0) * (x - w / 2.0) * (-2.0) / w + w / 2.0);
      // float xo = i * 0.02f - 1;
      // float yo = -0.5f * xo * xo + 0.5f;
      // float xt = scale * xo + w * 0.5f;
      // float yt = scale * yo;
      // path.lineTo(xt, yt);

      // float x3 = i * 0.02f - 1;
      // float y3 = -0.5f * x3 * x3 + 0.5f;
      // float[] point3 = new float[] { x3, y3, 0, 1 };
      // float[] point0 = new float[4];
      // Matrix.multiplyMV(point0, 0, m, 0, point3, 0);
      // path.lineTo(point0[0], point0[1]);
      //float x3 = i * delta - 1;
      //float z3 = -0.5f * x3 * x3 + 0.5f;

      float x3 = i * delta;
      float z3 = -2f * (x3 - 0.5f) * (x3 - 0.5f) + 0.5f;


      float[] point3d = new float[] { x3, 0f, z3, 1 };
      float[] point3dL = new float[4];
      Matrix.multiplyMV(point3dL, 0, mL, 0, point3d, 0);
      float[] point3dR = new float[4];
      Matrix.multiplyMV(point3dR, 0, mR, 0, point3d, 0);

      Matrix.multiplyMV(point3d, 0, mRotateZ, 0, point3d, 0);
      Matrix.multiplyMV(point3d, 0, mTransXY, 0, point3d, 0);

      Matrix.multiplyMV(point3dL, 0, mRotateZ, 0, point3dL, 0);
      Matrix.multiplyMV(point3dL, 0, mTransXY, 0, point3dL, 0);

      Matrix.multiplyMV(point3dR, 0, mRotateZ, 0, point3dR, 0);
      Matrix.multiplyMV(point3dR, 0, mTransXY, 0, point3dR, 0);

      float xp = xc - (point3d[0] - xc) * zc / (point3d[2] - zc);
      float yp = yc - (point3d[1]  - yc) * zc / (point3d[2] - zc);
      float xpL = xc - (point3dL[0] - xc) * zc / (point3dL[2] - zc);
      float ypL = yc - (point3dL[1]  - yc) * zc / (point3dL[2] - zc);
      float xpR = xc - (point3dR[0] - xc) * zc / (point3dR[2] - zc);
      float ypR = yc - (point3dR[1]  - yc) * zc / (point3dR[2]- zc);

      Matrix.multiplyMV(point3d, 0, mCP, 0, point3d, 0);
      Matrix.multiplyMV(point3dL, 0, mCP, 0, point3dL, 0);
      Matrix.multiplyMV(point3dR, 0, mCP, 0, point3dR, 0);

      point3d = new float[]{xp, yp, 0, 1};
      point3dL = new float[]{xpL, ypL, 0, 1};
      point3dR = new float[]{xpR, ypR, 0, 1};

      float[] point2d = new float[4];
      float[] point2dL = new float[4];
      float[] point2dR = new float[4];

      Matrix.multiplyMV(point2d, 0, m, 0, point3d, 0);
      Matrix.multiplyMV(point2dL, 0, m, 0, point3dL, 0);
      Matrix.multiplyMV(point2dR, 0, m, 0, point3dR, 0);

      if(i == 0){
        path.moveTo(point2d[0], point2d[1]);
        pathL.moveTo(point2dL[0], point2dL[1]);
        pathR.moveTo(point2dR[0], point2dR[1]);
      }else{
        path.lineTo(point2d[0], point2d[1]);
        pathL.lineTo(point2dL[0], point2dL[1]);
        pathR.lineTo(point2dR[0], point2dR[1]);
      }

    }
    canvas.drawPath(pathL, redP);
    canvas.drawPath(path, blueP);
    canvas.drawPath(pathR, greenP);
  }

  public void setBear(int bear) {
    this.bear = bear;
    invalidate();
  }

  public void setTilt(int tilt) {
    this.tilt = tilt;
    invalidate();
  }

  public void setTransX(float transX){
    this.transX = transX;
    invalidate();
  }

  public void setTransY(float transY){
    this.transY = transY;
    invalidate();
  }

  public void setRotateZ(int rotateZ){
    this.rotateZ = rotateZ;
    invalidate();
  }
}
