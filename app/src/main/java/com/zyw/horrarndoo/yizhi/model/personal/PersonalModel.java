package com.zyw.horrarndoo.yizhi.model.personal;

import android.support.annotation.NonNull;

import com.zyw.horrarndoo.yizhi.contract.personal.PersonalContract;

/**
 * Created by Horrarndoo on 2017/9/26.
 * <p>
 */

public class PersonalModel implements PersonalContract.IPersonalModel {

    @NonNull
    public static PersonalModel newInstance() {
        return new PersonalModel();
    }
}
