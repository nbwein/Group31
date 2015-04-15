package upenn.edu.playscription;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import com.parse.*;

public class StatsActivity extends ActionBarActivity {

    private String username;
    private Date currDate;
    private Date[] loggedWeightDates;
    private double[] loggedWeights;
    private HashMap<Date, Double> dateToWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_layout);

        final Context context = this;

        username = getIntent().getStringExtra("USERNAME");

        currDate = new Date();

        ParseQuery<ParseObject> users = ParseQuery.getQuery("User");

        users.whereEqualTo("username", username);
        try {
            dateToWeight = (HashMap) users.getFirst().get("dateToWeight");

            if (dateToWeight == null) {
                dateToWeight = new HashMap<Date, Double>();
            }

        } catch (ParseException e) {
            Toast toast = Toast.makeText(context, "Database error", Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }

//        long firstTime = getFirstTime(dateToWeight);
//        long lastTime = getLastTime(dateToWeight);
//        long range = lastTime - firstTime;

        DataPoint[] points = new DataPoint[dateToWeight.size()];
        int index = 0;
        for (Map.Entry<Date, Double> entry : dateToWeight.entrySet()) {
            DataPoint currPoint = new DataPoint(entry.getKey().getTime(), entry.getValue());
            points[index] = currPoint;
            index++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.addSeries(series);
    }

//    private long getFirstTime(HashMap<Date, Double> map) {
//        long firstTime = (long) Integer.MAX_VALUE;
//
//        for (Map.Entry<Date, Double> entry : map.entrySet()) {
//
//            long time = entry.getKey().getTime();
//            if (time < firstTime) {
//                firstTime = time;
//            }
//        }
//
//        return firstTime;
//    }
//
//    private long getLastTime(HashMap<Date, Double> map) {
//        long lastTime = (long) Integer.MIN_VALUE;
//
//        for (Map.Entry<Date, Double> entry : map.entrySet()) {
//
//            long time = entry.getKey().getTime();
//            if (time > lastTime) {
//                lastTime = time;
//            }
//        }
//
//        return lastTime;
//    }
}
