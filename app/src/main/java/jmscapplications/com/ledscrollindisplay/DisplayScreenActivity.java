package jmscapplications.com.ledscrollindisplay;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.ckmobile.led.R;
import com.google.gson.Gson;
import jmscapplications.com.ledscrollindisplay.analityc.AnalyticMethods;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomTextView;
import jmscapplications.com.ledscrollindisplay.engine.LedParameters;
import jmscapplications.com.ledscrollindisplay.engine.LedThread;

public class DisplayScreenActivity extends Activity implements Callback, OnClickListener {
    private static final int REQUEST_INVITE = 34;
    private LinearLayout buttonsLayout;
    private SurfaceView display;
    private LedThread ledThread;
    private ImageView menuButton;
    private LinearLayout menuLayout;
    private LedParameters parameters;
    @BindView(R.id.paused)
    CustomTextView paused;
    private ImageView playButton;
    private SharedPreferences settins;
    private boolean visibleButtons;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(128);
        this.parameters = (LedParameters) new Gson().fromJson(getIntent().getStringExtra(LedParameters.KEY), LedParameters.class);
        setContentView((int) R.layout.activity_display);
        ButterKnife.bind((Activity) this);
        this.buttonsLayout = (LinearLayout) findViewById(R.id.ButtonsLayout);
        this.visibleButtons = false;
        this.display = (SurfaceView) findViewById(R.id.display);
        this.display.getHolder().addCallback(this);
        this.menuButton = (ImageView) findViewById(R.id.BvMenu);
        this.playButton = (ImageView) findViewById(R.id.BvPlay);
        this.menuButton.setOnClickListener(this);
        this.playButton.setOnClickListener(this);
        this.buttonsLayout.setOnClickListener(this);
        this.menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
        this.menuLayout.setBackgroundDrawable(new RoundRectangleDrawable(Color.argb(50, 0, 0, 0), 8));
        hideBottomUIMenu();
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    public void onBackPressed() {
        if (this.ledThread != null) {
            this.ledThread.pause();
            this.paused.setVisibility(View.GONE);
        }
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
        if (this.ledThread != null) {
            this.ledThread.play();
            this.paused.setVisibility(View.GONE);
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.ledThread = new LedThread(holder, getApplicationContext(), this.parameters);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.ledThread.stopThread();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.BvMenu) {
            hideButtons();
            startActivity(new Intent(this, SettingActivity.class));
            finish();
        }
        if (v.getId() == R.id.BvPlay) {
            hideButtons();
            this.ledThread.play();
            this.paused.setVisibility(View.GONE);
        }
        if (v.getId() != R.id.ButtonsLayout) {
            return;
        }
        if (this.visibleButtons) {
            hideButtons();
            this.ledThread.play();
            this.paused.setVisibility(View.GONE);
            return;
        }
        this.menuLayout.setVisibility(View.VISIBLE);
        this.visibleButtons = true;
        this.ledThread.pause();
        this.paused.setVisibility(View.VISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                DisplayScreenActivity.this.hideButtons();
            }
        }, 1500);
    }

    private void hideButtons() {
        this.visibleButtons = false;
        this.menuLayout.setVisibility(View.GONE);
    }
}
