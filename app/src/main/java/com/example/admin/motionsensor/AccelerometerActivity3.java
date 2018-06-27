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
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class AccelerometerActivity3 extends Activity implements SensorEventListener {
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

        private static final int CIRCLE_RADIUS = 25; //pixels

        private Paint blue;
        private Paint green;
        private Paint black;
        private Paint white;

        private float x;
        private float y;
        private float viewWidth;
        private float viewHeight;

        public AnimatedView(Context context) {
            super(context);
            blue = new Paint();
            blue.setColor(Color.BLUE);
            green = new Paint();
            green.setColor(Color.GREEN);
            black = new Paint();
            black.setColor(Color.BLACK);
            white = new Paint();
            white.setColor(Color.WHITE);
            mCustomImage = context.getResources().getDrawable(R.drawable.pin);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
        }

        public void onSensorEvent (SensorEvent event) {
            x = x - event.values[0]*3;
            y = y + event.values[1]*4;
            //ramka
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
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Rect imageBounds = canvas.getClipBounds();

            mCustomImage.setBounds(imageBounds);
            mCustomImage.draw(canvas);

            canvas.drawCircle(x, y, CIRCLE_RADIUS,black);
            canvas.drawCircle(x, y, CIRCLE_RADIUS-5,white);
            canvas.drawCircle(x, y, CIRCLE_RADIUS-10,black);

            invalidate();
        }
    }
}