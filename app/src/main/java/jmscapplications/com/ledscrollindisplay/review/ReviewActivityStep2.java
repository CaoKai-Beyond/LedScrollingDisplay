package jmscapplications.com.ledscrollindisplay.review;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.ckmobile.led.R;
import com.google.gson.Gson;
import jmscapplications.com.ledscrollindisplay.analityc.AnalyticMethods;

public class ReviewActivityStep2 extends AppCompatActivity {
    private Review review;
    private String reviewJson;
    private String url;

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 4) {
            AnalyticMethods.registerEvent(this, AnalyticMethods.DISMISS_COMMENT);
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
        setContentView((int) R.layout.activity_rate_step2);
        ButterKnife.bind((Activity) this);
        this.reviewJson = getIntent().getStringExtra("review");
        this.review = (Review) new Gson().fromJson(this.reviewJson, Review.class);
        this.url = this.review.getUrl();
    }

    @OnClick({R.id.rate, R.id.later, R.id.dont_ask})
    public void onViewClicked(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
        switch (view.getId()) {
            case R.id.rate:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.url)));
                sharedPreferences.edit().putBoolean("show", false).apply();
                AnalyticMethods.registerEvent(this, AnalyticMethods.RATE_GOOGLE_PLAY);
                break;
            case R.id.later:
                sharedPreferences.edit().putLong("last_time_showed", System.currentTimeMillis()).apply();
                sharedPreferences.edit().putInt("sessions", 0).apply();
                AnalyticMethods.registerEvent(this, AnalyticMethods.LATER);
                break;
            case R.id.dont_ask:
                sharedPreferences.edit().putBoolean("show", false).apply();
                AnalyticMethods.registerEvent(this, AnalyticMethods.DO_NOT_ASK_RATE);
                break;
        }
        finish();
    }
}
