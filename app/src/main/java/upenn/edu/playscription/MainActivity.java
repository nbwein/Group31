package upenn.edu.playscription;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable local datastore and initialize Parse.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        final Context context = this;

        // Create account button starts a new CreateAccountActivity in order to 
        // create a new account.
        Button createAccountButton = (Button) findViewById(R.id.button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CreateAccountActivity.class);
                startActivity(i);
            }
        });

        // Login button checks the username and password the user entered into 
        // the EditTexts.
        // If they are both valid, a new DashboardActivity is created and passed 
        // the user's username.
        // Else, an error message is given.
        Button loginButton = (Button) findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passwordEditText = (EditText) findViewById(R.id.editText);
                EditText usernameEditText = (EditText) findViewById(R.id.editText2);
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                ParseQuery<ParseObject> users = ParseQuery.getQuery("User");
                users.whereEqualTo("username", username);
                try {
                    // If password and username are in database, go to DashboardActivity.
                    if (users.count() == 1 && users.getFirst().get("password").equals(password)) {
                        Intent i = new Intent(context, DashboardActivity.class);
                        i.putExtra("USERNAME", username);
                        startActivity(i);
                    }
                    // Else, give user an error message
                    else {
                        Toast toast = Toast.makeText(context, R.string.login_error, Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (ParseException e) {
                    Toast toast = Toast.makeText(context, "Database error", Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
            }
        });
    }
}
