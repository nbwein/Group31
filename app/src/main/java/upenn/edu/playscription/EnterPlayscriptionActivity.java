package upenn.edu.playscription;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.*;
import com.parse.*;


public class EnterPlayscriptionActivity extends ActionBarActivity {
    private boolean dailyReminders = false;
    private String curUser;
    private EditText activityEditText;
    private EditText durationEditText;
    private EditText frequencyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_playscription);
        curUser = getIntent().getStringExtra("USERNAME");
        activityEditText = (EditText) findViewById(R.id.activity_type);
        durationEditText = (EditText) findViewById(R.id.duration);
        frequencyEditText = (EditText) findViewById(R.id.frequency);

        Button submit = (Button) findViewById(R.id.submit_playscription);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String activityType = activityEditText.getText().toString().trim();
                String durationString = durationEditText.getText().toString().trim();
                String frequencyString = frequencyEditText.getText().toString().trim();
                int duration = Integer.parseInt(durationString);
                int frequency = Integer.parseInt(frequencyString);
                ParseObject playscription = new ParseObject("Playscription");
                playscription.put("username", curUser);
                playscription.put("activityType", activityType);
                playscription.put("duration", duration);
                playscription.put("frequency", frequency);
                playscription.put("dailyReminder", dailyReminders);
                playscription.saveInBackground();
                Intent intent = new Intent(EnterPlayscriptionActivity.this, DashboardActivity.class);
                intent.putExtra("USERNAME", curUser);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_playscription, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        dailyReminders = checked;
    }
}
