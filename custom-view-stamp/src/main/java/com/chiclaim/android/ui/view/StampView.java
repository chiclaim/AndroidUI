package com.chiclaim.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class StampView extends View {

    private final Paint mPaint = new Paint();

    private int mWidth;
    private int mHeight;
    private int mOutCircleBorder;
    private int mInnerCircleBorder;
    private int mCirclesGap;
    private int mCircleColor;
    private int mTextColor;
    private int mTextAngle;
    private String mRawText;
    private float mTextMargin;
    private int mNormalTextLength;

    private StringBuilder mStringBuilder;
    private StaticLayout mTextLayout;
    private String mShowText;


    public StampView(Context context) {
        super(context);
        init(null);
    }

    public StampView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StampView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StampView);
        mOutCircleBorder = typedArray.getDimensionPixelOffset(R.styleable.StampView_outside_circle_border_width, 0);
        mInnerCircleBorder = typedArray.getDimensionPixelOffset(R.styleable.StampView_inside_circle_border_width, 0);
        mCirclesGap = typedArray.getDimensionPixelOffset(R.styleable.StampView_circles_gap, 0);
        mCircleColor = typedArray.getColor(R.styleable.StampView_circle_color, 0);
        mRawText = typedArray.getString(R.styleable.StampView_text);
        mTextAngle = typedArray.getInt(R.styleable.StampView_text_angle, 25);
        mNormalTextLength = typedArray.getInt(R.styleable.StampView_line_normal_text_length, 3);
        mTextColor = typedArray.getColor(R.styleable.StampView_textColor, 0);
        if (mTextColor == 0) {
            mTextColor = mCircleColor;
        }
        mTextMargin = typedArray.getDimensionPixelOffset(R.styleable.StampView_text_margin, 10);
        typedArray.recycle();
    }

    private int getSize(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                return 0;

            case MeasureSpec.EXACTLY: {
                return size;
            }
        }
        return 0;
    }

    private String getShowText(int step) {
        if (!TextUtils.isEmpty(mShowText)) {
            return mShowText;
        }
        if (mStringBuilder == null) {
            mStringBuilder = new StringBuilder(mRawText.length() + mRawText.length() / step);
        }
        mStringBuilder.setLength(0);
        for (int i = 0; i < mRawText.length(); i += step) {
            mStringBuilder.append(mRawText, i, Math.min(i + step, mRawText.length()));
            if (i + step >= mRawText.length()) {
                continue;
            }
            mStringBuilder.append('\n');
        }
        return mStringBuilder.toString();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getSize(widthMeasureSpec);
        mHeight = getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw outside circle
        mPaint.setAntiAlias(true);
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mOutCircleBorder);
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, (mWidth - 2 * mOutCircleBorder) / 2.0f, mPaint);

        // draw inside circle
        mPaint.setStrokeWidth(mInnerCircleBorder);
        float innerCircleRadius = (mWidth - 2 * mOutCircleBorder - 2 * mInnerCircleBorder - 2 * mCirclesGap) / 2.0f;
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, innerCircleRadius, mPaint);

        canvas.save();

        // draw text
        if (!TextUtils.isEmpty(mRawText)) {
            float textSize = (innerCircleRadius * 2 - mTextMargin * 2) / mNormalTextLength;
            float marginOffset = 0;
            if (mRawText.length() > mNormalTextLength) {
                marginOffset = textSize / 2;
                mShowText = getShowText(mNormalTextLength - 1);
            } else {
                mShowText = mRawText;
            }

            if (mTextLayout == null) {
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(textSize);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setColor(mTextColor);
                textPaint.setAntiAlias(true);
                textPaint.setFakeBoldText(true);
                mTextLayout = new StaticLayout(mShowText, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }

            canvas.rotate(mTextAngle, mWidth / 2f, mHeight / 2f);
            canvas.translate(
                    mOutCircleBorder + mInnerCircleBorder + mCirclesGap + mTextMargin + marginOffset,
                    mHeight / 2f - mTextLayout.getHeight() / 2.f);

            mTextLayout.draw(canvas);

            canvas.restore();

        }


        // test text is center
//        paint.setStrokeWidth((float) 20.0);         //线宽
//        paint.setColor(getResources().getColor(android.R.color.holo_red_light));
//        canvas.drawPoint(width / 2f, height / 2f, paint);
    }
}
