package com.geojit.libin.sip_v3.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by 10945 on 7/28/2016.
 */
public class SIPEditText extends EditText {
    private KeyboardEventListener callback;

    public SIPEditText(Context context) {
        super(context);
        init(context);
    }

    public SIPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SIPEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SIPEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void setKeyboardEventListener(KeyboardEventListener callback) {
        this.callback = callback;
    }

    private void init(Context context) {
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans-light.ttf");
        this.setTypeface(type);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            if (callback != null) {
                callback.onKeyboardClosed();
            }

        }
        return super.dispatchKeyEvent(event);
    }

    public interface KeyboardEventListener {
        void onKeyboardClosed();
    }
}
