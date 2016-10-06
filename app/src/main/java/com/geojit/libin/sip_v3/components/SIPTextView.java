package com.geojit.libin.sip_v3.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.geojit.libin.sip_v3.R;


/**
 * Created by libin on 15/07/16.
 */
public class SIPTextView extends TextView {
    public SIPTextView(Context context) {
        super(context);

        init(context, null);
    }

    public SIPTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public SIPTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SIPTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SIPTextView, 0, 0);

            try {
                int font = attributes.getInteger(R.styleable.SIPTextView_font, 0);

                Typeface type;
                switch (font) {
                    case 0:
                        type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans-light.ttf");
                        this.setTypeface(type);

                        break;

                    case 1:
                        type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans.ttf");
                        this.setTypeface(type);

                        break;

                    case 2:
                        type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans-bold.ttf");
                        this.setTypeface(type);

                        break;

                    case 3:
                        type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/BNPP Sans Cond ipad (v3).otf");
                        this.setTypeface(type);

                        break;

                    default:
                        type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans.ttf");
                        this.setTypeface(type);
                }


            } finally {
                attributes.recycle();
            }

        } else {
            Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans-light.ttf");
            this.setTypeface(type);
        }

    }
}
