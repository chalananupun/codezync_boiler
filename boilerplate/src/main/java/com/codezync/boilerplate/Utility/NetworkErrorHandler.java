package com.codezync.boilerplate.Utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;

public class NetworkErrorHandler {
    public static String Extract(ResponseBody errorBody){
        StringBuilder error = new StringBuilder();
        try {
            BufferedReader bufferedReader = null;
            if (errorBody != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(
                        errorBody.byteStream()));

                String eLine = null;
                while ((eLine = bufferedReader.readLine()) != null) {
                    error.append(eLine);
                }
                bufferedReader.close();
            }

        } catch (Exception e) {
            error.append(e.getMessage());
        }

        return error.toString();
    }
}
