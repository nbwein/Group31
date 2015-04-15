package upenn.edu.playscription;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_playscription);
        curUser = getIntent().getStringExtra("USERNAME");
        Button submit = (Button) findViewById(R.id.submit_playscription);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("username", curUser);
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
