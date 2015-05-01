package upenn.edu.playscription;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;

/**
 * Created by Drew on 4/28/2015.
 */
public class LogActivityActivity extends Activity {
    private String curUser;
    private EditText activityEditText;
    private EditText durationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        curUser = getIntent().getStringExtra("USERNAME");
        activityEditText = (EditText) findViewById(R.id.activityTypeEditText);
        durationEditText = (EditText) findViewById(R.id.durationEditText);

        Button submit = (Button) findViewById(R.id.submit_activity);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String activityType = activityEditText.getText().toString().trim();
                String durationString = durationEditText.getText().toString().trim();
                int duration;
                int durationTotal = 0;
                try {
                    duration = Integer.parseInt(durationString);
                } catch (Exception e) {
                    Toast.makeText(LogActivityActivity.this, "Please give a duration number in minutes.", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                ParseQuery<ParseObject> activities = ParseQuery.getQuery("Activity");
                activities.whereEqualTo("username", curUser);

                try {
                    boolean activityExists = false;
                    JSONArray activityStrings = activities.getFirst().fetch().getJSONArray("ActivityType");
                    JSONArray activityInts = activities.getFirst().fetch().getJSONArray("Durations");
                    for (int i = 0; i < activityStrings.length(); i++) {
                        try {
                            durationTotal += activityInts.getInt(i);
                            if (activityStrings.getString(i).equals(activityType)) {
                                activityExists = true;
                            }
                        } catch (JSONException jse) {
                            jse.printStackTrace();
                        }
                    }
                    if (!activityExists) {
                        Toast.makeText(LogActivityActivity.this,
                                "The activity you input does not match your playscription.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    activities.getFirst().fetch().add("ActivityType", activityType);
                    activities.getFirst().fetch().add("Durations", duration);
                    activities.getFirst().saveInBackground();
                }
                catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
//                activity.put("username", curUser);
//                activity.add("ActivityType", activityType);
//                activity.add("Durations", duration);
//                activity.saveInBackground();
                Intent intent = new Intent(LogActivityActivity.this, DashboardActivity.class);
                intent.putExtra("USERNAME", curUser);
                durationTotal += duration;
                intent.putExtra("durationTotal",durationTotal);
                startActivity(intent);
            }
        });

    }
}
