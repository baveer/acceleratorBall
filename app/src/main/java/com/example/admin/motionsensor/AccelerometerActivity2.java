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
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class AccelerometerActivity2 extends Activity implements SensorEventListener {
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

        private float CIRCLE_RADIUS = 0; //pixels

        private Paint blue;
        private Paint green;
        private float x, x_;
        private float y, y_;
        private float viewWidth;
        private float viewHeight;

        public AnimatedView(Context context) {
            super(context);
            blue = new Paint();
            blue.setColor(Color.BLUE);
            green = new Paint();
            green.setColor(Color.GREEN);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
        }

        public void onSensorEvent (SensorEvent event) {
            //CIRCLE_RADIUS= (float) (0.05*viewWidth);
            x = x - event.values[0]*3;
            y = y + event.values[1]*3;

            x_ = x_ + event.values[0]*3;
            y_ = y_ - event.values[1]*3;
            //Make sure we do not draw outside the bounds of the view.
            //So the max values we can draw to are the bounds + the size of the circle
            if (x <= 0 + CIRCLE_RADIUS) {
                x = 0 + CIRCLE_RADIUS;
            }
            if (x >= viewWidth - CIRCLE_RADIUS) {
                x = viewWidth - CIRCLE_RADIUS;
            }
            if (y <= 0 + CIRCLE_RADIUS) {
                y = 0 + CIRCLE_RADIUS;
            }
            if (y >= viewHeight - CIRCLE_RADIUS) {
                y = viewHeight - CIRCLE_RADIUS;
            }

            if (x_ <= 0 - CIRCLE_RADIUS) {
                x_ = 0 - CIRCLE_RADIUS;
            }
            if (x_ >= viewWidth + CIRCLE_RADIUS) {
                x_ = viewWidth + CIRCLE_RADIUS;
            }
            if (y_ <= 0 - CIRCLE_RADIUS) {
                y_ = 0 - CIRCLE_RADIUS;
            }
            if (y_ >= viewHeight + CIRCLE_RADIUS) {
                y_ = viewHeight + CIRCLE_RADIUS;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //naprzemienne kółka
            int numberC = 50;
            float base = 1;
            float minus=base/numberC;
            float przekatna = (float) Math.sqrt((viewHeight*viewHeight)+(viewWidth*viewWidth));
            for (int i =0;i<numberC/2;i++) {
                canvas.drawCircle(x*(1-base), y*(1-base),  przekatna*base, green);
                canvas.drawCircle(x_*(1-base), y_*(1-base),przekatna*base, green);
                base=base-minus;

                canvas.drawCircle(x*(1-base), y*(1-base), przekatna*base, blue);
                canvas.drawCircle(x_*(1-base), y_*(1-base),przekatna*base, blue);
                base=base-minus;
            }

            invalidate();
        }
    }
}