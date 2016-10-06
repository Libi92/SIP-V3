package com.geojit.libin.sip_v3.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.geojit.libin.sip_v3.R;

/**
 * Created by 10945 on 8/5/2016.
 */
public class SIPIconEditText extends LinearLayout {
    private ImageView imageViewLeft;
    private EditText editText;

    public SIPIconEditText(Context context) {
        super(context);

        init(context);
    }

    public SIPIconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);

        setAttributes(context, attrs);
    }

    public SIPIconEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
        setAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SIPIconEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
        setAttributes(context, attrs);
    }

    private void init(Context context) {
        this.setGravity(Gravity.CENTER_VERTICAL);
        imageViewLeft = new ImageView(context);
        editText = new EditText(context);
        editText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        editText.setTextSize(20);
        editText.setTextColor(getResources().getColor(R.color.gray2X));
        editText.setHintTextColor(getResources().getColor(R.color.baseGray));


        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/bnpp/bnpp-sans.ttf");
        editText.setTypeface(type);
        LayoutParams editTextParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editTextParams.setMargins(25, 0, 0, 0);
        editText.setLayoutParams(editTextParams);

        LayoutParams imageViewParams = new LayoutParams(60, 60);
        imageViewLeft.setLayoutParams(imageViewParams);

        this.setOrientation(HORIZONTAL);
        this.addView(imageViewLeft);
        this.addView(editText);
        this.setBackgroundColor(getResources().getColor(android.R.color.white));
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 55));
        this.setPadding(25, 25, 25, 15);


    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SIPIconEditText, 0, 0);

        try {
            String hint = attributes.getString(R.styleable.SIPIconEditText_hint);
            String text = attributes.getString(R.styleable.SIPIconEditText_textString);
            Drawable image = attributes.getDrawable(R.styleable.SIPIconEditText_drawableLeft);

            if (hint != null) {
                editText.setHint(hint);
            }

            if (text != null) {
                editText.setText(text);
            }

            if (image != null) {
                imageViewLeft.setImageDrawable(image);
            }
        } finally {
            attributes.recycle();
        }
    }
}
