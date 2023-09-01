package com.codezync.boilerplate;

import java.util.Locale;

public class AppConfigurations {

    public static boolean isRELEASE = false;
    private static Locale DEFAULT_LOCAL_FOR_PLACE_AUTO_COMPLETE = Locale.ENGLISH;

    public static Locale getDefaultLocalForPlaceAutoComplete() {
        return DEFAULT_LOCAL_FOR_PLACE_AUTO_COMPLETE;
    }

    public static void setDefaultLocalForPlaceAutoComplete(Locale defaultLocalForPlaceAutoComplete) {
        DEFAULT_LOCAL_FOR_PLACE_AUTO_COMPLETE = defaultLocalForPlaceAutoComplete;
    }
}
