package jmscapplications.com.ledscrollindisplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import jmscapplications.com.ledscrollindisplay.engine.LedParameters;
import jmscapplications.com.ledscrollindisplay.engine.LedThreadPreview;
import jmscapplications.com.ledscrollindisplay.share.AcceptInviteActivity;

public class PreviewActivity extends AcceptInviteActivity implements Callback {
    private SurfaceHolder holder = null;
    private LedThreadPreview ledThread = null;
    private LedParameters param = null;
    private boolean show = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showPreview(boolean show) {
        this.show = show;
    }

    public void onBackPressed() {
        if (this.ledThread != null) {
            this.ledThread.pause();
        }
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
        if (this.ledThread != null) {
            this.ledThread.play();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        if (this.param != null) {
            this.ledThread = new LedThreadPreview(holder, getApplicationContext(), this.param);
        }
    }

    public synchronized void updatePreview(LedParameters parameters) {
        this.param = parameters.clone();
        if (this.show && this.ledThread != null) {
            if (this.ledThread.isAlive()) {
                this.ledThread.stopThread();
            }
            if (this.holder != null) {
                this.ledThread = new LedThreadPreview(this.holder, getApplicationContext(), parameters);
            }
        }
    }

    public void init(SurfaceView view) {
        view.getHolder().addCallback(this);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.ledThread.stopThread();
    }
}
