package upenn.edu.playscription;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;


public class UserPersonalInfoActivity extends ActionBarActivity {

    EditText patientName;
    EditText height;
    EditText weight;
    EditText age;
    EditText dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_personal_info);
        patientName = (EditText) findViewById(R.id.editText);
        height = (EditText) findViewById(R.id.editText2);
        weight = (EditText) findViewById(R.id.editText3);
        age = (EditText) findViewById(R.id.editText4);
        dob = (EditText) findViewById(R.id.editText5);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_user_personal_info, menu);
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
}
