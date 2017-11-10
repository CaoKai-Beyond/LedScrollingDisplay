package BasicViewClasses;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

public abstract class ViewWithChekeable extends View implements Checkable {
    public ViewWithChekeable(Context context) {
        super(context);
    }

    public ViewWithChekeable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewWithChekeable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ViewWithChekeable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
