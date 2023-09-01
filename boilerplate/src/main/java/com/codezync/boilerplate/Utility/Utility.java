package com.codezync.boilerplate.Utility;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.BuildConfig;
import com.codezync.boilerplate.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class Utility extends LogUtil {

    private static final String TAG = Utility.class.getSimpleName();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int RC_HINT = 1000;

    private final static DecimalFormat currencyFormat = new DecimalFormat("0.00");
    private final static DecimalFormat twoDecimalFormat = new DecimalFormat("00.00");
    private static GoogleApiClient googleApiClient;

//    public static String getPhoneNo(Intent data, String code) {
//        Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
//        String phoneNumber = null;
//        if (cred.getId().contains(code)) {
//
//            phoneNumber = cred.getId().replace(code, "");
//        }
//        return phoneNumber;
//
//    }

    public static void showToast(ToastTypes type, Context context, String message) {
        switch (type) {
            case INFO:
                infoToast(context, message);
                break;

            case WARNING:
                warningToast(context, message);
                break;
            case ERROR:
                errorToast(context, message);
                break;
            case SUCCESS:
                successToast(context, message);
                break;
            default:
        }
    }

    public static List<Double> getLatLagToArray(LatLng latLng) {
        List<Double> startPoints = new ArrayList<>();
        startPoints.add(latLng.longitude);
        startPoints.add(latLng.latitude);
        return startPoints;
    }


    public static LatLng getArrayToLatLng(List<Double> points) {
        return new LatLng(points.get(1), points.get(0));
    }

    public static LatLng getArrayToLatLngReverse(List<Double> points) {
        return new LatLng(points.get(0), points.get(1));
    }

//    protected void initStetho(Context context) {
//        Stetho.initializeWithDefaults(context);
//    }


    public static String getDecimalValue(double value) {
        return currencyFormat.format(value);
    }

    public static String getDecimalValueWithCurrency(Activity activity, double value) {
        return activity.getString(R.string.currency) + " " + currencyFormat.format(value);
    }

    public static String getDecimalValue(int value) {
        return currencyFormat.format(value);
    }


    protected int toInt(String value) {
        if (StringUtility.isNotNull(value)) {
            return Integer.parseInt(value);
        } else {
            error(TAG, new Exception("toInt : Value is null returns 0"));
            return 0;

        }
    }


    private static void fetchPhoneNumberFromDevice() {

    }

    public static String getNameFromEmail(String email, Context context) {
        String possibleEmail = "";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
            }
        }
        return possibleEmail;

    }

    public static String getFirstWord(String text) {

        int index = text.indexOf(' ');

        if (index > -1) { // Check if there is more than one word.

            return text.substring(0, index).trim(); // Extract first word.

        } else {

            return text; // Text is the first word itself.
        }
    }

//    public static void fetchDataFromDevice(Context context, DeviceData deviceData) {
//        LogUtil.error(TAG, "fetchDataFromDevice");
//        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
//
//        boolean isEmail = false, isPhoneNumber = false;
//        switch (deviceData) {
//            case EMAIL:
//                isEmail = true;
//                isPhoneNumber = false;
//                break;
//            case PHONE:
//                isEmail = false;
//                isPhoneNumber = true;
//                break;
//        }
//
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(context)
//                    .enableAutoManage(appCompatActivity, new GoogleApiClient.OnConnectionFailedListener() {
//                        @Override
//                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                        }
//                    })
//                    .addApi(Auth.CREDENTIALS_API)
//                    .build();
//        }
//
//        HintRequest hintRequest = new HintRequest.Builder()
//                .setHintPickerConfig(new CredentialPickerConfig.Builder()
//                        .setShowCancelButton(true)
//                        .build())
//                .setEmailAddressIdentifierSupported(isEmail)
//                .setPhoneNumberIdentifierSupported(isPhoneNumber)
//                .build();
//
//        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
//        try {
//            appCompatActivity.startIntentSenderForResult(intent.getIntentSender(), RC_HINT, null, 0, 0, 0);
//        } catch (Exception e) {
//            LogUtil.debug("PHONE_HINT", "Could not start hint picker Intent", e);
//        }
//
//    }

