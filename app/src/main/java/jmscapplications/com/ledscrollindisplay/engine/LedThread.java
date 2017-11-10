package jmscapplications.com.ledscrollindisplay.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.animation.SpringForce;
import android.support.v4.internal.view.SupportMenu;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LedThread extends Thread {
    private static final float PREDEFINE_HEIGHT = 100.0f;
    private final int CHARACTER_HEIGHT_RESOLUTION = 30;
    private int OUTPUT_SCREEN_WIDHT_RESOLUTION;
    private int TEXT_WIDHT_RESOLUTION;
    private Paint auxiliarPaint;
    private ArrayList<Integer[]> backgrounColorFromImage;
    private Integer[] bck;
    private boolean blink = false;
    private int countBlink = 0;
    private final Context ctx;
    private float fps = 24.0f;
    private int index;
    private int[][] intTextMatrix;
    private long lasTime = 0;
    private long lasTimeBlink = 0;
    private ArrayList<int[][]> ledPosition;
    private StaticLayout lsLayout;
    private ArrayList<int[]> outputTextMatrix;
    private final Paint paintLed;
    public final LedParameters parameters;
    private boolean playing;
    private int radius;
    private int surfaceHeight;
    private final SurfaceHolder surfaceHolder;
    private int surfaceWidth;
    private TextPaint textPaint;
    private boolean working;

    public enum Type {
        DIGITAL,
        ROBOTO,
        ENTER_GRIP,
        DEFAULT_CHARACTER,
        LCD_SOLID,
        PREZIDENT,
        OPIFICIO
    }

    public LedThread(SurfaceHolder holder, Context ctx, LedParameters parameters) {
        this.parameters = parameters.clone();
        this.playing = true;
        this.ctx = ctx;
        this.surfaceHolder = holder;
        this.outputTextMatrix = new ArrayList();
        this.paintLed = new Paint();
        Canvas auxCanvas = this.surfaceHolder.lockCanvas();
        if (auxCanvas != null) {
            this.surfaceWidth = auxCanvas.getWidth();
            this.surfaceHeight = auxCanvas.getHeight();
            auxCanvas.drawColor(SupportMenu.CATEGORY_MASK);
            this.surfaceHolder.unlockCanvasAndPost(auxCanvas);
            configureLedMessage();
        }
    }

    public void run() {
        this.working = true;
        this.index = this.OUTPUT_SCREEN_WIDHT_RESOLUTION;
        while (this.working) {
            if (((float) (System.currentTimeMillis() - this.lasTime)) > 1000.0f / this.fps) {
                this.lasTime = System.currentTimeMillis();
                if (this.playing) {
                    if (System.currentTimeMillis() - this.lasTimeBlink > 250 && this.parameters.isBlink()) {
                        this.lasTimeBlink = System.currentTimeMillis();
                        this.blink = true;
                    }
                    nextFrame();
                }
            }
        }
    }

    private synchronized void nextFrame() {
        try {
            Canvas canvas = this.surfaceHolder.lockCanvas();
            if (!(canvas == null || this.surfaceHolder == null)) {
                int[] column;
                int cx;
                int cy;
                float margen;
                int cy1;
                if (this.parameters.isDirectionStraight()) {
                    addColumnFront(this.parameters.getSpeed());
                } else {
                    addColumnBack(this.parameters.getSpeed());
                }
                canvas.drawColor(Color.rgb(25, 25, 25));
                this.paintLed.setColor(this.parameters.getBackgroundcolor());
                for (int b = 0; b < this.outputTextMatrix.size(); b++) {
                    column = (int[]) this.outputTextMatrix.get(b);
                    if (this.parameters.isBckgroundImageEnable()) {
                        this.bck = (Integer[]) this.backgrounColorFromImage.get(b);
                    }
                    for (int r = 0; r < column.length; r++) {
                        if (this.parameters.isBckgroundImageEnable()) {
                            this.paintLed.setColor(this.bck[r].intValue());
                        }
                        cx = ((int[][]) this.ledPosition.get(b))[0][r];
                        cy = ((int[][]) this.ledPosition.get(b))[1][r];
                        if (this.parameters.getShape() == 2) {
                            canvas.drawCircle((float) cx, (float) cy, (float) this.radius, this.paintLed);
                        } else {
                            margen = ((((float) this.surfaceWidth) / ((float) this.outputTextMatrix.size())) / 2.0f) * 0.8f;
                            cy1 = (int) (((float) cy) - margen);
                            canvas.drawRect((float) ((int) (((float) cx) - margen)), (float) cy1, (float) ((int) (((float) cx) + margen)), (float) ((int) (((float) cy) + margen)), this.paintLed);
                        }
                    }
                }
                this.paintLed.setColor(this.parameters.getTextcolor());
                for (int rawIndex = 0; rawIndex < this.outputTextMatrix.size(); rawIndex++) {
                    column = (int[]) this.outputTextMatrix.get(rawIndex);
                    int columIndex = 0;
                    while (columIndex < column.length) {
                        cx = ((int[][]) this.ledPosition.get(rawIndex))[0][columIndex];
                        cy = ((int[][]) this.ledPosition.get(rawIndex))[1][columIndex];
                        this.paintLed.setColor(((int[]) this.outputTextMatrix.get(rawIndex))[columIndex]);
                        if (!(this.blink || column[columIndex] == 0)) {
                            if (this.parameters.getShape() == 2) {
                                canvas.drawCircle((float) cx, (float) cy, (float) this.radius, this.paintLed);
                            } else {
                                margen = ((((float) this.surfaceWidth) / ((float) this.outputTextMatrix.size())) / 2.0f) * 0.8f;
                                cy1 = (int) (((float) cy) - margen);
                                canvas.drawRect((float) ((int) (((float) cx) - margen)), (float) cy1, (float) ((int) (((float) cx) + margen)), (float) ((int) (((float) cy) + margen)), this.paintLed);
                            }
                        }
                        columIndex++;
                    }
                }
                if (this.blink) {
                    this.countBlink++;
                    if (this.countBlink >= 3) {
                        this.countBlink = 0;
                        this.blink = false;
                    }
                }
                if (!(canvas == null || this.surfaceHolder == null)) {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        } catch (Exception e) {
        }
    }

    private void addColumnFront(int speed) {
        for (int i = 0; i < speed; i++) {
            this.outputTextMatrix.remove(0);
            addColumm(true);
        }
    }

    private void addColumnBack(int speed) {
        for (int i = 0; i < speed; i++) {
            this.outputTextMatrix.remove(this.outputTextMatrix.size() - 1);
            addColumm(false);
        }
    }

    private void addColumm(boolean addFront) {
        int[] auxColumn = new int[30];
        for (int j = 0; j < 30; j++) {
            if (this.intTextMatrix[this.index][j] != 0) {
                auxColumn[j] = this.intTextMatrix[this.index][j];
            } else {
                auxColumn[j] = 0;
            }
        }
        if (addFront) {
            this.outputTextMatrix.add(auxColumn);
        } else {
            this.outputTextMatrix.add(0, auxColumn);
        }
        if (this.index < this.TEXT_WIDHT_RESOLUTION - 1) {
            this.index++;
        } else {
            this.index = 0;
        }
    }

    private void configureLedMessage() {
        createTextPaint();
        this.intTextMatrix = getTextMatrix(this.parameters.getMessage());
        this.radius = (int) (((((float) this.surfaceHeight) / 30.0f) / 2.0f) * 0.95f);
        if (this.parameters.isDirectionStraight()) {
            if (this.parameters.isMirror()) {
                invertMatrix();
            }
        } else if (!this.parameters.isMirror()) {
            invertMatrix();
        }
        fillTextScreen();
        updateLedLocation();
        if (this.parameters.isBckgroundImageEnable()) {
            getBackgroundColorFromImage(this.ctx);
        }
        start();
    }

    private int[][] invertMatrix() {
        int height = this.intTextMatrix[0].length;
        int width = this.intTextMatrix.length;
        int[][] out = (int[][]) Array.newInstance(Integer.TYPE, new int[]{width, height});
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                out[i][j] = this.intTextMatrix[(width - i) - 1][j];
            }
        }
        for (int k = 0; k < width; k++) {
            for (int m = 0; m < height; m++) {
                this.intTextMatrix[k][m] = out[k][m];
            }
        }
        this.intTextMatrix = (int[][]) null;
        this.intTextMatrix = out;
        return out;
    }

    private void updateLedLocation() {
        ArrayList<int[][]> location = new ArrayList();
        for (int i = 0; i < this.outputTextMatrix.size(); i++) {
            int[] column = (int[]) this.outputTextMatrix.get(i);
            int squareY = (int) (((float) this.surfaceHeight) / ((float) column.length));
            int squareX = (int) (((float) this.surfaceWidth) / ((float) this.outputTextMatrix.size()));
            int cx = (int) ((((float) squareX) / 2.0f) + ((float) (squareX * i)));
            int[][] aux = (int[][]) Array.newInstance(Integer.TYPE, new int[]{2, column.length});
            for (int j = 0; j < column.length; j++) {
                int cy = (int) ((((float) squareY) / 2.0f) + ((float) (squareY * j)));
                aux[0][j] = cx;
                aux[1][j] = cy;
            }
            location.add(aux);
        }
        this.ledPosition = location;
    }

    private void fillTextScreen() {
        for (int i = 0; i < this.OUTPUT_SCREEN_WIDHT_RESOLUTION; i++) {
            int[] auxColumn = new int[30];
            for (int j = 0; j < 30; j++) {
                if (this.intTextMatrix[i][j] != 0) {
                    auxColumn[j] = this.intTextMatrix[i][j];
                } else {
                    auxColumn[j] = 0;
                }
            }
            this.outputTextMatrix.add(auxColumn);
        }
    }

    private void createTextPaint() {
        this.textPaint = new TextPaint();
        this.textPaint.setColor(this.parameters.getTextcolor());
        this.textPaint.setTextAlign(Align.CENTER);
        if (this.parameters.isSmall()) {
            this.textPaint.setTextSize(SpringForce.STIFFNESS_VERY_LOW);
        } else {
            this.textPaint.setTextSize(80.0f);
        }
        this.auxiliarPaint = new Paint();
        this.auxiliarPaint.setColor(SupportMenu.CATEGORY_MASK);
        switch (this.parameters.getTextType()) {
            case 0:
                this.textPaint.setTypeface(Typeface.DEFAULT);
                return;
            case 1:
                this.textPaint.setTypeface(Typeface.createFromAsset(this.ctx.getAssets(), "fonts/Roboto-Black.ttf"));
                return;
            case 2:
                this.textPaint.setTypeface(Typeface.createFromAsset(this.ctx.getAssets(), "fonts/digital.ttf"));
                return;
            case 3:
                this.textPaint.setTypeface(Typeface.createFromAsset(this.ctx.getAssets(), "fonts/enterthegrip.ttf"));
                return;
            case 4:
                this.textPaint.setTypeface(Typeface.createFromAsset(this.ctx.getAssets(), "fonts/Prezident.ttf"));
                return;
            case 5:
                this.textPaint.setTypeface(Typeface.createFromAsset(this.ctx.getAssets(), "fonts/LCD_Solid.ttf"));
                return;
            case 6:
                this.textPaint.setTypeface(Typeface.createFromAsset(this.ctx.getAssets(), "fonts/Opificio.ttf"));
                return;
            default:
                return;
        }
    }

    private int[][] getTextMatrix(String text) {
        Rect r = new Rect();
        this.textPaint.getTextBounds(text, 0, text.length(), r);
        float factorScreenSize = PREDEFINE_HEIGHT / ((float) this.surfaceHeight);
        int w = (int) (((float) r.width()) + ((((float) this.surfaceWidth) * 2.0f) * factorScreenSize));
        float posX = ((float) w) / 2.0f;
        float initialPosyWithoutPG = SpringForce.STIFFNESS_VERY_LOW + (((float) (r.height() - r.bottom)) / 2.0f);
        float spaceNeededForPG = ((float) r.bottom) - (PREDEFINE_HEIGHT - initialPosyWithoutPG);
        float posY = initialPosyWithoutPG;
        if (spaceNeededForPG > 0.0f) {
            posY = initialPosyWithoutPG - spaceNeededForPG;
        }
        this.TEXT_WIDHT_RESOLUTION = (int) (30.0f * (((float) w) / PREDEFINE_HEIGHT));
        this.OUTPUT_SCREEN_WIDHT_RESOLUTION = (int) ((30.0f * (((float) this.surfaceWidth) * factorScreenSize)) / PREDEFINE_HEIGHT);
        int[][] outputTextMatrix = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.TEXT_WIDHT_RESOLUTION, 30});
        int xSearchDistance = (int) (((float) w) / ((float) this.TEXT_WIDHT_RESOLUTION));
        int ySearchDistance = (int) (((float) 100) / 30.0f);
        Bitmap bmp = Bitmap.createBitmap(w, 100, Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(0);
        canvas.drawText(text, posX, posY, this.textPaint);
        for (int i = 0; i < this.TEXT_WIDHT_RESOLUTION; i++) {
            for (int j = 0; j < 30; j++) {
                outputTextMatrix[i][j] = bmp.getPixel((int) ((((float) xSearchDistance) / 2.0f) + ((float) (i * xSearchDistance))), (int) ((((float) ySearchDistance) / 2.0f) + ((float) (j * ySearchDistance))));
            }
        }
        bmp.recycle();
        return outputTextMatrix;
    }

    private void getBackgroundColorFromImage(Context ctx) {
        this.backgrounColorFromImage = new ArrayList();
        Bitmap bm = BitmapFactory.decodeResource(ctx.getResources(), this.parameters.getImageFromPosition());
        if (bm == null) {
//            FirebaseCrash.report(new Exception("Exception Fail creating bitmap with " + this.parameters.getImagePosition()));
//            FirebaseCrash.log("Fail creating bitmap with " + this.parameters.getImagePosition());
        }
        float hz = ((float) bm.getHeight()) / 30.0f;
        float wz = ((float) bm.getWidth()) / ((float) this.OUTPUT_SCREEN_WIDHT_RESOLUTION);
        for (int i = 0; i < this.OUTPUT_SCREEN_WIDHT_RESOLUTION; i++) {
            Integer[] colum = new Integer[30];
            for (int j = 0; j < 30; j++) {
                colum[j] = bm.getPixel((int) ((wz / 2.0f) + (((float) i) * wz)), (int) ((hz / 2.0f) + (((float) j) * hz)));
            }
            this.backgrounColorFromImage.add(colum);
        }
    }

    public void stopThread() {
        this.playing = false;
        this.working = false;
        boolean retry = true;
        while (retry) {
            try {
                join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void play() {
        this.playing = true;
    }

    public void pause() {
        this.playing = false;
    }
}
