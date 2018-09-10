package com.zyw.horrarndoo.sdk.widgets;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Horrarndoo on 2017/4/17.
 * <p>
 * 等待提示dialog
 */

public class WaitProgressDialog extends ProgressDialog {

    public WaitProgressDialog(Context context) {
        this(context, 0);
    }

    public WaitProgressDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
    }
}
