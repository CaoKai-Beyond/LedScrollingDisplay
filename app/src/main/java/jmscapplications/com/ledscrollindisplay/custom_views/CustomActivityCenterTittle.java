package jmscapplications.com.ledscrollindisplay.custom_views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ckmobile.led.R;


public class CustomActivityCenterTittle extends AppCompatActivity implements OnGlobalLayoutListener {
    private ActionBar actionBar;
    private View activityRootView;
    private boolean detectKeyboardHidden;
    private int layoutId;
    private ViewTreeObserver treeObserver;

    public static float dpToPx(Context context, float valueInDp) {
        return TypedValue.applyDimension(1, valueInDp, context.getResources().getDisplayMetrics());
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.actionBar = getSupportActionBar();
        this.activityRootView = findViewById(R.id.general_layout);
    }

    public void enableDetectKeyboardHidden(int rootViewID) {
        this.detectKeyboardHidden = true;
        this.activityRootView = findViewById(rootViewID);
    }

    protected void onResume() {
        super.onResume();
        if (this.detectKeyboardHidden) {
            this.treeObserver = this.activityRootView.getViewTreeObserver();
            this.treeObserver.addOnGlobalLayoutListener(this);
        }
    }

    protected void onPause() {
        if (this.detectKeyboardHidden && this.treeObserver.isAlive()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                this.treeObserver.removeOnGlobalLayoutListener(this);
            }
        }
        super.onPause();
    }

    public void setActionBarParameters(String tittle, boolean isDisplayBackButtonEnable) {
       // this.actionBar.setDisplayOptions(16);
        this.actionBar.setElevation(8.0f);
        setDisplayBackButton(isDisplayBackButtonEnable);
        actionBar.setTitle(tittle);
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        if (isDisplayBackButtonEnable) {
//            LinearLayout customActionBarView = (LinearLayout) layoutInflater.inflate(R.layout.action_bar_with_back_button, null);
//            changeTittle(customActionBarView, tittle);
//            this.actionBar.setCustomView(customActionBarView);
//            return;
//        }
//        RelativeLayout customActionBarView = (RelativeLayout) layoutInflater.inflate(R.layout.action_bar, null);
//        changeTittle(customActionBarView, tittle);
//        this.actionBar.setCustomView(customActionBarView);
    }

    private void changeTittle(ViewGroup view, String tittle) {
        ((TextView) view.getChildAt(0)).setText(tittle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDisplayBackButton(boolean isBackButtonEnable) {
        this.actionBar.setHomeButtonEnabled(isBackButtonEnable);
        this.actionBar.setDisplayHomeAsUpEnabled(isBackButtonEnable);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.hardKeyboardHidden == 1) {
            onKeyBoardVisibleChange(true);
        } else if (newConfig.hardKeyboardHidden == 2) {
            onKeyBoardVisibleChange(false);
        }
    }

    public void onKeyBoardVisibleChange(boolean isVisible) {
    }

    public void onGlobalLayout() {
        if (((float) (this.activityRootView.getRootView().getHeight() - this.activityRootView.getHeight())) > dpToPx(this, SpringForce.STIFFNESS_LOW)) {
            onKeyBoardVisibleChange(false);
        } else {
            onKeyBoardVisibleChange(true);
        }
    }
}
