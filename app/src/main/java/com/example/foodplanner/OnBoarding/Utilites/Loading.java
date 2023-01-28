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
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }
    public static void dismiss() {
        progress.dismiss();
    }
    public static void guestAlert(Context requireContext) {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext);
        // Set the message show for the Alert time
        builder.setMessage("Guest can't use Favorite or Meal List please Sign up to can use it");
        // Set Alert Title
        builder.setTitle("Alert !");
        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);
        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }
}