//    public static void requestEmail()

    //this method return 00:00 format string
    public static String twoDecimalFormat(double value, Boolean attachLkrPrefix) {
        if (attachLkrPrefix)
            return "LKR " + twoDecimalFormat.format(value);
        return twoDecimalFormat.format(value);
    }

    public static String GetExtraFromBundle(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString(code);
        }
        return "";
    }

    public static Double GetExtraFromBundleDouble(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getDouble(code);
        }
        return 0.0;
    }

    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";

    private static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String checkDoubleValueSameToInt(double value) {
        String onesPerString = currencyFormat.format(value) + "";

        if (value == ((int) value)) {
            onesPerString = ((int) value) + "";
        }
        return onesPerString;
    }

    /**
     * Set Adapter
     *
     * @param context      represents the context of the view
     * @param recyclerView the recycler view to display the data
     * @param adapter      represents the adapter for the specific recycler view
     * @param orientation  Orientation for the recycler view, which could be Horizontal or Vertical
     */
    public static void setAdapter(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
        recyclerView.setLayoutManager(getLayoutManager(context, orientation));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
    }

    public static String twoDecimalFormat(int value) {
        return twoDecimalFormat.format(value);
    }


    public static void showPermissionRequiredAlert(final Context context, String permission,
                                                   final String[] requestPermissions, final PermissionManager manager) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.alertTitle))
                .setMessage(context.getString(R.string.alertMessage) + permission)
                .setPositiveButton(context.getString(R.string.alertAllowBtn), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        manager.checkPermissions(requestPermissions);
                    }
                })
                .setNegativeButton(context.getString(R.string.alertDenyBtn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        errorToast(context, context.getString(R.string.alertDenyMessage));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private static void successToast(Context context, String message) {
        Toasty.success(context, message, Toasty.LENGTH_LONG).show();
    }

    private static void infoToast(Context context, String message) {
        Toasty.info(context, message, Toasty.LENGTH_LONG).show();
    }

    private static void warningToast(Context context, String message) {
        Toasty.warning(context, message, Toasty.LENGTH_LONG).show();
    }

    private static void errorToast(Context context, String message) {
        Toasty.error(context, message, Toasty.LENGTH_LONG).show();
    }

    private static void errorToast(Context context, String message, Exception e) {
        Toasty.error(context, message, Toasty.LENGTH_LONG).show();
        e.printStackTrace();
    }

    private static void errorToast(Context context, Exception e) {
        Toasty.error(context, e.getMessage(), Toasty.LENGTH_LONG).show();
        e.printStackTrace();
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }


    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }

    //intent actions
    public static void sendToDialler(Context context, String contactNo) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contactNo));
        context.startActivity(intent);
    }


    public static void sendToGoogleMap(Context context, LatLng latLng) {
        String uri = "http://maps.google.com/maps?q=loc:" + latLng.latitude + "," + latLng.longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);
    }

    public static boolean openInFacebookApp(Context context, String fbId, boolean isPage) {
        boolean isInstalledFb = false;
        Intent intent = null;
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            isInstalledFb = true;
        } catch (Exception e) {
            isInstalledFb = false;
        }
        if (isInstalledFb) {
            if (isPage) {
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + fbId));
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + fbId));
                }
            } else {
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + fbId));
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + fbId));
                }
            }
            context.startActivity(intent);
        }
        return isInstalledFb;
    }


    public static void sendToGmailApp(Context context, String receiver, String subject, String body) {
        String uriText =
                "mailto:" + receiver +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(body);

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        context.startActivity(Intent.createChooser(sendIntent, "Send email"));
    }


    public static void shareText(Context context, String text, String subject) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = text;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void openInBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void startActivityFinish(Class<?> cls, Activity mActivity) {
        Intent i = new Intent(mActivity, cls);
        mActivity.finish();
        i.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(i);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public static void startActivityLastFinish(Class<?> cls, Context mContext, Activity mActivity) {
        Intent i = new Intent(mContext, cls);
        mContext.startActivity(i);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public static void startActivityFinishAll(Class<?> cls, Activity mActivity) {
        Intent i = new Intent(mActivity, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        i.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mActivity.startActivity(i);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivityFinish(Intent intent, Context mContext, Activity mActivity) {
        intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivityExtra(Intent intent, Activity mActivity) {
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivityExtraFinish(Intent intent, Activity mActivity) {
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivity(Class<?> cls, Activity activity) {
        Intent i = new Intent(activity, cls);
        i.setFlags(FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivity(Intent i, Activity activity) {

        i.setFlags(FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static String getExtraFromBundle(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString(code);
        }
        return "";
    }

    public static int getExtraIntFromBundle(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt(code);
        }
        return 0;
    }

    public static Object getExtraFromBundle(String code, Class clz, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            new Gson().fromJson(bundle.getString(code), clz);
        }
        return null;
    }

    public static Parcelable getParcelableExtra(String code, Activity mActivity) {
        Intent intent = mActivity.getIntent();
        return intent.getParcelableExtra(code);
    }

    public static Double getExtraFromBundleDouble(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getDouble(code);
        }
        return 0.0;
    }

    public static int getExtraFromBundleInt(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getInt(code);
        }
        return 0;
    }

    public static boolean getExtraFromBundleBool(String code, Activity mActivity) {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            return bundle.getBoolean(code);
        }
        return false;
    }

    public static void finishAndStartActivity(Activity activity, Class clz) {
        activity.startActivity(new Intent(activity, clz));
        activity.finish();
    }

    public static void finishAndStartActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startActivity(Activity activity, Class clz) {
        activity.startActivity(new Intent(activity, clz));
    }

    public static Object getBundleObject(Bundle bundle, Class clz, String keyword) {
        if (bundle != null) {
            if (ExtenstionMethods.isNotEmptyString(bundle.getString(keyword))) {
                return new Gson().fromJson(bundle.getString(keyword), clz);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public static Object stringToObject(Object obj, Class clz) {
        String dataAsString = new Gson().toJson(obj);
        return new Gson().fromJson(dataAsString, clz);
    }


    public static Object stringToObject(String obj, Class clz) {
        return new Gson().fromJson(obj, clz);
    }


    public static String getObjectAsString(Object object) {
        return new Gson().toJson(object);
    }

    public static void redirectToPlayStore(Activity activity, String packageName) {
        final String appPackageName = packageName; // package name of the app
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void redirectToPlayStore(Activity activity) {
        final String appPackageName = activity.getPackageName(); // package name of the app
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static LinearLayoutManager getLayoutManager(Context context, int orientation) {
        return new LinearLayoutManager(context, orientation, false);
    }

//    public static LinearLayoutManagerWrapper getLayoutManager(Context context, int orientation) {
//        return new LinearLayoutManagerWrapper(context, orientation, false);
//    }

    public static RecyclerView.LayoutManager getLayoutManager(Context context, int orientation, int columnCount) {
        return new GridLayoutManager(context, columnCount, orientation, false);
    }

    public enum ToastTypes {INFO, WARNING, ERROR, SUCCESS}



}
