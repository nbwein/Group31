package upenn.edu.playscription;

import android.content.Intent;
import android.graphics.Color;
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

import java.text.*;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

import com.parse.*;

public class StatsActivity extends ActionBarActivity {

    private String username;
    private Date currDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_layout);

        final Context context = this;
        username = getIntent().getStringExtra("USERNAME");

        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Double> weights = new ArrayList<>();

        try {
            ParseQuery<ParseObject> users = ParseQuery.getQuery("User");
            users.whereEqualTo("username", username);
            JSONArray temp1 = users.getFirst().fetch().getJSONArray("Dates");
            for (int i = 0; i < temp1.length(); i++) {
                dates.add(temp1.getString(i));
            }

            JSONArray temp2 = users.getFirst().fetch().getJSONArray("Weights");
            for (int i = 0; i < temp2.length(); i++) {
                weights.add(temp2.getDouble(i));
            }
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(context, "Database error", Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }

        DataPoint[] points = new DataPoint[dates.size()];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // populate data points array
        try {
            for (int i = 0; i < dates.size(); i++) {
                Date date = formatter.parse(dates.get(i));
                points[i] = new DataPoint(date, weights.get(i));
            }
        }
        catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        // add points to graph
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        GraphView lineGraph = (GraphView) findViewById(R.id.lineGraph);
        lineGraph.addSeries(series);

        // set date label formatter
        lineGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        lineGraph.getGridLabelRenderer().setNumHorizontalLabels(3);

        ArrayList<String> activities = new ArrayList<>();
        ArrayList<Integer> durations = new ArrayList<>();

        try {
            ParseQuery<ParseObject> users = ParseQuery.getQuery("Activity");
            users.whereEqualTo("username", username);
            JSONArray temp1 = users.getFirst().fetch().getJSONArray("ActivityType");
            for (int i = 0; i < temp1.length(); i++) {
                activities.add(temp1.getString(i));
                Log.v("StatsActivity", temp1.getString(i));
            }

            JSONArray temp2 = users.getFirst().fetch().getJSONArray("Durations");
            for (int i = 0; i < temp2.length(); i++) {
                durations.add(temp2.getInt(i));
                Log.v("StatsActivity", Integer.toString(temp2.getInt(i)));
            }
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(context, "Database error", Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }

        DataPoint[] barPoints = new DataPoint[durations.size()];

        for (int i = 0; i < durations.size(); i++) {
            barPoints[i] = new DataPoint(i, durations.get(i));
        }

        BarGraphSeries<DataPoint> barSeries = new BarGraphSeries<DataPoint>(barPoints);
        GraphView barGraph = (GraphView) findViewById(R.id.barGraph);
        barGraph.getViewport().setYAxisBoundsManual(true);
        barGraph.getViewport().setMinY(0);
        barGraph.getViewport().setMaxY(barSeries.getHighestValueY() + 20);
        barSeries.setSpacing(50);
        barSeries.setDrawValuesOnTop(true);
        barSeries.setValuesOnTopColor(Color.RED);
        barGraph.setTitle("Duration of Play");

        StaticLabelsFormatter slf = new StaticLabelsFormatter(barGraph);

        String[] labelArr = new String[activities.size()];
        for (int i = 0; i < activities.size(); i++) {
            labelArr[i] = activities.get(i);
        }

        slf.setHorizontalLabels(labelArr);
        barGraph.getGridLabelRenderer().setLabelFormatter(slf);
        barSeries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        barGraph.addSeries(barSeries);
    }
}
