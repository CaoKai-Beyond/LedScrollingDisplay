package jmscapplications.com.ledscrollindisplay;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.ckmobile.led.R;

public class AdvDialog extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setMessage(R.string.watch_adv).setPositiveButton(R.string.unlock, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNegativeButton(R.string.cancel, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder.create();
    }
}
