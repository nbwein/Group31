package upenn.edu.playscription;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseObject;

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
                int duration = Integer.parseInt(durationString);
                ParseObject activity = new ParseObject("Activity");
                activity.put("username", curUser);
                activity.put("ActivityType", activityType);
                activity.put("TimeCompleted", duration);
                activity.saveInBackground();
                Intent intent = new Intent(LogActivityActivity.this, DashboardActivity.class);
                intent.putExtra("USERNAME", curUser);
                startActivity(intent);
            }
        });

    }
}
