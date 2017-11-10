package jmscapplications.com.ledscrollindisplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ckmobile.led.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomActivityCenterTittle;

public class BackGroundListActivity extends CustomActivityCenterTittle {
    @BindView(R.id.countriesLayout)
    RelativeLayout countriesLayout;
    @BindView(R.id.loveLayout)
    RelativeLayout loveLayout;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_bkg_list);
        ButterKnife.bind((Activity) this);
        enableDetectKeyboardHidden(R.id.general_layout);
        setActionBarParameters(getString(R.string.bkg), true);
        this.countriesLayout.setTranslationX(800.0f);
        SpringAnimation springAnim = new SpringAnimation(this.countriesLayout, DynamicAnimation.TRANSLATION_X, 0.0f);
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        springAnim.setStartValue(800.0f);
        springAnim.start();
        this.loveLayout.setTranslationX(800.0f);
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            public void run() {
                SpringAnimation springAnim2 = new SpringAnimation(BackGroundListActivity.this.loveLayout, DynamicAnimation.TRANSLATION_X, 0.0f);
                springAnim2.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
                springAnim2.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
                springAnim2.setStartValue(800.0f);
                springAnim2.start();
            }
        }, 70);
    }

    @OnClick({R.id.countriesButton, R.id.loveButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.countriesButton:
                Intent intent = new Intent(this, BackgroundFlagActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
                return;
            case R.id.loveButton:
                Intent intent2 = new Intent(this, BackgroundEspecialActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent2);
                finish();
                return;
            default:
                return;
        }
    }
}
