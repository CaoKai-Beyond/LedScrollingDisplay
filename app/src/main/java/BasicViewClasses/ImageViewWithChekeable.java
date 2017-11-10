package BasicViewClasses;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

public abstract class ImageViewWithChekeable extends ImageView implements Checkable {
    public ImageViewWithChekeable(Context context) {
        super(context);
    }

    public ImageViewWithChekeable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewWithChekeable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ImageViewWithChekeable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setChecked(boolean checked) {
    }

    public boolean isChecked() {
        return false;
    }

    public void toggle() {
    }
}
