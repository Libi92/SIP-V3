package com.geojit.libin.sip_v3.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.geojit.libin.sip_v3.R;

/**
 * Created by 10945 on 7/28/2016.
 */
public class RupeesTextView extends LinearLayout {
    private SIPTextView textViewIcon;
    private SIPTextView textViewAmount;

    public RupeesTextView(Context context) {
        super(context);

        init(context);
    }

    public RupeesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public RupeesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RupeesTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    private void init(Context context) {
        this.setOrientation(HORIZONTAL);
        this.textViewIcon = new SIPTextView(context);
        this.textViewAmount = new SIPTextView(context);

        textViewIcon.setText(getResources().getString(R.string.rs));
        textViewAmount.setText(getResources().getString(R.string.rs23));

        this.addView(textViewIcon);
        this.addView(textViewAmount);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);

        if (attrs != null) {
            TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RupeesTextView, 0, 0);

            int defaultColor = getResources().getColor(android.R.color.black);
            float defaultTextSize = getResources().getDimension(R.dimen.defaultTextSize);
            float defaultTextDistance = getResources().getDimension(R.dimen.defaultTextDistance);


            String text = attributes.getString(R.styleable.RupeesTextView_text);
            int textColor = attributes.getColor(R.styleable.RupeesTextView_textColor, defaultColor);
            int iconColor = attributes.getColor(R.styleable.RupeesTextView_iconColor, defaultColor);

            float textSize = attributes.getDimension(R.styleable.RupeesTextView_textSize, defaultTextSize);
            float textDistance = attributes.getDimension(R.styleable.RupeesTextView_textDistance, defaultTextDistance);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginStart((int) textDistance);

            if (text != null) {
                textViewAmount.setText(text);
            }
            textViewAmount.setTextColor(textColor);
            textViewAmount.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textViewAmount.setLayoutParams(params);

            textViewIcon.setTextColor(iconColor);
            textViewIcon.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    public String getText() {
        return this.textViewAmount.getText().toString();
    }

    public void setText(String value) {
        this.textViewAmount.setText(value);
    }

    ;
}
