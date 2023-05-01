package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {
    Drawable mClearButtonImage;

    private void init(){
        // define clear button
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.clear_opaque_24dp, null);

        // show clear button when user fill or change the edit text
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            // call function
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // action when component touched
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // there are 4 position for component: start(no.0), top(no.1), end(no.2), bottom(no.3)
                // check if there is a clear button at no.2 (end)
                if (getCompoundDrawablesRelative()[2] != null) {
                    // value to check whether the button has been clicked or not
                    boolean isClicked = false;

                    // check the layout view
                    if (getLayoutDirection() == LAYOUT_DIRECTION_LTR){
                        // clear button starts on the right side of the edit text (Used for LTR languages)
                        float clearButtonStartPosition = (getWidth() - getPaddingEnd()
                                - mClearButtonImage.getIntrinsicWidth());

                        // if the clicked position is after clearButtonStartPosition,
                        // then the clear button was successfully clicked
                        if (event.getX() > clearButtonStartPosition) {
                            isClicked = true;
                        }

                    }else{
                        // clear button ends on the left side of the field (Used for RTL languages)
                        float clearButtonEndPosition = mClearButtonImage
                                .getIntrinsicWidth() + getPaddingStart();

                        // if the clicked position is before clearButtonStartPosition,
                        // then the clear button was successfully clicked
                        if (event.getX() < clearButtonEndPosition) {
                            isClicked = true;
                        }
                    }

                    if (isClicked) {
                        // checking if clicked in down (when the button is pressed)
                        // or up (when the button is released) position
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // if action is down, change to black clear button
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.clear_black_24dp, null);
                            showClearButton();
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // if action is up, change to gray/opaque clear button
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.clear_opaque_24dp, null);
                            // if action is up, clear the edit text
                            getText().clear();
                            // if action is up, hide the clear button
                            hideClearButton();
                            return true;
                        }
                    }
                    // if the button is not clicked, there is no action performed
                    else {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        // calling init in constructor
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // calling init in constructor
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // calling init in constructor
        init();
    }

    // show Clear Button
    private void showClearButton(){
    // add drawable component in edit text in end/right position
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    // hide Clear Button
    private void hideClearButton(){
    // hide drawable component from edit text
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}

