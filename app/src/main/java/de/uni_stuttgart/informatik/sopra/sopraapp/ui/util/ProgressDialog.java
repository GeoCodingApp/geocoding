package de.uni_stuttgart.informatik.sopra.sopraapp.ui.util;

import android.app.Activity;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * @author MikeAshi
 */
public class ProgressDialog extends ViewDialog {

    public ProgressDialog(Activity mActivity) {
        super(mActivity, false, R.layout.progress_spinner);
    }
}
