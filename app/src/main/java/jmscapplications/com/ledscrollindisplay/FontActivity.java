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

public class FontActivity extends CustomActivityCenterTittle{
    private ImageAdapter adapter;
    private ArrayList<Boolean> availableItems;
    int currentPosition = 0;
    @BindView(R.id.gridview)
    GridView gridview;
    private boolean isLiteVersion;
    private boolean isUnPayVersion;
    private boolean t1Available;
    private boolean t2Available;
    private boolean t3Available;
    private boolean t4Available;
    private boolean t5Available;
    private boolean t6Available;
    private boolean t7Available;

    public class ImageAdapter extends BaseAdapter {
        private final ArrayList<Boolean> freeItems;
        private final boolean isLite;
        private final boolean isPayVersion;
        private Context mContext;
        private final Integer[] mThumbIds;
        private final int width;

        public ImageAdapter(Context c, boolean isUnPayVersion, boolean isLite, ArrayList<Boolean> freeItems, int width) {
            this.mContext = c;
            this.isPayVersion = !isUnPayVersion;
            this.isLite = isLite;
            this.freeItems = freeItems;
            this.mThumbIds = getIcon(this.isPayVersion, this.isLite, freeItems);
            this.width = width;
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

        private Integer[] getIcon(boolean isPayVersion, boolean isLite, ArrayList<Boolean> freeItems) {
            if (isPayVersion || isLite) {
                return new Integer[]{R.drawable.unlock1, R.drawable.unlock2, R.drawable.unlock3,
                        R.drawable.unlock4, R.drawable.unlock5,
                        R.drawable.unlock6, R.drawable.unlock7};
            }
            ArrayList<Integer> icons = new ArrayList();
            for (int i = 0; i < freeItems.size(); i++) {
                switch (i) {
                    case 0:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock1);
                            break;
                        }
                        icons.add(R.drawable.unlock1);
                        break;
                    case 1:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock2);
                            break;
                        }
                        icons.add(R.drawable.unlock2);
                        break;
                    case 2:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock3);
                            break;
                        }
                        icons.add(R.drawable.unlock3);
                        break;
                    case 3:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock4);
                            break;
                        }
                        icons.add(R.drawable.unlock4);
                        break;
                    case 4:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock5);
                            break;
                        }
                        icons.add(R.drawable.unlock5);
                        break;
                    case 5:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock6);
                            break;
                        }
                        icons.add(R.drawable.unlock6);
                        break;
                    case 6:
                        if (!freeItems.get(i)) {
                            icons.add(R.drawable.lock7);
                            break;
                        }
                        icons.add(R.drawable.unlock7);
                        break;
                    default:
                        break;
                }
            }
            return icons.toArray(new Integer[icons.size()]);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);
        ButterKnife.bind(this);
        enableDetectKeyboardHidden(R.id.general_layout);
        setActionBarParameters(getString(R.string.font_tittle), true);
        this.gridview.setTranslationX(800.0f);
        SpringAnimation springAnim = new SpringAnimation(this.gridview, DynamicAnimation.TRANSLATION_X, 0.0f);
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        springAnim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        springAnim.setStartValue(800.0f);
        springAnim.start();
    }


    protected void onResume() {
        super.onResume();
//        SharedPreferences settings = getSharedPreferences("advsettings", 0);
//        this.isUnPayVersion = settings.getBoolean("free_version", true);
//        this.isLiteVersion = settings.getBoolean("light_version", false);
//        this.t1Available = settings.getBoolean("t1", true);
//        this.t2Available = settings.getBoolean("t2", false);
//        this.t3Available = settings.getBoolean("t3", false);
//        this.t4Available = settings.getBoolean("t4", false);
//        this.t5Available = settings.getBoolean("t5", false);
//        this.t6Available = settings.getBoolean("t6", false);
//        this.t7Available = settings.getBoolean("t7", false);

        this.isUnPayVersion = false;
        this.isLiteVersion = true;
        this.t1Available =true;
        this.t2Available =true;
        this.t3Available = true;
        this.t4Available = true;
        this.t5Available =true;
        this.t6Available = true;
        this.t7Available = true;








        this.availableItems = new ArrayList();
        this.availableItems.add(this.t1Available);
        this.availableItems.add(this.t2Available);
        this.availableItems.add(this.t3Available);
        this.availableItems.add(this.t4Available);
        this.availableItems.add(this.t5Available);
        this.availableItems.add(this.t6Available);
        this.availableItems.add(this.t7Available);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.adapter = new ImageAdapter(this, this.isUnPayVersion, this.isLiteVersion, this.availableItems, (int) (((float) size.x) / 4.75f));
        this.gridview.setAdapter(this.adapter);
        this.gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                if (!FontActivity.this.isUnPayVersion || FontActivity.this.isLiteVersion) {
                    FontActivity.this.setResult(-1, FontActivity.this.getIntent().putExtra("value", position));
                    FontActivity.this.finish();
                } else if ((Boolean) FontActivity.this.availableItems.get(position)) {
                    FontActivity.this.setResult(-1, FontActivity.this.getIntent().putExtra("value", position));
                    FontActivity.this.finish();
                } else {
                    FontActivity.this.currentPosition = position;
                    AnalyticMethods.registerEvent(FontActivity.this, AnalyticMethods.TRY_UNLOCK);
                    FontActivity.this.showDialog();
                }
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.watch_adv)).setTitle(R.string.unlock);
        builder.setPositiveButton(R.string.unlock, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AnalyticMethods.registerEvent(FontActivity.this, AnalyticMethods.ACCEPT_TO_SEE_TEXT);
                Toast.makeText(FontActivity.this, FontActivity.this.getString(R.string.no_adv), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AnalyticMethods.registerEvent(FontActivity.this, AnalyticMethods.DO_NOT_ACCEPT_TO_WATCH_TEXT);
            }
        });
        builder.create().show();
    }
}
