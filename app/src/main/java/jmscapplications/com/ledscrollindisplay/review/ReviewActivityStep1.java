package jmscapplications.com.ledscrollindisplay.review;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.ckmobile.led.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import jmscapplications.com.ledscrollindisplay.analityc.AnalyticMethods;

public class ReviewActivityStep1 extends Activity {
    private ArrayList<ImageButton> imageButtons;
    @BindView(R.id.rate1)
    ImageButton rate1;
    @BindView(R.id.rate2)
    ImageButton rate2;
    @BindView(R.id.rate3)
    ImageButton rate3;
    @BindView(R.id.rate4)
    ImageButton rate4;
    @BindView(R.id.rate5)
    ImageButton rate5;
    private String reviewJson;

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 4) {
            AnalyticMethods.registerEvent(this, AnalyticMethods.DISMISS_RATE);
            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
            sharedPreferences.edit().putLong("last_time_showed", System.currentTimeMillis()).apply();
            sharedPreferences.edit().putInt("sessions", 0).apply();
            finish();
        }
        return false;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(32, 32);
        window.setFlags(262144, 262144);
        setContentView((int) R.layout.activity_rate_step1);
        ButterKnife.bind((Activity) this);
        AnalyticMethods.registerEvent(this, AnalyticMethods.SHOW_RATE_VALE);
        this.reviewJson = getIntent().getStringExtra("review");
        this.imageButtons = new ArrayList();
        this.imageButtons.add(this.rate1);
        this.imageButtons.add(this.rate2);
        this.imageButtons.add(this.rate3);
        this.imageButtons.add(this.rate4);
        this.imageButtons.add(this.rate5);
    }

    @OnClick({R.id.rate1, R.id.rate2, R.id.rate3, R.id.rate4, R.id.rate5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rate1:
                updateView(1);
                return;
            case R.id.rate2:
                updateView(2);
                return;
            case R.id.rate3:
                updateView(3);
                return;
            case R.id.rate4:
                updateView(4);
                return;
            case R.id.rate5:
                updateView(5);
                return;
            default:
                return;
        }
    }

    private void updateView(final int position) {
        for (int i = 0; i < position; i++) {
            ((ImageButton) this.imageButtons.get(i)).setImageResource(R.drawable.start_full);
        }
        AnalyticMethods.registerRateEvent(this, AnalyticMethods.RATE_VALE, (long) position);
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                if (position < 4) {
                    ReviewActivityStep1.this.getSharedPreferences(ReviewActivityStep1.this.getPackageName(), 0).edit().putBoolean("show", false).apply();
                    ReviewActivityStep1.this.finish();
                    return;
                }
                AnalyticMethods.registerEvent(ReviewActivityStep1.this, AnalyticMethods.RATE_OVER_FOUR);
                ReviewActivityStep1.this.startActivity(new Intent(ReviewActivityStep1.this, ReviewActivityStep2.class).putExtra("review", ReviewActivityStep1.this.reviewJson));
                ReviewActivityStep1.this.finish();
            }
        }, 300);
    }
}
