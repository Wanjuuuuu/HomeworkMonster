package com.example.wanjukim.homeworkmonster.utils;

import android.content.Context;

import androidx.appcompat.widget.AppCompatSpinner;

import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by Wanju Kim on 2018-02-12.
 */

public class EventListenSpinner extends AppCompatSpinner {

    public interface OnSpinnerEventsListener {
        void onSpinnerOpened(Spinner spinner);

        void onSpinnerClosed(Spinner spinner);
    }

    private OnSpinnerEventsListener listener;
    private boolean opened = false;

    public EventListenSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean performClick() {
        if (listener != null) {
            if (!opened) {
                opened = true;
                listener.onSpinnerOpened(this);
            } else {
                performClosedEvent();
            }
        }
        return super.performClick();
    }

    public void performClosedEvent() {
        opened = false;
//        if(listener!=null){
        listener.onSpinnerClosed(this);
//        }
    }

    public void setSpinnerEventListener(OnSpinnerEventsListener onSpinnerEventsListener) {
        listener = onSpinnerEventsListener;
    }

    public boolean isOpened() {
        return opened;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (isOpened() && hasFocus) {
            performClosedEvent();
        }
    }
}
