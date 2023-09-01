package com.codezync.boilerplate.Utility;

import static android.content.Context.VIBRATOR_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;

import com.airbnb.lottie.BuildConfig;



public class DeviceManagerUtil {

    private static Activity mActivity;
    private static Vibrator myVib;


    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (!ExtenstionMethods.isNotEmptyString(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }


//    public static void redirectToStore(Activity activity) {
//        mActivity = activity;
//        googlePlayStore();
//    }

//    private static void googlePlayStore() {
//        try {
//            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PLAY_STORE_ID)));
//        } catch (android.content.ActivityNotFoundException anfe) {
//            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + PLAY_STORE_ID)));
//        }
//        mActivity.finish();
//    }
//

    public static void vibrate(Context context) {

        if (myVib == null) {
            myVib = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        }
        myVib.vibrate(50);
    }


    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }
}
