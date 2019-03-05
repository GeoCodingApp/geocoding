package de.uni_stuttgart.informatik.sopra.sopraapp.ui.util;

import android.app.Activity;
import android.app.Dialog;

/**
 * @author MikeAshi
 */
public class ViewDialog {
    private static final String TAG = "ViewDialog";

    private Activity mActivity;
    private Dialog mDialog;
    private boolean mCancelable;
    private int mLayoutResID;

    public ViewDialog(Activity mActivity, boolean cancelable, int layoutResID) {
        this.mActivity = mActivity;
        this.mDialog = new Dialog(mActivity);
        this.mCancelable = cancelable;
        this.mLayoutResID = layoutResID;
    }

    public void show() {
        mDialog.setCancelable(mCancelable);
        mDialog.setContentView(mLayoutResID);
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }
}
