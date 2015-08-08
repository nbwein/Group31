package upenn.edu.playscription;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import java.util.*;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.Context;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ViewMessagesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);
        final String username = getIntent().getStringExtra("USERNAME");

        // Initialize Parse
        Parse.initialize(this, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        final Context context = this;

        // Find messages from database relating to patient, and sort them
        ParseQuery<ParseObject> messages = ParseQuery.getQuery("Message");
        messages.whereEqualTo("Patient", username);
        messages.orderByDescending("createdAt");
        List<ParseObject> results = new ArrayList<ParseObject>();
        try {
            results = messages.find();
        } catch (ParseException e) {
            Toast toast = Toast.makeText(context, "Database error", 
                Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
        
        // Find the patient's doctor
        String theDoctor = "";
        ParseQuery<ParseObject> users = ParseQuery.getQuery("User");
        users.whereEqualTo("username", username);
        try {
            theDoctor = users.getFirst().getString("Doctor");
        } catch (ParseException e) {
            theDoctor = "couldn't find doctor";
            Toast toast = Toast.makeText(context, "Database error", 
                Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
        final String doctor = theDoctor;
        
        // Sends the patient's message to the doctor when pressed
        Button sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText1);
                ParseObject message = new ParseObject("Message");
                message.put("Patient", username);
                message.put("Doctor", doctor);
                message.put("To", doctor);
                message.put("From", username);
                message.put("Text", editText.getText().toString());                
                message.saveInBackground();
                editText.setText("");
                recreate();
            }
        });
        
        // List messages between doctor and patient in order from newest to oldest
        ScrollView scroll = (ScrollView) findViewById(R.id.scrollView1);
        LinearLayout scrollLayout = (LinearLayout) scroll.getChildAt(0);
        List<TextView> messageList = new ArrayList<TextView>();
        for (ParseObject result : results) {
            TextView message = new TextView(this);
            message.setBackgroundResource(R.drawable.playscription_start_screen_button);
            message.setText("From: " + result.getString("From") + "\n" + "To: " + 
                              result.getString("To") + "\n" + "Date: " + 
                              result.getCreatedAt() + "\n\n" + result.getString("Text")
                              + "\n");
            scrollLayout.addView(message);
            messageList.add(message);
        }
    }
}