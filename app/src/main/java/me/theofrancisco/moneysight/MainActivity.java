package me.theofrancisco.moneysight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Data>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    View loadingIndicator;
    private ListView listView;
    private DataAdapter adapter;
    private Context appContext;
    private TextView tvEmptyView;
    private boolean isWiFi = false;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataAdapter emptyAdapter;

        super.onCreate(savedInstanceState);
        Log.i("myApp", "[MainActivity.onCreate] start");
        setContentView(R.layout.activity_main);
        appContext = this;

        // Find a reference to the {@link ListView} in the layout
        listView = findViewById(R.id.list);
        tvEmptyView = findViewById(R.id.tvEmptyState);
        loadingIndicator = findViewById(R.id.loading_spinner);
        listView.setEmptyView(tvEmptyView);
        emptyAdapter = new DataAdapter(appContext, new ArrayList<Data>(), 5);
        listView.setAdapter(emptyAdapter);

        //Determine and monitor the connectivity status
        //https://developer.android.com/training/monitoring-device-state/connectivity-monitoring?utm_source=udacity&utm_medium=course&utm_campaign=android_basics#java
        ConnectivityManager cm =
                (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //Determine the type of your internet connection
        if (isConnected) isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        if (isConnected) {
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        } else {
            loadingIndicator.setVisibility(View.GONE);
            tvEmptyView.setText(R.string.noInternetConnection);
        }

        //+++++++++++PREFERENCE INITIALIZATION BLOCK+++++++++++++++++++++++++
        /*The preferences you create probably define some important behaviors for your app,
         so it's necessary that you initialize the associated SharedPreferences file with default
         values for each Preference when the user first opens your app.

         The first thing you must do is specify a default value for each Preference object in your
         XML file using the android:defaultValue attribute. The value can be any data type that is
         appropriate for the corresponding Preference object
         */

        Log.i("myApp", "Setting default values for my settings...");
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);


        Log.i("myApp", "done setting default values");
        Log.i("myApp", "Setting SharedPreference Listener...");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        Log.i("myApp", "[MainActivity.onCreate] ends");
    }

    @Override
    public DataLoader onCreateLoader(int id, Bundle bundle) {
        Log.i("myApp", "[MainActivity.onCreateLoader] running...");
        return new DataLoader(appContext);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Data>> loader, List<Data> earthquakes) {

        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        tvEmptyView.setText(R.string.noNews);

        //Log.i("myApp", "onLoadFinished running...");
        if (earthquakes != null && !earthquakes.isEmpty()) {
            // Clear the adapter of previous  data
            //adapter.clear();
            adapter = new DataAdapter(appContext, earthquakes, 5);
            listView.setAdapter(adapter);
        } else {
            tvEmptyView.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Data>> loader) {
        Log.i("myApp", "[MainActivity.onLoaderReset]");
        adapter.clear();
    }

    //++++++++++++++++++++++++MENU SECTION+++++++++++++++++++++++++++++++++++
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_filter) {
            Toast.makeText(this, "You have clicked on menu filter", Toast.LENGTH_LONG).show();
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    //+++++++++++++++++++++++MENU SECTIONs END+++++++++++++++++++++++++++++++++

    //++++++++++++++++PREFERENCE BLOCK++++++++++++++++++++++++++++++++++++++++++
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Log.i("myApp", "[MainActivity] SharedPreferenceChanged: " + key);
        String and_value = sharedPreferences.getString(key, "");
        Log.i("myApp", "[MainActivity] SharedPreferenceChanged:" + key + " = " + and_value);


        /*
        if (key.equals(KEY_PREF_SYNC_CONN)) {
            Preference connectionPref = sharedPreferences.getString(key,"");
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
        */
    }

    /*
    https://developer.android.com/guide/topics/ui/settings
    For proper lifecycle management in the activity, we recommend that you register
    and unregister your SharedPreferences.OnSharedPreferenceChangeListener during the
    onResume() and onPause() callbacks, respectively
     */
    @Override
    protected void onResume() {
        super.onResume();
        //sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        Log.i("myApp", "On resume, set SharedPreference Listener...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        //Log.i("myApp","OnPause, unregister SharedPreference Listener...");
    }
}

