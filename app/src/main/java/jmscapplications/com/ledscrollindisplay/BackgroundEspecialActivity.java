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

public class BackgroundEspecialActivity extends CustomActivityCenterTittle{
    private ImageAdapter adapter;
    private ArrayList<Boolean> availableItems;
    int currentPosition = 0;
    @BindView(R.id.gridview)
    GridView gridview;
    private boolean isFreeContentRetrieved;
    private boolean isLiteVersion;
    private boolean isUnPayVersion;
    private boolean t1Available;
    private boolean t2Available;
    private boolean t3Available;
    private boolean t4Available;

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
            this.mThumbIds = getIcon(this.isPayVersion, freeItems);
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

        private Integer[] getIcon(boolean isPayVersion, ArrayList<Boolean> freeItems) {
            if (isPayVersion || this.freeContentAchieved || this.isLite) {
                return new Integer[]{R.drawable.b37, R.drawable.b38, R.drawable.b39, R.drawable.b40};
            }
            ArrayList icons = new ArrayList();
            for (int i = 0; i < freeItems.size(); i++) {
                String id = "";
                if ((Boolean) freeItems.get(i)) {
                    id = "b" + String.valueOf((i + 1) + 36);
                } else {
                    id = "b" + String.valueOf((i + 1) + 36) + "l";
                }
                icons.add(BackgroundEspecialActivity.this.getResources().getIdentifier(id, "drawable", BackgroundEspecialActivity.this.getPackageName()));
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
//        this.t1Available = settings.getBoolean("f37", false);
//        this.t2Available = settings.getBoolean("f38", false);
//        this.t3Available = settings.getBoolean("f39", false);
//        this.t4Available = settings.getBoolean("f40", false);

        this.isUnPayVersion =false;
        this.isFreeContentRetrieved =true;
        this.isLiteVersion = true;
        this.t1Available = true;
        this.t2Available = true;
        this.t3Available = true;
        this.t4Available = true;





        this.availableItems = new ArrayList();
        this.availableItems.add(this.t1Available);
        this.availableItems.add(this.t2Available);
        this.availableItems.add(this.t3Available);
        this.availableItems.add(this.t4Available);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.adapter = new ImageAdapter(this, this.isUnPayVersion, this.isFreeContentRetrieved, this.isLiteVersion, this.availableItems, (int) (((float) size.x) / 4.75f));
        this.gridview.setAdapter(this.adapter);
        this.gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                if (!BackgroundEspecialActivity.this.isUnPayVersion || BackgroundEspecialActivity.this.isFreeContentRetrieved || BackgroundEspecialActivity.this.isLiteVersion) {
                    BackgroundEspecialActivity.this.setResult(-1, BackgroundEspecialActivity.this.getIntent().putExtra("value", position + 36));
                    BackgroundEspecialActivity.this.finish();
                } else if ((Boolean) BackgroundEspecialActivity.this.availableItems.get(position)) {
                    BackgroundEspecialActivity.this.setResult(-1, BackgroundEspecialActivity.this.getIntent().putExtra("value", position + 36));
                    BackgroundEspecialActivity.this.finish();
                } else {
                    BackgroundEspecialActivity.this.currentPosition = position + 36;
                    AnalyticMethods.registerEvent(BackgroundEspecialActivity.this, AnalyticMethods.TRY_UNLOCK_FLAGS);
                    BackgroundEspecialActivity.this.showDialog();
                }
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.watch_adv)).setTitle(R.string.unlock);
        builder.setPositiveButton(R.string.unlock, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AnalyticMethods.registerEvent(BackgroundEspecialActivity.this, AnalyticMethods.ACCEPT_TO_SEE_BKG);
                Toast.makeText(BackgroundEspecialActivity.this, BackgroundEspecialActivity.this.getString(R.string.no_adv), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AnalyticMethods.registerEvent(BackgroundEspecialActivity.this, AnalyticMethods.DO_NOT_ACCEPT_TO_WATCH_BKG);
            }
        });
        builder.create().show();
    }
}
