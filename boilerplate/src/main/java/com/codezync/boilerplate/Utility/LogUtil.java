package com.codezync.boilerplate.Utility;

import android.util.Log;

import com.codezync.boilerplate.AppConfigurations;


public class LogUtil {

    public static void error(String TAG, Exception error) {
        //Send logs to firebase
        // FirebaseManager.setFirebaseLog(TAG + " " + error.getLocalizedMessage());
        if (!AppConfigurations.isRELEASE) {
            Log.e(TAG, error.getMessage());
            error.printStackTrace();
        }
    }

    public static void error(String TAG, String error) {
        //Send logs to firebase
        if (!AppConfigurations.isRELEASE) {
            Log.e(TAG, error);
        }
    }

    public static void debug(String TAG, String error) {
        //Send logs to firebase
        if (!AppConfigurations.isRELEASE) {
            Log.e(TAG, error);
        }
    }

    public static void debug(String TAG, String error, Exception e) {
        //Send logs to firebase
        if (!AppConfigurations.isRELEASE) {
            Log.e(TAG, error);
        }
        e.printStackTrace();
    }


    public static void info(String TAG, String error) {
        //Send logs to firebase
        if (!AppConfigurations.isRELEASE) {
            Log.e(TAG, error);
        }
    }

    public static void warning(String TAG, String error) {
        //Send logs to firebase
        if (!AppConfigurations.isRELEASE) {
            Log.e(TAG, error);
        }
    }

}
