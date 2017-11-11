package jmscapplications.com.ledscrollindisplay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.ckmobile.led.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import jmscapplications.com.ledscrollindisplay.analityc.AnalyticMethods;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomActivityCenterTittle;
import jmscapplications.com.ledscrollindisplay.custom_views.CustomTextView;
import jmscapplications.com.ledscrollindisplay.engine.LedParameters;

public class BackgroundFlagActivity extends CustomActivityCenterTittle{
    private ImageAdapter adapter;
    private ArrayList<Boolean> availableItems;
    int currentPosition = 0;
    @BindView(R.id.gridview)
    GridView gridview;
    private boolean isFreeContentRetrieved;
    private boolean isLiteVersion;
    private boolean isUnPayVersion;
    private boolean t10Available;
    private boolean t11Available;
    private boolean t12Available;
    private boolean t13Available;
    private boolean t14Available;
    private boolean t15Available;
    private boolean t16Available;
    private boolean t17Available;
    private boolean t18Available;
    private boolean t19Available;
    private boolean t1Available;
    private boolean t20Available;
    private boolean t21Available;
    private boolean t22Available;
    private boolean t23Available;
    private boolean t24Available;
    private boolean t25Available;
    private boolean t26Available;
    private boolean t27Available;
    private boolean t28Available;
    private boolean t29Available;
    private boolean t2Available;
    private boolean t30Available;
    private boolean t31Available;
    private boolean t32Available;
    private boolean t33Available;
    private boolean t34Available;
    private boolean t35Available;
    private boolean t36Available;
    private boolean t3Available;
    private boolean t4Available;
    private boolean t5Available;
    private boolean t6Available;
    private boolean t7Available;
    private boolean t8Available;
    private boolean t9Available;

    public class ImageAdapter extends BaseAdapter {
        private final boolean freeContentAchieved;
        private final ArrayList<Boolean> freeItems;
        private final boolean isLite;
        private final boolean isPayVersion;
        private Context mContext;
        private final Integer[] mThumbIds;
        private final int width;

        public ImageAdapter(Context c, boolean isUnPayVersion, boolean isFreeContentRetrieved, boolean isLite, ArrayList<Boolean> freeItems, int width) {
            this.mContext = c;
            this.isPayVersion = !isUnPayVersion;
            this.freeItems = freeItems;
            this.isLite = isLite;
            this.freeContentAchieved = isFreeContentRetrieved;
            this.width = width;
            this.mThumbIds = getIcon(this.isPayVersion, freeItems, isLite);
        }

        public int getCount() {
            return this.freeItems.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(this.mContext);
                imageView.setLayoutParams(new LayoutParams(this.width, (int) (((float) this.width) * 0.61f)));
                imageView.setScaleType(ScaleType.FIT_XY);
                imageView.setPadding(0, 0, 0, 0);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(this.mThumbIds[position]);
            return imageView;
        }

        private Integer[] getIcon(boolean isPayVersion, ArrayList<Boolean> freeItems, boolean isLite) {
            if (isPayVersion || this.freeContentAchieved || isLite) {
                return new Integer[]{R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5,
                        R.drawable.b6, R.drawable.b7, R.drawable.b8,
                        R.drawable.b9, R.drawable.b10, R.drawable.b11,
                        R.drawable.b12, R.drawable.b13, R.drawable.b14,
                        R.drawable.b15, R.drawable.b16, R.drawable.b17,
                        R.drawable.b18, R.drawable.b19, R.drawable.b20,
                        R.drawable.b21, R.drawable.b22, R.drawable.b23,
                        R.drawable.b24, R.drawable.b25, R.drawable.b26,
                        R.drawable.b27, R.drawable.b28, R.drawable.b29,
                        R.drawable.b30, R.drawable.b31, R.drawable.b32,
                        R.drawable.b33, R.drawable.b34, R.drawable.b35,
                        R.drawable.b36};
            }
            ArrayList<Integer> icons = new ArrayList();
            for (int i = 0; i < freeItems.size(); i++) {
                String id = "";
                if ((Boolean) freeItems.get(i)) {
                    id = "b" + String.valueOf(i + 1);
                } else {
                    id = "b" + String.valueOf(i + 1) + "l";
                }
                icons.add(BackgroundFlagActivity.this.getResources().getIdentifier(id, "drawable", BackgroundFlagActivity.this.getPackageName()));
            }
            return (Integer[]) icons.toArray(new Integer[icons.size()]);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fonts);
        ButterKnife.bind((Activity) this);
        enableDetectKeyboardHidden(R.id.general_layout);
        setActionBarParameters(getString(R.string.bkg), true);
        this.gridview.setTranslationX(800.0f);
        SpringAnimation springAnim = new SpringAnimation(this.gridview, DynamicAnimation.TRANSLATION_X, 0.0f);
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        springAnim.setStartValue(800.0f);
        springAnim.start();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }


