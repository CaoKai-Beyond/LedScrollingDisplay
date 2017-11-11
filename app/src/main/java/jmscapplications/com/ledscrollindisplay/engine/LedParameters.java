package jmscapplications.com.ledscrollindisplay.engine;

import android.net.Uri;
import android.util.Base64;

import com.ckmobile.led.R;

import java.io.UnsupportedEncodingException;

public class LedParameters implements Cloneable {



    public static final String KEY="value";

    public static final int CIRCULAR_SHAPE = 2;
    public static final int SQUARED_SHAPE = 1;
    int bckColor;
    boolean bckgroundImageEnable;
    boolean blink;
    private int imagePosition;
    String message;
    boolean mirror;
    int shape;
    private boolean small;
    int speed;
    boolean straightDirection;
    int textColor;
    private int textType;
    boolean useImageBck;
    private String uri;


    public LedParameters clone() {
        try {
            return (LedParameters) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public void setUri(String uri){
        this.uri=uri;
    }
    public String getUri(){
        return uri;
    }

    public boolean isBckgroundImageEnable() {
        return this.bckgroundImageEnable;
    }

    public void setBckgroundImageEnable(boolean bckgroundImageEnable) {
        this.bckgroundImageEnable = bckgroundImageEnable;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBckColor(int bckColor) {
        this.bckColor = bckColor;
    }

    public boolean isBlink() {
        return this.blink;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    public boolean isDirectionStraight() {
        return this.straightDirection;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStraightDirection(boolean straightDirection) {
        this.straightDirection = straightDirection;
    }

    public int getBackgroundcolor() {
        return this.bckColor;
    }

    public int getShape() {
        return this.shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getTextcolor() {
        return this.textColor;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMirror() {
        return this.mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    public int getImagePosition() {
        return this.imagePosition;
    }

    public void setImagePosition(int position) {
        this.imagePosition = position;
    }

    public int getImageFromPosition() {
        switch (this.imagePosition) {
            case 0:
                return R.drawable.b1r;
            case 1:
                return R.drawable.b2r;
            case 2:
                return R.drawable.b3r;
            case 3:
                return R.drawable.b4r;
            case 4:
                return R.drawable.b5r;
            case 5:
                return R.drawable.b6r;
            case 6:
                return R.drawable.b7r;
            case 7:
                return R.drawable.b8r;
            case 8:
                return R.drawable.b9r;
            case 9:
                return R.drawable.b10r;
            case 10:
                return R.drawable.b11r;
            case 11:
                return R.drawable.b12r;
            case 12:
                return R.drawable.b13r;
            case 13:
                return R.drawable.b14r;
            case 14:
                return R.drawable.b15r;
            case 15:
                return R.drawable.b16r;
            case 16:
                return R.drawable.b17r;
            case 17:
                return R.drawable.b18r;
            case 18:
                return R.drawable.b19r;
            case 19:
                return R.drawable.b20r;
            case 20:
                return R.drawable.b21r;
            case 21:
                return R.drawable.b22r;
            case 22:
                return R.drawable.b23r;
            case 23:
                return R.drawable.b24r;
            case 24:
                return R.drawable.b25r;
            case 25:
                return R.drawable.b26r;
            case 26:
                return R.drawable.b27r;
            case 27:
                return R.drawable.b28r;
            case 28:
                return R.drawable.b29r;
            case 29:
                return R.drawable.b30r;
            case 30:
                return R.drawable.b31r;
            case 31:
                return R.drawable.b32r;
            case 32:
                return R.drawable.b33r;
            case 33:
                return R.drawable.b34r;
            case 34:
                return R.drawable.b35r;
            case 35:
                return R.drawable.b36r;
            case 36:
                return R.drawable.b37r;
            case 37:
                return R.drawable.b38r;
            case 38:
                return R.drawable.b39r;
            case 39:
                return R.drawable.b40r;
            case 40:
                return R.drawable.b41;
            default:
                return 0;
        }
    }

    public String getBase64Data() {
        String base64 = "";
        StringBuilder stringBuilder = new StringBuilder();
        if (this.bckgroundImageEnable) {
            stringBuilder.append("t_");
        } else {
            stringBuilder.append("f_");
        }
        stringBuilder.append(this.textColor);
        stringBuilder.append("_");
        stringBuilder.append(this.bckColor);
        stringBuilder.append("_");
        if (this.blink) {
            stringBuilder.append("t_");
        } else {
            stringBuilder.append("f_");
        }
        if (this.straightDirection) {
            stringBuilder.append("t_");
        } else {
            stringBuilder.append("f_");
        }
        stringBuilder.append(this.speed);
        stringBuilder.append("_");
        if (this.useImageBck) {
            stringBuilder.append("t_");
        } else {
            stringBuilder.append("f_");
        }
        stringBuilder.append(this.shape);
        stringBuilder.append("_");
        stringBuilder.append(this.message);
        stringBuilder.append("_");
        if (this.mirror) {
            stringBuilder.append("t_");
        } else {
            stringBuilder.append("f_");
        }
        stringBuilder.append(this.imagePosition);
        stringBuilder.append("_");
        byte[] data = new byte[0];
        try {
            base64 = Base64.encodeToString(stringBuilder.toString().getBytes("UTF-8"), 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public int getTextType() {
        return this.textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public boolean isSmall() {
        return this.small;
    }
}
