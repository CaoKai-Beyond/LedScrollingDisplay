package jmscapplications.com.ledscrollindisplay;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class RoundRectangleDrawable extends Drawable {
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 3;
    public static final int UP = 1;
    private final Paint borderPaint;
    private final Paint colorPaint = new Paint();
    private boolean customConners = false;
    private int radius;
    private int rounSize = 1;
    private final boolean stroke;

    public RoundRectangleDrawable(int color, int radius) {
        this.colorPaint.setColor(color);
        this.colorPaint.setAntiAlias(true);
        this.radius = radius;
        this.stroke = false;
        this.borderPaint = null;
    }

    public RoundRectangleDrawable(int color, int radius, int roundSize) {
        this.colorPaint.setColor(color);
        this.colorPaint.setAntiAlias(true);
        this.radius = radius;
        this.stroke = false;
        this.borderPaint = null;
        this.customConners = true;
        this.rounSize = roundSize;
    }

    public RoundRectangleDrawable(int color, int radius, int strokeWidth, int strokeColor) {
        this.colorPaint.setColor(color);
        this.colorPaint.setAntiAlias(true);
        this.borderPaint = new Paint();
        this.borderPaint.setStrokeWidth((float) strokeWidth);
        this.borderPaint.setStyle(Style.STROKE);
        this.borderPaint.setColor(strokeColor);
        this.borderPaint.setAntiAlias(true);
        this.radius = radius;
        this.stroke = true;
    }

    public void draw(Canvas canvas) {
        if (!this.customConners) {
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight()), (float) this.radius, (float) this.radius, this.colorPaint);
            if (this.stroke) {
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight()), (float) this.radius, (float) this.radius, this.borderPaint);
            }
        } else if (this.rounSize == 1) {
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight()), (float) this.radius, (float) this.radius, this.colorPaint);
            canvas.drawRect(0.0f, (float) (canvas.getHeight() / 2), (float) canvas.getWidth(), (float) canvas.getHeight(), this.colorPaint);
        } else if (this.rounSize == 2) {
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight()), (float) this.radius, (float) this.radius, this.colorPaint);
            canvas.drawRect(0.0f, 0.0f, (float) canvas.getWidth(), (float) (canvas.getHeight() / 2), this.colorPaint);
        } else if (this.rounSize == 4) {
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight()), (float) this.radius, (float) this.radius, this.colorPaint);
            canvas.drawRect((float) (canvas.getWidth() / 2), 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), this.colorPaint);
        } else if (this.rounSize == 3) {
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight()), (float) this.radius, (float) this.radius, this.colorPaint);
            canvas.drawRect(0.0f, 0.0f, (float) (canvas.getWidth() / 2), (float) canvas.getHeight(), this.colorPaint);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
