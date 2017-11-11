package jmscapplications.com.ledscrollindisplay;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.ckmobile.led.R;
import com.google.gson.Gson;
import java.util.Locale;
import jmscapplications.com.ledscrollindisplay.analityc.AnalyticMethods;
import jmscapplications.com.ledscrollindisplay.custom_views.AutoCompleteValues;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomAutoCompleteTextView;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomButtonView;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomTextView;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomTextView.Weight;
import jmscapplications.com.ledscrollindisplay.engine.LedParameters;
import jmscapplications.com.ledscrollindisplay.engine.LedParametersBuilder;
import jmscapplications.com.ledscrollindisplay.review.ReviewBuilder;


public class SettingActivity extends PreviewActivity implements CustomSeekBarView.CustomSeekBarListener, TextWatcher {
    private static final int BKG_COLOR_REQUEST = 2;
    private static final boolean DEBUG_MODE = false;
    private static final int IMAGE_BKG_REQUEST = 3;
    private static final int REQUEST_TYPE_FACE = 5;
    private static final int SPEECH_REQUEST_CODE = 4;
    private static final int TEXT_COLOR_REQUEST = 1;
    private AutoCompleteValues autoCompleteValues;
    @BindView(R.id.bkg_background)
    ImageButton bkgBackground;
    @BindView(R.id.bkgColorBackground)
    ImageButton bkgColorBackground;
    @BindView(R.id.bkgColorCustomTextView)
    CustomTextView bkgColorCustomTextView;
    @BindView(R.id.bkgCustomTex)
    CustomTextView bkgCustomTex;
    @BindView(R.id.blinkSwitch)
    SwitchCompat blinkSwitch;
    @BindView(R.id.bottom_menu)
    CoordinatorLayout bottomMenu;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.change_typeface)
    CustomTextView changeTypeface;
    @BindView(R.id.circular_background)
    ImageButton circularBackground;
    @BindView(R.id.circularCustomTex)
    CustomTextView circularCustomTex;
    @BindView(R.id.delete_button)
    ImageButton deleteButton;
    @BindView(R.id.display)
    SurfaceView display;
    private Handler handler;
    @BindView(R.id.invertSwitch)
    SwitchCompat invertSwitch;
    private boolean isLiteVersion;
    private boolean isUnPayVersion = true;
    private LedParameters ledParameters;
    @BindView(R.id.mirrorSwitch)
    SwitchCompat mirrorSwitch;
    @BindView(R.id.own_adv)
    CustomButtonView ownAdv;
    @BindView(R.id.playButton)
    FloatingActionButton playButton;
    private Runnable runableUpdate;
    @BindView(R.id.search_button)
    ImageButton searchButton;
    @BindView(R.id.search_field)
    CustomAutoCompleteTextView searchField;
    @BindView(R.id.seekBarField)
    CustomSeekBarView seekBarField;
    @BindView(R.id.square_background)
    ImageButton squareBackground;
    @BindView(R.id.squaredCustomTex)
    CustomTextView squaredCustomTex;
    @BindView(R.id.text_big_bkg)
    ImageButton textBigBkg;
    @BindView(R.id.text_big_txt)
    CustomTextView textBigTxt;
    @BindView(R.id.textColorBackground)
    ImageButton textColorBackground;
    @BindView(R.id.textColorCustomTextView)
    CustomTextView textColorCustomTextView;
    @BindView(R.id.text_small_bkg)
    ImageButton textSmallBkg;
    @BindView(R.id.text_small_txt)
    CustomTextView textSmallTxt;
    @BindView(R.id.top_menu)
    RelativeLayout topMenu;
    @BindView(R.id.toolbar)
    Toolbar        toolbar;

    private  Dialog dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeting);
        ButterKnife.bind(this);
        enableDetectKeyboardHidden(R.id.general_layout);
        this.handler = new Handler(getMainLooper());
        this.runableUpdate = new Runnable() {
            public void run() {
                SettingActivity.this.ledParameters.setMessage(SettingActivity.this.searchField.getText().toString());
                SettingActivity.this.updatePreview(SettingActivity.this.ledParameters);
            }
        };
        getWindow().setBackgroundDrawable(null);


        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.home);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);


        //setActionBarParameters(getString(R.string.home), false);
        this.seekBarField = (CustomSeekBarView) findViewById(R.id.seekBarField);
        this.seekBarField.setSnapping(true, 1);
        this.seekBarField.setOnCustomSeekBarListener(this);
        this.seekBarField.setColor(getResources().getColor(R.color.colorPrimary));
        this.seekBarField.setUnselectedColor(getResources().getColor(R.color.gray));
        this.seekBarField.setPointRadio(22);
        this.seekBarField.setSelectedPointRadio(32);
        String config = getSharedPreferences(getPackageName(), 0).getString("default_values", "");
        if (config.equals("")) {
            this.ledParameters = new LedParametersBuilder().build();
        } else {
            this.ledParameters =new Gson().fromJson(config, LedParameters.class);
        }
        initializeUI();
        new ReviewBuilder("https://play.google.com/store/apps/details?id="+getPackageName()).build().onCreate(this);
    }

    public void onMessageRedeemed(final String value) {
        super.onMessageRedeemed(value);
        Log.i("Json", value);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                SettingActivity.this.ledParameters = (LedParameters) new Gson().fromJson(value, LedParameters.class);
                SettingActivity.this.initializeUI();
                SettingActivity.this.playButton.performClick();
            }
        }, 500);
    }

    protected void onResume() {
        super.onResume();
        this.searchField.addTextChangedListener(this);
        setAutoCompleteValues();
//        SharedPreferences settings = getSharedPreferences("advsettings", 0);
//        this.isUnPayVersion = settings.getBoolean("free_version", true);
//        this.isLiteVersion = settings.getBoolean("light_version", false);
        this.isUnPayVersion =false;
        this.isLiteVersion = true;


//        if (this.isUnPayVersion && !this.isLiteVersion) {
//            showPreview(false);
//            init(this.display);
//            this.display.setVisibility(8);
//            if (this.adView.getVisibility() != 0) {
//                this.ownAdv.setVisibility(0);
//            }
//        }
        if (!this.isUnPayVersion) {
            this.ownAdv.setVisibility(View.GONE);
            showPreview(true);
            init(this.display);
            this.display.setVisibility(View.VISIBLE);
            updatePreview(this.ledParameters);
        }
//        if (this.isLiteVersion) {
//            this.ownAdv.setVisibility(8);
//            showPreview(false);
//            init(this.display);
//            this.display.setVisibility(8);
//        }
    }

    private void initializeUI() {
        boolean isSquaredLed;
        boolean z = true;
        this.seekBarField.setCurrentValue((float) (this.ledParameters.getSpeed() - 1));
        updateImageBackgroundButton(this.ledParameters.isBckgroundImageEnable());
        this.textColorBackground.setColorFilter(this.ledParameters.getTextcolor());
        this.bkgColorBackground.setColorFilter(this.ledParameters.getBackgroundcolor());
        if (ColorUtils.calculateContrast(-1, this.ledParameters.getTextcolor()) < 1.6d) {
            this.textColorCustomTextView.setTextColor(-12303292);
        } else {
            this.textColorCustomTextView.setTextColor(-1);
        }
        if (ColorUtils.calculateContrast(-1, this.ledParameters.getBackgroundcolor()) < 1.6d) {
            this.bkgColorCustomTextView.setTextColor(-12303292);
        } else {
            this.bkgColorCustomTextView.setTextColor(-1);
        }
        if (this.ledParameters.getShape() == 1) {
            isSquaredLed = true;
        } else {
            isSquaredLed = false;
        }
        if (isSquaredLed) {
            selectButton(this.squareBackground, this.squaredCustomTex);
            deselectButton(this.circularBackground, this.circularCustomTex);
        } else {
            deselectButton(this.squareBackground, this.squaredCustomTex);
            selectButton(this.circularBackground, this.circularCustomTex);
        }
        if (this.ledParameters.isSmall()) {
            selectButton(this.textSmallBkg, this.textSmallTxt);
            deselectButton(this.textBigBkg, this.textBigTxt);
        } else {
            selectButton(this.textBigBkg, this.textBigTxt);
            deselectButton(this.textSmallBkg, this.textSmallTxt);
        }
        this.blinkSwitch.setChecked(this.ledParameters.isBlink());
        this.mirrorSwitch.setChecked(this.ledParameters.isMirror());
        SwitchCompat switchCompat = this.invertSwitch;
        if (this.ledParameters.isDirectionStraight()) {
            z = false;
        }
        switchCompat.setChecked(z);
        this.searchField.setText(this.ledParameters.getMessage());
        this.searchField.setSelection(this.searchField.getText().length());
        checkTextFieldButton();
    }

    private void setAutoCompleteValues() {
        String json = getSharedPreferences(getPackageName(), 0).getString("autocomplete", "");
        if (json.equals("")) {
            this.autoCompleteValues = new AutoCompleteValues();
            return;
        }
        this.autoCompleteValues = (AutoCompleteValues) new Gson().fromJson(json, AutoCompleteValues.class);
        this.searchField.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.autoCompleteValues.getValues()));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    int currentTextColor = data.getIntExtra(LedParameters.KEY, 0);
                    if (ColorUtils.calculateContrast(-1, currentTextColor) < 1.6d) {
                        this.textColorCustomTextView.setTextColor(-12303292);
                    } else {
                        this.textColorCustomTextView.setTextColor(-1);
                    }
                    this.ledParameters.setTextColor(currentTextColor);
                    this.textColorBackground.setColorFilter(currentTextColor);
                    return;
                case 2:
                    int currentBkgColor = data.getIntExtra(LedParameters.KEY, 0);
                    if (ColorUtils.calculateContrast(-1, currentBkgColor) < 1.6d) {
                        this.bkgColorCustomTextView.setTextColor(-12303292);
                    } else {
                        this.bkgColorCustomTextView.setTextColor(-1);
                    }
                    this.ledParameters.setBckColor(currentBkgColor);
                    this.bkgColorBackground.setColorFilter(currentBkgColor);
                    return;
                case 3:

                    if(data.hasExtra(LedParameters.KEY)){
                        this.ledParameters.setUri(null);
                        this.ledParameters.setImagePosition(data.getIntExtra(LedParameters.KEY, 0));
                    }else if(data.getData()!=null){
                        this.ledParameters.setUri("content://media"+data.getData().getPath());
                        this.ledParameters.setImagePosition(0);
                    }

                    return;
                case 4:
                    this.searchField.setText((String) data.getStringArrayListExtra("android.speech.extra.RESULTS").get(0));
                    this.searchField.setSelection(this.searchField.getText().length());
                    return;
                case 5:
                    this.ledParameters.setTextType(data.getIntExtra(LedParameters.KEY, 0));
                    return;
                default:
                    return;
            }
        } else if (resultCode == 0) {
            switch (requestCode) {
                case 3:
                    updateImageBackgroundButton(false);
                    this.ledParameters.setBckgroundImageEnable(false);
                    return;
                default:
                    return;
            }
        }
    }

    public void onKeyBoardVisibleChange(boolean isVisible) {
        super.onKeyBoardVisibleChange(isVisible);
        if (!isVisible) {
        }
    }

    public void onCustomSeekBarChangedListener(float v) {
        this.ledParameters.setSpeed(Math.round(v) + 1);
        updatePreview(this.ledParameters);
    }

    public void onCustomSeekBarProgressListener(float v) {
    }

    public void startEditingCustomSeekBar() {
    }

    public void endEditingCustomSeekBar() {
    }

    public void actionCustomSeekBar(int i) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.share){
            this.ledParameters.setMessage(this.searchField.getText().toString());
            updatePreview(this.ledParameters);
            share(ledParameters);
        }else if(item.getItemId()==R.id.about){


            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(android.R.string.dialog_alert_title);
            float des=getResources().getDisplayMetrics().density;
            TextView textView=new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(18);
            textView.setPadding((int) (des*20),0,0,0);
            textView.setMovementMethod(LinkMovementMethod.getInstance());


            String feedback=getString(R.string.feedback);
            String message="Version:"+getAppVersionName(this)+"\n"+feedback;
            SpannableString spannableString=new SpannableString(message);
            spannableString.setSpan(new AbsoluteSizeSpan((int) (20*des)),message.indexOf(feedback),message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","caokai363541180@gmail.com", null));
                    startActivity(Intent.createChooser(emailIntent, "Choose Email Client"));
                    dialog.dismiss();
                }
            },message.indexOf(feedback),message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);

            builder.setView(textView);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setCancelable(true);
            dialog=builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    public static String getAppVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String sVersion = String.valueOf(info.versionName);
            return sVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }


    public void share(LedParameters parameters) {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/plain");
        i.putExtra("android.intent.extra.SUBJECT", "Sharing");
        i.putExtra("android.intent.extra.TEXT", parameters.getMessage()+" " +
                "   "+"https://play.google.com/store/apps/developer?id="+getPackageName());
        startActivity(Intent.createChooser(i, "Share"));

    }

    @OnClick({R.id.delete_button, R.id.search_button, R.id.squaredCustomTex, R.id.circularCustomTex, R.id.text_small_txt, R.id.text_big_txt,
            R.id.bkgCustomTex, R.id.textColorCustomTextView, R.id.bkgColorCustomTextView, R.id.playButton, R.id.invertSwitch, R.id.mirrorSwitch,
            R.id.blinkSwitch, R.id.own_adv,R.id.change_typeface})
    public void onViewClicked(View view) {
        boolean z = true;
        LedParameters ledParameters;
        switch (view.getId()) {
            case R.id.search_button:
                displaySpeechRecognizer(this);
                return;
            case R.id.own_adv:
                AnalyticMethods.registerEvent(this, AnalyticMethods.OPEN_PURCHASE_MENU);
                AnalyticMethods.registerEvent(this, AnalyticMethods.OPEN_PURCHASE_MENU_FROM_BANNER);
              //  startActivity(new Intent(this, PurchaseLedProductsActivity.class));
                return;
            case R.id.delete_button:
                this.searchField.setText("");
                checkTextFieldButton();
                return;
            case R.id.invertSwitch:
                ledParameters = this.ledParameters;
                if (this.invertSwitch.isChecked()) {
                    z = false;
                }
                ledParameters.setStraightDirection(z);
                updatePreview(this.ledParameters);
                return;
            case R.id.mirrorSwitch:
                this.ledParameters.setMirror(this.mirrorSwitch.isChecked());
                updatePreview(this.ledParameters);
                return;
            case R.id.blinkSwitch:
                this.ledParameters.setBlink(this.blinkSwitch.isChecked());
                updatePreview(this.ledParameters);
                return;
            case R.id.change_typeface:
                AnalyticMethods.registerEvent(this, AnalyticMethods.OPEN_TEXT);
                startActivityForResult(new Intent(this, FontActivity.class), 5);
                return;
//            case R.id.share_text:
//                AnalyticMethods.registerEvent(this, AnalyticMethods.SHARE_VIDEO);
//                AnalyticMethods.registerEvent(this, AnalyticMethods.SHARE_VIDEO_SETTINGS);
//                this.ledParameters.setMessage(this.searchField.getText().toString());
//                updatePreview(this.ledParameters);
//                return;
            case R.id.bkgCustomTex:
                boolean z2;
                boolean isBckEnable = this.ledParameters.isBckgroundImageEnable();
                if (isBckEnable) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                updateImageBackgroundButton(z2);
                ledParameters = this.ledParameters;
                if (isBckEnable) {
                    z = false;
                }
                ledParameters.setBckgroundImageEnable(z);
                if (isBckEnable) {
                    updatePreview(this.ledParameters);
                    return;
                }
                AnalyticMethods.registerEvent(this, AnalyticMethods.OPEN_IMAGE);
                startActivityForResult(new Intent(this, BackGroundListActivity.class), 3);
                return;
            case R.id.textColorCustomTextView:
                startActivityForResult(new Intent(this, ColorActivity.class).putExtra(LedParameters.KEY, this.ledParameters.getTextcolor()), 1);
                return;
            case R.id.bkgColorCustomTextView:
                startActivityForResult(new Intent(this, ColorActivity.class).putExtra(LedParameters.KEY, this.ledParameters.getBackgroundcolor()), 2);
                return;
            case R.id.text_small_txt:
                selectButton(this.textSmallBkg, this.textSmallTxt);
                deselectButton(this.textBigBkg, this.textBigTxt);
                this.ledParameters.setSmall(true);
                updatePreview(this.ledParameters);
                return;
            case R.id.text_big_txt:
                selectButton(this.textBigBkg, this.textBigTxt);
                deselectButton(this.textSmallBkg, this.textSmallTxt);
                this.ledParameters.setSmall(false);
                updatePreview(this.ledParameters);
                return;
            case R.id.squaredCustomTex:
                selectButton(this.squareBackground, this.squaredCustomTex);
                deselectButton(this.circularBackground, this.circularCustomTex);
                this.ledParameters.setShape(1);
                updatePreview(this.ledParameters);
                return;
            case R.id.circularCustomTex:
                selectButton(this.circularBackground, this.circularCustomTex);
                deselectButton(this.squareBackground, this.squaredCustomTex);
                this.ledParameters.setShape(2);
                updatePreview(this.ledParameters);
                return;
            case R.id.playButton:
                this.autoCompleteValues.add(this.searchField.getText().toString());
                String autoCompleteJson = new Gson().toJson(this.autoCompleteValues);
                this.ledParameters.setMessage(this.searchField.getText().toString());
                updatePreview(this.ledParameters);
                String json = new Gson().toJson(this.ledParameters);
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
                sharedPreferences.edit().putString("default_values", json).apply();
                sharedPreferences.edit().putString("autocomplete", autoCompleteJson).apply();
                startActivity(new Intent(this, DisplayScreenActivity.class).putExtra(LedParameters.KEY, json));
                return;
            default:
                return;
        }
    }

    public void displaySpeechRecognizer(Activity activity) {
        try {
            Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
            intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
            intent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
            intent.putExtra("android.speech.extra.PROMPT", activity.getString(R.string.say_your_text));
            intent.putExtra("android.speech.extra.MAX_RESULTS", 3);
            intent.putExtra("android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS", 100);
            activity.startActivityForResult(intent, 4);
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + appPackageName)));
            } catch (ActivityNotFoundException e2) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    private void updateImageBackgroundButton(boolean isBckEnable) {
        if (isBckEnable) {
            selectButton(this.bkgBackground, this.bkgCustomTex);
        } else {
            deselectButton(this.bkgBackground, this.bkgCustomTex);
        }
    }

    private void deselectButton(ImageButton view, CustomTextView text) {
        view.setColorFilter(getResources().getColor(R.color.gray_dark));
        text.setTypeFace(Weight.LIGHT.ordinal(), this);
    }

    private void selectButton(ImageButton view, CustomTextView text) {
        view.setColorFilter(getResources().getColor(R.color.colorPrimary));
        text.setTypeFace(Weight.BOLD.ordinal(), this);
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        checkTextFieldButton();
        this.handler.removeCallbacks(this.runableUpdate);
        this.handler.postDelayed(this.runableUpdate, 300);
    }

    private void checkTextFieldButton() {
        if (this.searchField.getText().toString().isEmpty()) {
            this.searchButton.setVisibility(View.VISIBLE);
            this.deleteButton.setVisibility(View.GONE);
            return;
        }
        this.searchButton.setVisibility(View.GONE);
        this.deleteButton.setVisibility(View.VISIBLE);
    }
}
