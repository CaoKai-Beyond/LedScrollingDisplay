package jmscapplications.com.ledscrollindisplay.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

import com.ckmobile.led.R;

public class CustomAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private int type;

    public enum Weight {
        THIN,
        LIGHT,
        REGULAR,
        MEDIUM,
        BOLD
    }

    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomTypeFace(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomTypeFace(context, attrs);
    }

    private void setCustomTypeFace(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypeFaceWeight, 0, 0);
        try {
            this.type = a.getInteger(R.styleable.TypeFaceWeight_Weight, 0);
            setTypeFace(this.type, context);
        } finally {
            a.recycle();
        }
    }

    public int getTypeFace() {
        return this.type;
    }

    public void setCustomTypeFace(Weight weight, Context context) {
        this.type = weight.ordinal();
        setTypeFace(this.type, context);
        invalidate();
        requestLayout();
    }

    public void setTypeFace(int type, Context context) {
        switch (type) {
            case 0:
                setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf"));
                return;
            case 1:
                setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
                return;
            case 2:
                setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
                return;
            case 3:
                setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));
                return;
            case 4:
                setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf"));
                return;
            default:
                return;
        }
    }
}
