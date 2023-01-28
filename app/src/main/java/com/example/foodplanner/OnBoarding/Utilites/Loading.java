package com.example.foodplanner.OnBoarding.Utilites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


public class Loading {
    static ProgressDialog progress;
    public static void activeLoading(Context requireContext) {
        progress = new ProgressDialog(requireContext);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
    }
    public static void dismiss() {
        progress.dismiss();
    }
    public static void guestAlert(Context requireContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext);
        builder.setMessage("Guest can't use Favorite or Meal List please Sign up to can use it");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
