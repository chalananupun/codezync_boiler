package com.codezync.boilerplate.GoogleLocation;

import android.graphics.Color;
import android.os.AsyncTask;

import com.codezync.boilerplate.Listeners.TaskCompleteListeners;
import com.codezync.boilerplate.Utility.LogUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vishal on 10/20/2018.
 */

public class PointsParser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    TaskCompleteListeners taskCallback;
    String directionMode = "driving";

    public PointsParser(TaskCompleteListeners googleDirectionService, String directionMode) {
        this.taskCallback = (TaskCompleteListeners) googleDirectionService;
        this.directionMode = directionMode;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
        try {
            jObject = new JSONObject(jsonData[0]);
            LogUtil.debug("mylog", jsonData[0].toString());
            DataParser parser = new DataParser();
            LogUtil.debug("mylog", parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            LogUtil.debug("mylog", "Executing routes");
            LogUtil.debug("mylog", routes.toString());

        } catch (Exception e) {
            LogUtil.debug("mylog", e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();
            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);
            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            if (directionMode.equalsIgnoreCase("walking")) {
                lineOptions.width(15);
                lineOptions.color(Color.rgb(57,73,171));
            } else {
                lineOptions.width(15);
                lineOptions.color(Color.rgb(57,73,171));
            }
            LogUtil.debug("mylog", "onPostExecute lineoptions decoded");
        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null) {
            taskCallback.onTaskCompleted(points);
        } else {
            LogUtil.debug("mylog", "without Polylines drawn");
        }
    }
}