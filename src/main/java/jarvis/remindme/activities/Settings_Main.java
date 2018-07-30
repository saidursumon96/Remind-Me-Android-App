package jarvis.remindme.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import jarvis.remindme.R;

public class Settings_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings__main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.app.FragmentManager fragmentManager = getFragmentManager(); //*** Return the FragmentManager for interacting with
                                                                            //              fragments associated with this activity.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //*** Start a series of edit operations on the
                                                                                      //    Fragments associated with this FragmentManager.

        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(android.R.id.content,settingsFragment,"SETTINGS_FRAGMENT"); //*** Add a fragment to the activity state.
        //*** Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
        fragmentTransaction.commit(); //*** commit() writes the data synchronously and informs about the success of the operation.
    }

    public static class SettingsFragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings); //*** Load the preferences from an XML resource.
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
