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
    private EditText playscriptionCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_playscription);
        curUser = getIntent().getStringExtra("USERNAME");
        activityEditText = (EditText) findViewById(R.id.activity_type);
        durationEditText = (EditText) findViewById(R.id.duration);
        frequencyEditText = (EditText) findViewById(R.id.frequency);
        playscriptionCodeEditText = (EditText) findViewById(R.id.playscription_code);

        Button submit = (Button) findViewById(R.id.submit_playscription);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String activityType = activityEditText.getText().toString().trim();
                String durationString = durationEditText.getText().toString().trim();
                String frequencyString = frequencyEditText.getText().toString().trim();
                String playscriptionCodeString = playscriptionCodeEditText.getText().toString().trim();
                int duration;
                int frequency;
                if(playscriptionCodeString.length() == 5)
                {
                    String activitySubstring = playscriptionCodeString.substring(0, 2);
                    String durationSubstring = playscriptionCodeString.substring(2, 4);
                    String freqSubstring = playscriptionCodeString.substring(4,5);
                    duration = Integer.parseInt(durationSubstring);
                    frequency = Integer.parseInt(freqSubstring);
                    if(activitySubstring.equals("01"))
                    {
                        activityType = "Running";
                    }
                    else if(activitySubstring.equals("02"))
                    {
                        activityType = "Playing sports";
                    }
                    else if(activitySubstring.equals("03"))
                    {
                        activityType = "Swimming";
                    }
                    else if(activitySubstring.equals("04"))
                    {
                        activityType = "Playing on a playground";
                    }
                    else if(activitySubstring.equals("05"))
                    {
                        activityType = "Playing basketball";
                    }
                    else if(activitySubstring.equals("06"))
                    {
                        activityType = "Playing football";
                    }
                    else if(activitySubstring.equals("07"))
                    {
                        activityType = "Playing soccer";
                    }
                    else if(activitySubstring.equals("08"))
                    {
                        activityType = "Playing baseball";
                    }
                    else if(activitySubstring.equals("09"))
                    {
                        activityType = "Go for a walk";
                    }
                    else if(activitySubstring.equals("10"))
                    {
                        activityType = "Go biking";
                    }
                    else
                    {
                        Toast.makeText(EnterPlayscriptionActivity.this, "Please enter a valid Playscription code", Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                }

                else {
                    if (activityType.equals("") || durationString.equals("") || frequencyString.equals("")) {
                        Toast.makeText(EnterPlayscriptionActivity.this, "Please fill in all fields.", Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    try {
                        duration = Integer.parseInt(durationString);

                    } catch (Exception e) {
                        Toast.makeText(EnterPlayscriptionActivity.this, "Please give a duration number in minutes.", Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    try {
                        frequency = Integer.parseInt(frequencyString);
                    } catch (Exception e) {
                        Toast.makeText(EnterPlayscriptionActivity.this, "Please input frequency as a number of times done per week.", Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                }
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
