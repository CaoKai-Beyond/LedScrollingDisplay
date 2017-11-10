package jmscapplications.com.ledscrollindisplay.share;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomActivityCenterTittle;
import org.json.JSONException;
import org.json.JSONObject;

public class AcceptInviteActivity extends CustomActivityCenterTittle {
    public void onStart() {
        super.onStart();
    }

    public void onMessageRedeemed(String value) {
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }
}
