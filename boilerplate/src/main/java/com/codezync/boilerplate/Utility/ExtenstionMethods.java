package com.codezync.boilerplate.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;

import com.codezync.boilerplate.R;
import com.google.android.material.tabs.TabLayout;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtenstionMethods {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static Matcher matcher;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static PreferenceManager preferenceManager;

    //Method to get String from Resource
    public static String getStringR(Context context, int value) {
        String mystring = context.getResources().getString(value);
        return mystring;
    }

    public static void shareText(Context context, String subject, String content) {
        String shareBody = content;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_using)));
    }

    //Method to check if string is empty
    public static boolean isNotEmptyString(String value) {
        if (value != null && !value.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    //Method to convert string to int
    public static int toInt(String number) {
        int value = 0;
        if (isNotEmptyString(number)) {
            value = Integer.parseInt(number);
        }
        return value;
    }

    //Method to convert float to int
    public static int toInt(float number) {
        return Math.round(number);
    }

    //Method to convert string to int
    public static String toString(int number) {
        String value = "";
        if (number > 0) {
            value = String.valueOf(number);
        }
        return value;
    }

    public static String toString(double number) {
        String value = "";
        if (number > 0) {
            value = String.valueOf(number);
        }
        return value;
    }

    //Method to validate EditText
    public static boolean validateField(EditText editText, String msg) {
        if (editText.getText().toString().length() == 0) {
            editText.setError(msg);
            editText.requestFocus();

            return false;
        }
        return true;
    }

    public static boolean validateField(EditText editText, TextView textView, String msg) {
        if (editText.getText().toString().length() == 0) {
            textView.setText(msg);
            textView.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }






    //Method to Validate an Email
    public static boolean validateEmail(EditText editText, String msg) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(msg);
            editText.requestFocus();
            return false;
        } else if (!validateEmailPattern(editText.getText().toString())) {
            editText.setError("Invalid email address");
            return false;
        }
        return true;
    }


    public static String checkDoubleValueSameToInt(double value) {
        String onesPerString = value + "";

        if (value == ((int) value)) {
            onesPerString = ((int) value) + "";
        }
        return onesPerString;
    }

    //Method that checks email pattern
    private static boolean validateEmailPattern(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Method That caps the first letter of a string
    public static String capitalizeFirst(String text) {
        String cap = text.substring(0, 1).toUpperCase() + text.substring(1);
        return cap;
    }


    public static String getUTCdatetimeAsString() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
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

    public static String getFirstNStrings(String str, int n) {
        if (isNotEmptyString(str)) {
            if (str.contains(" ")) {
                String[] sArr = str.split(" ");
                String firstStrs = "";

                if (sArr.length < n) {
                    n = sArr.length;
                }
                for (int i = 0; i < n; i++)
                    firstStrs += sArr[i] + " ";
                return firstStrs.trim();
            } else {
                return str;
            }
        } else {
            return " ";
        }
    }

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getSimpleDate(String date) {
        String simpleDate = "";
        try {
            Date current = sdf.parse(date);
            simpleDate = sdf.format(current);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return simpleDate;


    }

    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat sdf4 = new SimpleDateFormat("dd MMM yyyy");
    private final static SimpleDateFormat sdf5 = new SimpleDateFormat("dd MMM yyyy | HH:mm a");

    public static String getSimpleDateTime(String date) {

        String simpleDate = "";
        try {
            Date current = sdf2.parse(date);
            simpleDate = sdf3.format(current);
            LogUtil.debug("Date ", simpleDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return simpleDate;


    }


    public static String getSimpleDateTimeAsMMMFormat(String date) {

        String simpleDate = "";
        try {
            Date current = sdf2.parse(date);
            simpleDate = sdf4.format(current);
            LogUtil.debug("Date ", simpleDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return simpleDate;


    }

    public static String geStringtDateFormatted(String date, String inputFormat, String outputFormat) {

        String day = "";
        if (date != null) {
            try {
                Date date1 = new SimpleDateFormat(inputFormat).parse(date);
                day = new SimpleDateFormat(outputFormat).format(date1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return day;
    }


    public static String getSimpleDateTime(Date date) {
        return sdf2.format(date);
    }


    private final static DecimalFormat df = new DecimalFormat("#0.00");

    public static String getDecimal(double amount) {
        return df.format(amount);
    }

    public static String getDecimal(int amount) {
        return df.format(amount);
    }

    public static void setLanguage(boolean isEnglish, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isEnglish) {
                Configuration configuration = context.getResources().getConfiguration();
                configuration.setLayoutDirection(new Locale("en"));
                configuration.setLocale(new Locale("en"));
                context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

            } else {
                Configuration configuration = context.getResources().getConfiguration();
                configuration.setLayoutDirection(new Locale("ar"));
                configuration.setLocale(new Locale("ar"));
                context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

            }
        }
    }





   public static String removeSpace(String str) {
       return str.replaceAll("[\\s|\\u00A0]+", " ");
    }

    public static void setTabBG(int selectedUI, int unselectedUI, TabLayout tabLayout, int selected) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                View tabView = tabStrip.getChildAt(i);
                if (tabView != null) {
                    if (selected == i) {
                        int paddingStart = tabView.getPaddingStart();
                        int paddingTop = tabView.getPaddingTop();
                        int paddingEnd = tabView.getPaddingEnd();
                        int paddingBottom = tabView.getPaddingBottom();
                        ViewCompat.setBackground(tabView, AppCompatResources.getDrawable(tabView.getContext(), selectedUI));
                        ViewCompat.setPaddingRelative(tabView, paddingStart, paddingTop, paddingEnd, paddingBottom);
                    } else {
                        int paddingStart = tabView.getPaddingStart();
                        int paddingTop = tabView.getPaddingTop();
                        int paddingEnd = tabView.getPaddingEnd();
                        int paddingBottom = tabView.getPaddingBottom();
                        ViewCompat.setBackground(tabView, AppCompatResources.getDrawable(tabView.getContext(), unselectedUI));
                        ViewCompat.setPaddingRelative(tabView, paddingStart, paddingTop, paddingEnd, paddingBottom);
                    }
                }
            }

        }
    }


    public static void setTabBG(int selected, int unselected, TabLayout tabLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
            View tabView1 = tabStrip.getChildAt(0);
            View tabView2 = tabStrip.getChildAt(1);
            if (tabView1 != null) {
                int paddingStart = tabView1.getPaddingStart();
                int paddingTop = tabView1.getPaddingTop();
                int paddingEnd = tabView1.getPaddingEnd();
                int paddingBottom = tabView1.getPaddingBottom();
                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), selected));
                ViewCompat.setPaddingRelative(tabView1, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }

            if (tabView2 != null) {
                int paddingStart = tabView2.getPaddingStart();
                int paddingTop = tabView2.getPaddingTop();
                int paddingEnd = tabView2.getPaddingEnd();
                int paddingBottom = tabView2.getPaddingBottom();
                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), unselected));
                ViewCompat.setPaddingRelative(tabView2, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }
        }
    }


