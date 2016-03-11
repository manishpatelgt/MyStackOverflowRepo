package com.mystacky;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by Manish on 12/11/2015.
 */
public class ErrorDialogFragment extends DialogFragment {

    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;

    public ErrorDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the error code and retrieve the appropriate dialog
        int errorCode = this.getArguments().getInt(DIALOG_ERROR);
        return GoogleApiAvailability.getInstance().getErrorDialog(
                this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((LoginActivity2) getActivity()).onDialogDismissed();
    }
}
