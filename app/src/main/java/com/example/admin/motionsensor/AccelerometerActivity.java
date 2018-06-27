package com.example.admin.motionsensor;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class AccelerometerActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private AnimatedView mAnimatedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mAnimatedView = new AnimatedView(this);
        //view nie layout
        setContentView(mAnimatedView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAnimatedView.onSensorEvent(event);
        }
    }

    public class AnimatedView extends View {
        private Drawable mCustomImage;


        private static final int circleR = 25; //pixels

        private Paint mPaint, blue;
        private float x;
        private float y;

        public AnimatedView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setColor(Color.RED);
            blue = new Paint();
            blue.setColor(Color.BLUE);
            mCustomImage = context.getResources().getDrawable(R.drawable.tlo);
        }

        private int viewWidth;
        private int viewHeight;

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
        }

        public float rec_1_Left =0, rec_1_Top =200, rec_1_Right =700, rec_1_Bottom =300;
        public float rec_2_Left =300, rec_2_Top =400, rec_2_Right =1100, rec_2_Bottom =500;
        public float rec_3_Left =0, rec_3_Top =600, rec_3_Right =700, rec_3_Bottom =666;
        public float rec_7_Left =500, rec_7_Top =900, rec_7_Right =1100, rec_7_Bottom =950;
        public float rec_4_Left =500, rec_4_Top =750, rec_4_Right =550, rec_4_Bottom =1100;
        public float rec_5_Left =0, rec_5_Top =1150, rec_5_Right =800, rec_5_Bottom =1200;
        public float rec_6_Left =400, rec_6_Top =1450, rec_6_Right =700, rec_6_Bottom =1600;

        public void onSensorEvent (SensorEvent event) {
            x = x - event.values[0]*2;
            y = y + event.values[1];

            //ramka
            if (x <= 0 + circleR) {
                x = 0 + circleR;
            }
            if (x >= viewWidth - circleR) {
                x = viewWidth - circleR;
            }
            if (y <= 0 + circleR) {
                y = 0 + circleR;
            }
            if (y >= viewHeight - circleR) {
                y = viewHeight - circleR;
            }
            //prostokąty
            if ((y>= rec_1_Top   - circleR) && (y <= rec_1_Top    - circleR/2) && x <= rec_1_Right + circleR /2)  {y = rec_1_Top - circleR;}//trzyma na górze
            if ((y> rec_1_Top    - circleR) && (y < rec_1_Bottom  + circleR/2) && x <= rec_1_Right + circleR)     {x = rec_1_Right + circleR;}//trzyma po prawej
            if ((y<=rec_1_Bottom + circleR) && (y >= rec_1_Bottom + circleR/2) && x <= rec_1_Right + circleR /2)  {y = rec_1_Bottom + circleR;}//trzyma na dole

//            if ((y>=400- circleR) && (y <= 400- circleR /2) && x >=viewWidth-700 - circleR /2)  {y = 400 - circleR;}
//            if ((y>400- circleR) && (y < 410+ circleR) && x >=viewWidth-700 - circleR)  {x = viewWidth-700 - circleR;}//rect2 trzyma po lewej
//            if ((y<=420+ circleR) && (y >= 420+ circleR /2) && x >=viewWidth-700 - circleR /2)  {y = 420 + circleR;}

            if ((y>=rec_2_Top    - circleR) && (y <= rec_2_Top      - circleR/2) && x >= rec_2_Left  - circleR /2)  {y = rec_2_Top    - circleR;}
            if ((y>rec_2_Top     - circleR) && (y <  rec_2_Bottom   + circleR/2) && x >= rec_2_Left  - circleR)     {x = rec_2_Left   - circleR;}//trzyma po lewej
            if ((y<=rec_2_Bottom + circleR) && (y >= rec_2_Bottom   + circleR/2) && x >= rec_2_Left  - circleR /2)  {y = rec_2_Bottom + circleR;}

            if ((y>= rec_3_Top   - circleR) && (y <= rec_3_Top    - circleR/2) && x <= rec_3_Right + circleR /2)  {y = rec_3_Top - circleR;}
            if ((y> rec_3_Top    - circleR) && (y < rec_3_Bottom  + circleR/2) && x <= rec_3_Right + circleR)     {x = rec_3_Right + circleR;}
            if ((y<=rec_3_Bottom + circleR) && (y >= rec_3_Bottom + circleR/2) && x <= rec_3_Right + circleR /2)  {y = rec_3_Bottom + circleR;}

            if ((y>= rec_4_Top   - circleR) && (y <= rec_4_Top    - circleR/2) && (x <= rec_4_Right + circleR /2) && x>=rec_4_Left - circleR)  {y = rec_4_Top - circleR;}//góra
            if ((y> rec_4_Top    - circleR) && (y < rec_4_Bottom  + circleR/2) && (x < rec_4_Right + circleR) && x>rec_4_Left + circleR)     {x = rec_4_Right + circleR;}//prawo
            if ((y>rec_4_Top     - circleR) && (y < rec_4_Bottom  + circleR/2) && (x >= rec_4_Left  - circleR) && x<=rec_4_Right - circleR)   {x = rec_4_Left   - circleR;}//lewo
            if ((y<=rec_4_Bottom + circleR) && (y >= rec_4_Bottom + circleR/2) && (x <= rec_4_Right + circleR /2) && x>=rec_4_Left - circleR) {y = rec_4_Bottom + circleR;}//dół

            if ((y>= rec_5_Top   - circleR) && (y <= rec_5_Top    - circleR/2) && x <= rec_5_Right + circleR /2)  {y = rec_5_Top - circleR;}
            if ((y> rec_5_Top    - circleR) && (y < rec_5_Bottom  + circleR/2) && x <= rec_5_Right + circleR)     {x = rec_5_Right + circleR;}
            if ((y<=rec_5_Bottom + circleR) && (y >= rec_5_Bottom + circleR/2) && x <= rec_5_Right + circleR /2)  {y = rec_5_Bottom + circleR;}

            if ((y>= rec_6_Top   - circleR) && (y <= rec_6_Top    - circleR/2) && (x <= rec_6_Right + circleR /2) && x>=rec_6_Left - circleR)  {y = 0 - circleR;}//tp na góre
            if ((y> rec_6_Top    - circleR) && (y < rec_6_Bottom  + circleR/2) && (x < rec_6_Right + circleR) && x>rec_6_Left + circleR)     {x = 1100 + circleR;}//tp na prawo
            if ((y>rec_6_Top     - circleR) && (y < rec_6_Bottom  + circleR/2) && (x >= rec_6_Left  - circleR) && x<=rec_6_Right - circleR)   {x = 0   - circleR;}//tp na lewo
            if ((y<=rec_6_Bottom + circleR) && (y >= rec_6_Bottom + circleR/2) && (x <= rec_6_Right + circleR /2) && x>=rec_6_Left - circleR) {y = 1750 + circleR;}//tp na dół

            if ((y>=rec_7_Top    - circleR) && (y <= rec_7_Top      - circleR/2) && x >= rec_7_Left  - circleR /2)  {y = rec_7_Top    - circleR;}
            if ((y>rec_7_Top     - circleR) && (y <  rec_7_Bottom   + circleR/2) && x >= rec_7_Left  - circleR)     {x = rec_7_Left   - circleR;}//trzyma po lewej
            if ((y<=rec_7_Bottom + circleR) && (y >= rec_7_Bottom   + circleR/2) && x >= rec_7_Left  - circleR /2)  {y = rec_7_Bottom + circleR;}
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Rect imageBounds = canvas.getClipBounds();

            mCustomImage.setBounds(imageBounds);
            mCustomImage.draw(canvas);
            canvas.drawRect(rec_1_Left, rec_1_Top, rec_1_Right, rec_1_Bottom,mPaint);
            canvas.drawRect(rec_2_Left, rec_2_Top, rec_2_Right, rec_2_Bottom,mPaint);
            canvas.drawRect(rec_3_Left, rec_3_Top, rec_3_Right, rec_3_Bottom,mPaint);
            canvas.drawRect(rec_4_Left, rec_4_Top, rec_4_Right, rec_4_Bottom,mPaint);
            canvas.drawRect(rec_5_Left, rec_5_Top, rec_5_Right, rec_5_Bottom,mPaint);
            canvas.drawRect(rec_6_Left, rec_6_Top, rec_6_Right, rec_6_Bottom,blue);
            canvas.drawRect(rec_7_Left, rec_7_Top, rec_7_Right, rec_7_Bottom,mPaint);

            canvas.drawCircle(x, y, circleR, mPaint);
            invalidate();//ciągle rysuje
        }
    }
}