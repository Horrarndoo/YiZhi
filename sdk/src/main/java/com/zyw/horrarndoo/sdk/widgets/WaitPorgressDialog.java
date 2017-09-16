package com.zyw.horrarndoo.sdk.widgets;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Horrarndoo on 2017/4/17.
 * <p>
 * 等待提示dialog
 */

public class WaitPorgressDialog extends ProgressDialog {

    public WaitPorgressDialog(Context context) {
        this(context, 0);
    }

    public WaitPorgressDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
    }
}