    protected void onResume() {
        super.onResume();
//        SharedPreferences settings = getSharedPreferences("advsettings", 0);
//        this.isUnPayVersion = settings.getBoolean("free_version", true);
//        this.isFreeContentRetrieved = settings.getBoolean("free_content", false);
//        this.isLiteVersion = settings.getBoolean("light_version", false);
//        this.t1Available = settings.getBoolean("f1", false);
//        this.t2Available = settings.getBoolean("f2", false);
//        this.t3Available = settings.getBoolean("f3", false);
//        this.t4Available = settings.getBoolean("f4", false);
//        this.t5Available = settings.getBoolean("f5", false);
//        this.t6Available = settings.getBoolean("f6", false);
//        this.t7Available = settings.getBoolean("f7", false);
//        this.t8Available = settings.getBoolean("f8", false);
//        this.t9Available = settings.getBoolean("f9", false);
//        this.t10Available = settings.getBoolean("f10", false);
//        this.t11Available = settings.getBoolean("f11", false);
//        this.t12Available = settings.getBoolean("f12", false);
//        this.t13Available = settings.getBoolean("f13", false);
//        this.t14Available = settings.getBoolean("f14", false);
//        this.t15Available = settings.getBoolean("f15", false);
//        this.t16Available = settings.getBoolean("f16", false);
//        this.t17Available = settings.getBoolean("f17", false);
//        this.t18Available = settings.getBoolean("f18", false);
//        this.t19Available = settings.getBoolean("f19", false);
//        this.t20Available = settings.getBoolean("f20", false);
//        this.t21Available = settings.getBoolean("f21", false);
//        this.t22Available = settings.getBoolean("f22", false);
//        this.t23Available = settings.getBoolean("f23", false);
//        this.t24Available = settings.getBoolean("f24", false);
//        this.t25Available = settings.getBoolean("f25", false);
//        this.t26Available = settings.getBoolean("f26", false);
//        this.t27Available = settings.getBoolean("f27", false);
//        this.t28Available = settings.getBoolean("f28", false);
//        this.t29Available = settings.getBoolean("f29", false);
//        this.t30Available = settings.getBoolean("f30", false);
//        this.t31Available = settings.getBoolean("f31", false);
//        this.t32Available = settings.getBoolean("f32", false);
//        this.t33Available = settings.getBoolean("f33", false);
//        this.t34Available = settings.getBoolean("f34", false);
//        this.t35Available = settings.getBoolean("f35", false);
//        this.t36Available = settings.getBoolean("f36", false);


        this.isUnPayVersion = false;
        this.isFreeContentRetrieved =true;
        this.isLiteVersion =true;
        this.t1Available =true;
        this.t2Available =true;
        this.t3Available =true;
        this.t4Available = true;
        this.t5Available =true;
        this.t6Available =true;
        this.t7Available =true;
        this.t8Available =true;
        this.t9Available = true;
        this.t10Available =true;
        this.t11Available =true;
        this.t12Available =true;
        this.t13Available =true;
        this.t14Available =true;
        this.t15Available =true;
        this.t16Available =true;
        this.t17Available =true;
        this.t18Available =true;
        this.t19Available =true;
        this.t20Available =true;
        this.t21Available =true;
        this.t22Available = true;
        this.t23Available = true;
        this.t24Available = true;
        this.t25Available =true;
        this.t26Available =true;
        this.t27Available =true;
        this.t28Available =true;
        this.t29Available =true;
        this.t30Available =true;
        this.t31Available =true;
        this.t32Available =true;
        this.t33Available =true;
        this.t34Available =true;
        this.t35Available =true;
        this.t36Available =true;




        this.availableItems = new ArrayList();
        this.availableItems.add(this.t1Available);
        this.availableItems.add(this.t2Available);
        this.availableItems.add(this.t3Available);
        this.availableItems.add(this.t4Available);
        this.availableItems.add(this.t5Available);
        this.availableItems.add(this.t6Available);
        this.availableItems.add(this.t7Available);
        this.availableItems.add(this.t8Available);
        this.availableItems.add(this.t9Available);
        this.availableItems.add(this.t10Available);
        this.availableItems.add(this.t11Available);
        this.availableItems.add(this.t12Available);
        this.availableItems.add(this.t13Available);
        this.availableItems.add(this.t14Available);
        this.availableItems.add(this.t15Available);
        this.availableItems.add(this.t16Available);
        this.availableItems.add(this.t17Available);
        this.availableItems.add(this.t18Available);
        this.availableItems.add(this.t19Available);
        this.availableItems.add(this.t20Available);
        this.availableItems.add(this.t21Available);
        this.availableItems.add(this.t22Available);
        this.availableItems.add(this.t23Available);
        this.availableItems.add(this.t24Available);
        this.availableItems.add(this.t25Available);
        this.availableItems.add(this.t26Available);
        this.availableItems.add(this.t27Available);
        this.availableItems.add(this.t28Available);
        this.availableItems.add(this.t29Available);
        this.availableItems.add(this.t30Available);
        this.availableItems.add(this.t31Available);
        this.availableItems.add(this.t32Available);
        this.availableItems.add(this.t33Available);
        this.availableItems.add(this.t34Available);
        this.availableItems.add(this.t35Available);
        this.availableItems.add(this.t36Available);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.adapter = new ImageAdapter(this, this.isUnPayVersion, this.isFreeContentRetrieved, this.isLiteVersion, this.availableItems, (int) (((float) size.x) / 4.75f));
        this.gridview.setAdapter(this.adapter);
        this.gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                if (!BackgroundFlagActivity.this.isUnPayVersion || BackgroundFlagActivity.this.isFreeContentRetrieved || BackgroundFlagActivity.this.isLiteVersion) {
                    BackgroundFlagActivity.this.setResult(-1, BackgroundFlagActivity.this.getIntent().putExtra(LedParameters.KEY, position));
                    BackgroundFlagActivity.this.finish();
                } else if ((Boolean) BackgroundFlagActivity.this.availableItems.get(position)) {
                    BackgroundFlagActivity.this.setResult(-1, BackgroundFlagActivity.this.getIntent().putExtra(LedParameters.KEY, position));
                    BackgroundFlagActivity.this.finish();
                } else {
                    BackgroundFlagActivity.this.currentPosition = position;
                    AnalyticMethods.registerEvent(BackgroundFlagActivity.this, AnalyticMethods.TRY_UNLOCK_FLAGS);
                    BackgroundFlagActivity.this.showDialog();
                }
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.watch_adv)).setTitle(R.string.unlock);
        builder.setPositiveButton(R.string.unlock, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AnalyticMethods.registerEvent(BackgroundFlagActivity.this, AnalyticMethods.ACCEPT_TO_SEE_BKG);

                Toast.makeText(BackgroundFlagActivity.this, BackgroundFlagActivity.this.getString(R.string.no_adv), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AnalyticMethods.registerEvent(BackgroundFlagActivity.this, AnalyticMethods.DO_NOT_ACCEPT_TO_WATCH_BKG);
            }
        });
        builder.create().show();
    }
}
