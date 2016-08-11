package com.sianav.superflipcheckbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by farid on 08/06/2016.
 */
public class FlipCheckbox extends FrameLayout implements Checkable {

    private final int ANIMATION_DURATION = 500;
    private final int BASE_TEXT_SIZE = 30;
    private boolean mChecked;
    private int mCheckedDrawable;
    private int mUncheckedDrawable;
    private String mCheckedText;
    private String mUncheckedText;
    private int mCheckedSource;
    private int mUncheckedSource;
    private int mCheckedTint;
    private int mUncheckedBackgroundTint;
    private int mCheckedBackgroundTint;
    private int mUncheckedTint;
    private int mImageCheckedPadding;
    private int mImageUncheckedPadding;
    private View mCheckedView;
    private View mUncheckedView;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public FlipCheckbox(Context context) {
        super(context);
        init();
    }

    public FlipCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FlipCheckbox,
                0, 0);

        try {
            mCheckedDrawable = a.getResourceId(R.styleable.FlipCheckbox_checkedBackground, 0);
            mUncheckedDrawable = a.getResourceId(R.styleable.FlipCheckbox_uncheckedBackground, 0);
            mCheckedSource = a.getResourceId(R.styleable.FlipCheckbox_checkedSrc, 0);
            mUncheckedSource = a.getResourceId(R.styleable.FlipCheckbox_uncheckedSrc, 0);
            mCheckedTint = a.getColor(R.styleable.FlipCheckbox_checkedTint, 0);
            mUncheckedTint = a.getColor(R.styleable.FlipCheckbox_uncheckedTint, 0);
            mCheckedBackgroundTint = a.getColor(R.styleable.FlipCheckbox_checkedBackgroundTint, 0);
            mUncheckedBackgroundTint = a.getColor(R.styleable.FlipCheckbox_uncheckedBackgroundTint, 0);
            mChecked = a.getBoolean(R.styleable.FlipCheckbox_checked, false);
            mImageCheckedPadding = a.getLayoutDimension(R.styleable.FlipCheckbox_imageCheckedPadding, 0);
            mImageUncheckedPadding = a.getLayoutDimension(R.styleable.FlipCheckbox_imageUncheckedPadding, 0);
            mCheckedText = a.getString(R.styleable.FlipCheckbox_checkedText);
            mUncheckedText = a.getString(R.styleable.FlipCheckbox_uncheckedText);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        setCheckedImage(mCheckedSource);
        setUncheckedImage(mUncheckedSource);
        if (mUncheckedText != null) setUncheckedText(mUncheckedText);
        if (mCheckedText != null) setCheckedText(mCheckedText);

        setClickable(true);
        setFocusable(true);
        if (mChecked) {
            mCheckedView.setVisibility(VISIBLE);
            mUncheckedView.setVisibility(GONE);
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    private void check(int duration) {
        FlipAnimation flipAnimation = new FlipAnimation(mUncheckedView, mCheckedView, duration);
        startAnimation(flipAnimation);
    }

    private void uncheck(int duration) {
        FlipAnimation flipAnimation = new FlipAnimation(mUncheckedView, mCheckedView, duration);
        flipAnimation.reverse();
        startAnimation(flipAnimation);
    }

    public void setChecked(boolean checked, int animationDuration) {
        if (checked != mChecked) {
            if (checked) check(animationDuration);
            else uncheck(animationDuration);
        }
        mChecked = checked;
        if (mOnCheckedChangeListener != null) mOnCheckedChangeListener.onCheckedChanged(mChecked);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked != mChecked) {
            if (checked) check(ANIMATION_DURATION);
            else uncheck(ANIMATION_DURATION);
        }
        mChecked = checked;
        if (mOnCheckedChangeListener != null) mOnCheckedChangeListener.onCheckedChanged(mChecked);
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public View getCheckedView() {
        return mCheckedView;
    }

    public View getUncheckedView() {
        return mUncheckedView;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mUncheckedView.setEnabled(enabled);
        mCheckedView.setEnabled(enabled);
    }

    public void setUncheckedImage(int resId) {
        int index = indexOfChild(mUncheckedView);
        if (mUncheckedView != null) removeView(mUncheckedView);
        mUncheckedView = new ImageView(getContext());
        ((ImageView) mUncheckedView).setImageResource(resId);
        ((ImageView) mUncheckedView).setColorFilter(mUncheckedTint);
        ((ImageView) mUncheckedView).setScaleType(ImageView.ScaleType.FIT_CENTER);
        mUncheckedView.setBackgroundResource(mUncheckedDrawable);
        mUncheckedView.setPadding(mImageUncheckedPadding, mImageUncheckedPadding, mImageUncheckedPadding, mImageUncheckedPadding);
        if (mUncheckedBackgroundTint != 0) {
            mUncheckedView.getBackground().setColorFilter(mUncheckedBackgroundTint, PorterDuff.Mode.SRC_IN);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (index != -1) addView(mUncheckedView, index, params);
        else addView(mUncheckedView, params);
    }

    public void setCheckedImage(int resId) {
        int index = indexOfChild(mCheckedView);
        if (mCheckedView != null) removeView(mCheckedView);
        mCheckedView = new ImageView(getContext());
        ((ImageView) mCheckedView).setImageResource(resId);
        ((ImageView) mCheckedView).setColorFilter(mCheckedTint);
        ((ImageView) mCheckedView).setScaleType(ImageView.ScaleType.FIT_CENTER);
        mCheckedView.setBackgroundResource(mCheckedDrawable);
        mCheckedView.setPadding(mImageCheckedPadding, mImageCheckedPadding, mImageCheckedPadding, mImageCheckedPadding);
        if (mCheckedBackgroundTint != 0) {
            mCheckedView.getBackground().setColorFilter(mCheckedBackgroundTint, PorterDuff.Mode.SRC_IN);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (index != -1) addView(mCheckedView, index, params);
        else addView(mCheckedView, params);
    }

    public String getUncheckedText() {
        return mUncheckedText;
    }

    public void setUncheckedText(String text) {
        int index = indexOfChild(mUncheckedView);
        if (mUncheckedView != null) removeView(mUncheckedView);
        mUncheckedView = new TextView(getContext());
        ((TextView) mUncheckedView).setTextColor(mUncheckedTint);
        ((TextView) mUncheckedView).setText(text);
        ((TextView) mUncheckedView).setGravity(Gravity.CENTER);
        ((TextView) mUncheckedView).setTextSize(BASE_TEXT_SIZE);
        mUncheckedView.setBackgroundResource(mUncheckedDrawable);
        if (mUncheckedBackgroundTint != 0) {
            mUncheckedView.getBackground().setColorFilter(mUncheckedBackgroundTint, PorterDuff.Mode.SRC_IN);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (index != -1) addView(mUncheckedView, index, params);
        else addView(mUncheckedView, params);
    }

    public String getCheckedText() {
        return mCheckedText;
    }

    public void setCheckedText(String text) {
        int index = indexOfChild(mCheckedView);
        if (mCheckedView != null) removeView(mCheckedView);
        mCheckedView = new TextView(getContext());
        ((TextView) mCheckedView).setTextColor(mCheckedTint);
        ((TextView) mCheckedView).setText(text);
        ((TextView) mCheckedView).setGravity(Gravity.CENTER);
        ((TextView) mCheckedView).setTextSize(BASE_TEXT_SIZE);
        mCheckedView.setBackgroundResource(mCheckedDrawable);
        if (mCheckedBackgroundTint != 0) {
            mCheckedView.getBackground().setColorFilter(mCheckedBackgroundTint, PorterDuff.Mode.SRC_IN);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (index != -1) addView(mCheckedView, index, params);
        else addView(mCheckedView, params);
    }

    public int getCheckedTint() {
        return mCheckedTint;
    }

    public void setCheckedTint(int checkedTint) {
        mCheckedTint = checkedTint;
        init();
    }

    public int getUncheckedBackgroundTint() {
        return mUncheckedBackgroundTint;
    }

    public void setUncheckedBackgroundTint(int uncheckedBackgroundTint) {
        mUncheckedBackgroundTint = uncheckedBackgroundTint;
        init();
    }

    public int getCheckedBackgroundTint() {
        return mCheckedBackgroundTint;
    }

    public void setCheckedBackgroundTint(int checkedBackgroundTint) {
        mCheckedBackgroundTint = checkedBackgroundTint;
        init();
    }

    public int getUncheckedTint() {
        return mUncheckedTint;
    }

    public void setUncheckedTint(int uncheckedTint) {
        mUncheckedTint = uncheckedTint;
        init();
    }

    public int getImageCheckedPadding() {
        return mImageCheckedPadding;
    }

    public void setImageCheckedPadding(int imageCheckedPadding) {
        mImageCheckedPadding = imageCheckedPadding;
        init();
    }

    public int getImageUncheckedPadding() {
        return mImageUncheckedPadding;
    }

    public void setImageUncheckedPadding(int imageUncheckedPadding) {
        mImageUncheckedPadding = imageUncheckedPadding;
        init();
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }
}
