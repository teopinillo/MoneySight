package me.theofrancisco.moneysight;

import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentManager fragmentManager;
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        // Display the fragment as the main content.
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
        fragmentManager.executePendingTransactions();

    }

    public static class MyPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            Log.i("myApp", "[MyPreferenceFragment Setting OnPreferenceChangeListener]...");
            Preference and_value = findPreference(getString(R.string.edit_and_preference_key));
            and_value.setOnPreferenceChangeListener(this);
            Preference or_value = findPreference(getString(R.string.edit_or_preference_key));
            or_value.setOnPreferenceChangeListener(this);
            Preference not_value = findPreference(getString(R.string.edit_or_preference_key));
            not_value.setOnPreferenceChangeListener(this);
            Log.i("myApp", "[MyPreferenceFragment Setting OnPreferenceChangeListener] done");

        }

        @Override
        //preference: the changed preference
        //newValue: Object: the new value of the Preference
        //true to update the state of the Preference with the new value
        public boolean onPreferenceChange(Preference preference, Object value) {
            // The code in this method takes care of updating the displayed
            // preference summary after it has been changed
            String stringValue = value.toString();
            Log.i("myApp", "[MyPreferenceFragment OnPreferenceChangeListener] Preference Changed: " + stringValue);
            preference.setSummary(stringValue);
            return true;
        }

        //private void bindPreferenceSummaryToValue(Preference preference){
        //preference.setOnPreferenceChangeListener(this);

        //String preferenceString = preferences.getString(preference.getKey(),"");
        //onPreferenceChange(preference, preferenceString);
        //}


    }


}
