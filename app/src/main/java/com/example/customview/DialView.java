package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DialView extends View {
    // counter label
    private static int SELECTION_COUNT = 4;
    // store width and height view
    private float mWidth, mHeight;
    // paint for create text in canvas
    private Paint mTextPaint;
    // paint for create other attribute
    private Paint mDialPaint;
    // radius to create circle
    private float mRadius;
    // marker position
    private int mActiveSelection;
    // string number
    private StringBuffer mTempLabel = new StringBuffer(8);
    // store coordinate X and Y
    private final float [] mTempResult = new float[2];

    private void init(){
        // initialize paint for text
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);

        // initialize paint for dial
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(Color.GRAY);

        // marker start position
        mActiveSelection = 0;

        // event to change marker position
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // every clicked, value mActiveSelection will increase
                mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT;
                if (mActiveSelection >= 1){
                    mDialPaint.setColor(Color.GREEN);
                }else {
                    mDialPaint.setColor(Color.GRAY);
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (float) Math.min(mWidth, mHeight/2*0.8);
    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // method to calculate label and marker position
    private float[] computeXYPosition(final int position, final float radius){
        float[] result = mTempResult;
        Double startAngle = Math.PI * (9/8d);
        Double angle = startAngle + (position * (Math.PI/4));
        result[0] = (float) (radius * Math.cos(angle)) + (mWidth/2);
        result[1] = (float) (radius * Math.sin(angle)) + (mHeight/2);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw circle
        canvas.drawCircle(mWidth/2, mHeight/2, mRadius, mDialPaint);
        // draw label
        final float labelRadius = mRadius + 20;
        StringBuffer label = mTempLabel;
        for (int i = 0; i < SELECTION_COUNT; i++){
            float[] xyData = computeXYPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(8);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
        }
        // draw small indicator circle
        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYPosition(mActiveSelection, markerRadius);
        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);
    }
}
