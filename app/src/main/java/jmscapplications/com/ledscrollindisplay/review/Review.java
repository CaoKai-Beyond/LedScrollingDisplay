package jmscapplications.com.ledscrollindisplay.review;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.gson.Gson;

public class Review {
    private final int frequency;
    private final int minSession;
    private int numberOfSessions = 0;
    private final String url;

    public Review(String url, int minSesion, int frequency) {
        this.url = url;
        this.minSession = minSesion;
        this.frequency = frequency;
    }

    public String getUrl() {
        return this.url;
    }

    public void onCreate(Activity context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        int sessionNumber = sharedPreferences.getInt("sessions", 0);
        boolean isPossibleToShow = sharedPreferences.getBoolean("show", true);
        long lastTimeShowed = sharedPreferences.getLong("last_time_showed", 0);
        if (sessionNumber >= this.minSession && isPossibleToShow && System.currentTimeMillis() > (((long) this.frequency) * 86400000) + lastTimeShowed) {
            showRateMessage(context);
        }
        sharedPreferences.edit().putInt("sessions", sessionNumber + 1).apply();
    }

    private void showRateMessage(Activity context) {
        context.startActivity(new Intent(context, ReviewActivityStep1.class).putExtra("review", new Gson().toJson((Object) this)));
    }
}
