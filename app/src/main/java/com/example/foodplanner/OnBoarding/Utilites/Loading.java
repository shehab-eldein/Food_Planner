package com.example.foodplanner.OnBoarding.Utilites;

import android.app.ProgressDialog;
import android.content.Context;


public class Loading {
    static ProgressDialog progress;
    public static void activeLoading(Context requireContext) {
        progress = new ProgressDialog(requireContext);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }
    public static void dismiss() {
        progress.dismiss();
    }
}
