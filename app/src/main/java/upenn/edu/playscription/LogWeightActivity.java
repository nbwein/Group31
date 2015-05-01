package upenn.edu.playscription;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

public class LogWeightActivity extends Activity {

    private String username;
    private EditText logWeightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_weight);

        username = getIntent().getStringExtra("USERNAME");
        logWeightEditText = (EditText) findViewById(R.id.logWeightEditText);

        Button submit = (Button) findViewById(R.id.submit_activity);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String weightStr = logWeightEditText.getText().toString().trim();
                double weight;
                try {
                    weight = Double.parseDouble(weightStr);
                } catch (Exception e) {
                    Toast.makeText(LogWeightActivity.this, "Please input weight as a number in pounds.", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                ParseQuery<ParseObject> users = ParseQuery.getQuery("User");
                users.whereEqualTo("username", username);

                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date currDate = new Date();
                    String date = formatter.format(currDate);

                    users.getFirst().fetch().add("Dates", date);
                    users.getFirst().fetch().add("Weights", weight);
                    users.getFirst().saveInBackground();
                }
                catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LogWeightActivity.this, DashboardActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

    }
}
