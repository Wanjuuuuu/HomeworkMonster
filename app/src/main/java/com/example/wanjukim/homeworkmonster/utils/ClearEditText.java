package com.example.wanjukim.homeworkmonster.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.wanjukim.homeworkmonster.R;

import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by Wanju Kim on 2018-02-08.
 */

public class ClearEditText extends AppCompatEditText implements View.OnTouchListener,View.OnFocusChangeListener {
    @BindDrawable(R.drawable.ic_x1)
    Drawable btnClear;

    private static final String TAG=ClearEditText.class.getSimpleName();

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        ButterKnife.bind(this);

        btnClear.setBounds(0,0,btnClear.getIntrinsicWidth(),btnClear.getIntrinsicHeight());

        setClearBtnVisible(false);

        /* show btnclear up to length of text */

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // when changed text, check the length of text and show
                if(isFocused()){
                    setClearBtnVisible(charSequence.length()>0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
    }

    /* show btnclear while focusing on edittext */

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            setClearBtnVisible(getText().length()>0);
        } else {
            setClearBtnVisible(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /* clear text when touching btnclear */

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x=(int)motionEvent.getX();
        if(btnClear.isVisible()&&x>getWidth()-getPaddingRight()-btnClear.getIntrinsicWidth()){
            if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                setError(null);
                setText(null);
            }
            return true;
        }
        return false;
    }

    private void setClearBtnVisible(boolean flag) {
        btnClear.setVisible(flag, false);
        setCompoundDrawables(null, null, flag ? btnClear : null, null); // set btnclear on the right side of edittext
    }
}