//    public static void setTabBG(int selected, int unselected, TabLayout tabLayout) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
//            View tabView1 = tabStrip.getChildAt(0);
//            View tabView2 = tabStrip.getChildAt(1);
//            if (tabView1 != null) {
//                int paddingStart = tabView1.getPaddingStart();
//                int paddingTop = tabView1.getPaddingTop();
//                int paddingEnd = tabView1.getPaddingEnd();
//                int paddingBottom = tabView1.getPaddingBottom();
//                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), selected));
//                ViewCompat.setPaddingRelative(tabView1, paddingStart, paddingTop, paddingEnd, paddingBottom);
//            }
//
//            if (tabView2 != null) {
//                int paddingStart = tabView2.getPaddingStart();
//                int paddingTop = tabView2.getPaddingTop();
//                int paddingEnd = tabView2.getPaddingEnd();
//                int paddingBottom = tabView2.getPaddingBottom();
//                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), unselected));
//                ViewCompat.setPaddingRelative(tabView2, paddingStart, paddingTop, paddingEnd, paddingBottom);
//            }
//        }
//    }
//
//    public static void setTabBG(int selectedUI, int unselectedUI, TabLayout tabLayout, int selected) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
//
//            for (int i = 0; i < tabLayout.getTabCount(); i++) {
//                View tabView = tabStrip.getChildAt(i);
//                if (tabView != null) {
//                    if (selected == i) {
//                        int paddingStart = tabView.getPaddingStart();
//                        int paddingTop = tabView.getPaddingTop();
//                        int paddingEnd = tabView.getPaddingEnd();
//                        int paddingBottom = tabView.getPaddingBottom();
//                        ViewCompat.setBackground(tabView, AppCompatResources.getDrawable(tabView.getContext(), selectedUI));
//                        ViewCompat.setPaddingRelative(tabView, paddingStart, paddingTop, paddingEnd, paddingBottom);
//                    } else {
//                        int paddingStart = tabView.getPaddingStart();
//                        int paddingTop = tabView.getPaddingTop();
//                        int paddingEnd = tabView.getPaddingEnd();
//                        int paddingBottom = tabView.getPaddingBottom();
//                        ViewCompat.setBackground(tabView, AppCompatResources.getDrawable(tabView.getContext(), unselectedUI));
//                        ViewCompat.setPaddingRelative(tabView, paddingStart, paddingTop, paddingEnd, paddingBottom);
//                    }
//                }
//            }
//
//        }
//    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

//    public static void shareText(Context context, String subject, String content) {
//        String shareBody = content;
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_using)));
//    }

    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public static float getFloat(int value) {
        return Float.valueOf(value);
    }
}
