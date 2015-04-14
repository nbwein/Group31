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

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "8SD5YOQ9WTDsThTnsG5vRaaZptHqpcYdz6tLelQp", "HhBMNz9uLujfeLtnAxrnrJ9sa9KMgvqmDq7w664l");

        final Context context = this;

        Button createAccountButton = (Button) findViewById(R.id.button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CreateAccountActivity.class);
                startActivity(i);
            }
        });

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
//                        Toast toast = Toast.makeText(context, "It worked!", Toast.LENGTH_LONG);
//                        toast.show();
                        Intent i = new Intent(context, DashboardActivity.class);
                        i.putExtra("Username:", username);
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
/*
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
*/
}
