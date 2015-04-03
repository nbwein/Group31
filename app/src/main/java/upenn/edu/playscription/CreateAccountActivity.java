package upenn.edu.playscription;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.*;


public class CreateAccountActivity extends ActionBarActivity {
    // UI references.
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "8SD5YOQ9WTDsThTnsG5vRaaZptHqpcYdz6tLelQp", "HhBMNz9uLujfeLtnAxrnrJ9sa9KMgvqmDq7w664l");

        // Set up the signup form.
        usernameEditText = (EditText) findViewById(R.id.username);

        passwordEditText = (EditText) findViewById(R.id.password);
        passwordAgainEditText = (EditText) findViewById(R.id.password2);
        passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.password2 ||
                        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    createAccount();
                    return true;
                }
                return false;
            }
        });

        // Set up the submit button click handler
        Button mActionButton = (Button) findViewById(R.id.action_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();

        // Validate the sign up data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder("Please ");
        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append("enter a username");
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(" and ");
            }
            validationError = true;
            validationErrorMessage.append(" enter a password");
        }
        if (!password.equals(passwordAgain)) {
            if (validationError) {
                validationErrorMessage.append(" and ");
            }
            validationError = true;
            validationErrorMessage.append("enter the same password");
        }
        validationErrorMessage.append(".");

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(CreateAccountActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
//        final ProgressDialog dialog = new ProgressDialog(CreateAccountActivity.this);
//        dialog.setMessage(getString(R.string.progress_signup));
//        dialog.show();

        // Set up a new Parse user
        ParseObject user = new ParseObject("User");
        user.put("username", username);
        user.put("password", password);
        user.saveInBackground();
        Toast.makeText(CreateAccountActivity.this, "Account created", Toast.LENGTH_LONG).show();
//        user.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                dialog.dismiss();
//                if (e != null) {
//                    // Show the error message
//                    Toast.makeText(CreateAccountActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                } else {
//                    // Start an intent for the dispatch activity
//                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
//        });

        // Call the Parse signup method
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                dialog.dismiss();
//                if (e != null) {
//                    // Show the error message
//                    Toast.makeText(CreateAccountActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                } else {
//                    // Start an intent for the dispatch activity
////                    Intent intent = new Intent(CreateAccountActivity.this, DashboardActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                    startActivity(intent);
//                }
//            }
//        });
    }
}
