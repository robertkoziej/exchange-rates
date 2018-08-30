package com.robertkoziej.exchangerates.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorDialog {
    private ErrorDialog() {}

    public static AlertDialog getErrorDialog(Context context, int textId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(textId)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        return builder.create();
    }
}
