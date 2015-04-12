package com.projet.M1.Module;

import android.util.Log;

/**
 * Created by clement on 12/04/2015.
 */
public class ModuleException extends Exception {
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        Log.e("Module Exception","porbleme menu");
    }
}
