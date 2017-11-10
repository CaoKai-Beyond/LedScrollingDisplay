package jmscapplications.com.ledscrollindisplay;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.ckmobile.led.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomActivityCenterTittle;

public class ColorActivity extends CustomActivityCenterTittle {

    @BindView(R.id.color_picker)
    ColorPicker colorPicker;
    @BindView(R.id.colorPickerBackground)
    LinearLayout colorPickerBackground;
    private int colorValue;
    @BindView(R.id.selectLayout)
    FrameLayout selectLayout;
    @BindView(R.id.svbar)
    SVBar svbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_color_picker);
        ButterKnife.bind((Activity) this);
        enableDetectKeyboardHidden(R.id.general_layout);
        setActionBarParameters(getString(R.string.select_color), true);
        this.colorValue = getIntent().getIntExtra("value", 0);
        this.colorPicker.addSVBar(this.svbar);
        this.colorPicker.setShowOldCenterColor(false);
        this.colorPicker.setColor(this.colorValue);
        this.colorPickerBackground.setTranslationX(800.0f);
        SpringAnimation springAnim = new SpringAnimation(this.colorPickerBackground, DynamicAnimation.TRANSLATION_X, 0.0f);
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        springAnim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        springAnim.setStartValue(800.0f);
        springAnim.start();
        this.selectLayout.setTranslationX(800.0f);
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            public void run() {
                SpringAnimation springAnim2 = new SpringAnimation(ColorActivity.this.selectLayout, DynamicAnimation.TRANSLATION_X, 0.0f);
                springAnim2.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
                springAnim2.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
                springAnim2.setStartValue(800.0f);
                springAnim2.start();
            }
        }, 80);
    }
    @OnClick({R.id.okCustomTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.okCustomTextView:
                setResult(-1, getIntent().putExtra("value", this.colorPicker.getColor()));
                finish();
                return;
            default:
                return;
        }
    }

}
