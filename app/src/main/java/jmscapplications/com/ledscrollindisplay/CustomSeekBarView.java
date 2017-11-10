package jmscapplications.com.ledscrollindisplay;

/**
 * Created by Z on 2017/11/10.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomSeekBarView extends View {
    private int color = -1;
    private float currentValue = 2.0f;
    private long delay = 0;
    private int lineWidth = 3;
    private float maxValue = 5.0f;
    private Paint paint = new Paint();
    private Paint paintProgress = new Paint();
    private int pointRadio = 9;
    private CustomSeekBarListener seekBarListener;
    private int selectedPointRadio = 16;
    private boolean snapping = false;
    private int snappingStep = 1;
    private int unselectedColor = -1;
    private int width;

    public interface CustomSeekBarListener {
        void actionCustomSeekBar(int i);

        void endEditingCustomSeekBar();

        void onCustomSeekBarChangedListener(float f);

        void onCustomSeekBarProgressListener(float f);

        void startEditingCustomSeekBar();
    }

    public CustomSeekBarView(Context context) {
        super(context);
        init();
    }

    public CustomSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public CustomSeekBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.paint.setAntiAlias(true);
        this.paint.setColor(getColor());
        this.paint.setStrokeWidth((float) getLineWidth());
        this.paintProgress.setAntiAlias(true);
        this.paintProgress.setColor(getColor());
        this.paintProgress.setStrokeWidth((float) (getLineWidth() * 3));
    }

    private void update() {
        this.paint.setColor(this.unselectedColor);
        this.paint.setStrokeWidth((float) getLineWidth());
        this.paintProgress.setColor(getColor());
        this.paintProgress.setStrokeWidth((float) (getLineWidth() * 3));
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.width = canvas.getWidth();
        int height = canvas.getHeight();
        float x1 = (float) getSelectedPointRadio();
        float y1 = (float) (height / 2);
        float y2 = (float) (height / 2);
        Canvas canvas2 = canvas;
        canvas2.drawLine(x1, y1, (float) (this.width - getSelectedPointRadio()), y2, this.paint);
        float separation = (((float) (this.width - (getSelectedPointRadio() * 2))) - (((float) (getPointRadio() * 2)) * (getMaxValue() - 1.0f))) / (getMaxValue() - 1.0f);
        for (int i = 0; ((float) i) <= getMaxValue() - 1.0f; i++) {
            float x = (((float) i) * separation) + ((float) (((getPointRadio() * 2) * i) + getSelectedPointRadio()));
            float y = (float) (height / 2);
            if (((float) i) < getCurrentValue()) {
                canvas.drawCircle(x, y, (float) getPointRadio(), this.paintProgress);
            } else {
                canvas.drawCircle(x, y, (float) getPointRadio(), this.paint);
            }
        }
        float xs1 = ((getCurrentValue() / (getMaxValue() - 1.0f)) * ((float) (this.width - (getSelectedPointRadio() * 2)))) + ((float) getSelectedPointRadio());
        canvas.drawCircle(xs1, (float) (height / 2), (float) getSelectedPointRadio(), this.paintProgress);
        canvas.drawLine(x1, y1, xs1, y2, this.paintProgress);
    }

    public boolean isSnapping() {
        return this.snapping;
    }

    public int getSnappingStep() {
        return this.snappingStep;
    }

    public void setSnapping(boolean snapping, int snappingStep) {
        this.snapping = snapping;
        this.snappingStep = snappingStep;
    }

    public int getColor() {
        return this.color;
    }

    public float getMaxValue() {
        return this.maxValue;
    }

    public float getCurrentValue() {
        return this.currentValue;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void setColor(int color) {
        this.color = color;
        update();
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        update();
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
        update();
    }

    public void setUnselectedColor(int color) {
        this.unselectedColor = color;
        update();
    }

    public int getLineWidth() {
        return this.lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        update();
    }

    public int getPointRadio() {
        return this.pointRadio;
    }

    public void setPointRadio(int pointRadio) {
        this.pointRadio = pointRadio;
        update();
    }

    public int getSelectedPointRadio() {
        return this.selectedPointRadio;
    }

    public void setSelectedPointRadio(int selectedPointRadio) {
        this.selectedPointRadio = selectedPointRadio;
        update();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getEventTime() - this.delay > 20) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (x <= this.width && x >= 0) {
                setCurrentValue((((float) x) / ((float) this.width)) * (getMaxValue() - 1.0f));
            } else if (x < 0) {
                setCurrentValue(0.0f);
            } else if (x > this.width) {
                setCurrentValue(getMaxValue() - 1.0f);
            }
            invalidate();
            sendCurrentValue();
            this.delay = event.getEventTime();
        }
        if (event.getAction() == 1 || event.getAction() == 3) {
            sendLastValue();
            endEditingValue();
        }
        if (event.getAction() == 0) {
            startEditingValue();
        }
        sendActionValue(event.getAction());
        return true;
    }

    public void setOnCustomSeekBarListener(CustomSeekBarListener seekBarListener) {
        this.seekBarListener = seekBarListener;
    }

    private void sendLastValue() {
        if (this.seekBarListener == null) {
            return;
        }
        if (this.snapping) {
            setCurrentValue(((float) Math.round(getCurrentValue() / ((float) getSnappingStep()))) * ((float) getSnappingStep()));
            this.seekBarListener.onCustomSeekBarChangedListener(getCurrentValue());
            return;
        }
        this.seekBarListener.onCustomSeekBarChangedListener(getCurrentValue());
    }

    private void sendCurrentValue() {
        if (this.seekBarListener != null) {
            this.seekBarListener.onCustomSeekBarProgressListener(getCurrentValue());
        }
    }

    private void startEditingValue() {
        if (this.seekBarListener != null) {
            this.seekBarListener.startEditingCustomSeekBar();
        }
    }

    private void sendActionValue(int action) {
        if (this.seekBarListener != null) {
            this.seekBarListener.actionCustomSeekBar(action);
        }
    }

    private void endEditingValue() {
        if (this.seekBarListener != null) {
            this.seekBarListener.endEditingCustomSeekBar();
        }
    }
}
