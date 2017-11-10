package jmscapplications.com.ledscrollindisplay.engine;

import android.graphics.Color;
import android.support.v4.view.ViewCompat;

import com.ckmobile.led.R;

public class LedParametersBuilder {
    int bckColor = ViewCompat.MEASURED_STATE_MASK;
    boolean bckgroundImageEnable = false;
    boolean blink = false;
    private int imageID = R.drawable.b1r;
    String message = "LedScrolling 😎";
    boolean mirror = false;
    int shape = 2;
    boolean small = false;
    int speed = 3;
    boolean straightDirection = true;
    int textColor = Color.rgb(3, 251, 17);
    int typeFace = 0;
    boolean useImageBck = false;

    public LedParametersBuilder setBckgroundImageEnable(boolean bckgroundImageEnable) {
        this.bckgroundImageEnable = bckgroundImageEnable;
        return this;
    }

    public LedParametersBuilder setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public LedParametersBuilder setBckColor(int bckColor) {
        this.bckColor = bckColor;
        return this;
    }

    public LedParametersBuilder setBlink(boolean blink) {
        this.blink = blink;
        return this;
    }

    public LedParametersBuilder setStraightDirection(boolean straightDirection) {
        this.straightDirection = straightDirection;
        return this;
    }

    public LedParametersBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public LedParametersBuilder setUseImageBck(boolean useImageBck) {
        this.useImageBck = useImageBck;
        return this;
    }

    public LedParametersBuilder setShape(int shape) {
        this.shape = shape;
        return this;
    }

    public LedParametersBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public LedParametersBuilder setMirror(boolean mirror) {
        this.mirror = mirror;
        return this;
    }

    public LedParametersBuilder setImageID(int imageID) {
        this.imageID = imageID;
        return this;
    }

    public LedParametersBuilder setTypeFace(int typeFace) {
        this.typeFace = typeFace;
        return this;
    }

    public LedParametersBuilder setSmall(boolean vale) {
        this.small = vale;
        return this;
    }

    public LedParameters build() {
        LedParameters output = new LedParameters();
        output.setMessage(this.message);
        output.setStraightDirection(this.straightDirection);
        output.setMirror(this.mirror);
        output.setSpeed(this.speed);
        output.setBckColor(this.bckColor);
        output.setTextColor(this.textColor);
        output.setShape(this.shape);
        output.setBlink(this.blink);
        output.setImagePosition(this.imageID);
        output.setTextType(this.typeFace);
        output.setSmall(this.small);
        return output;
    }
}
