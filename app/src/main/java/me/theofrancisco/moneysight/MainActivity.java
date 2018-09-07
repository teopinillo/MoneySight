package me.theofrancisco.moneysight;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Data>> {

    View loadingIndicator;
    private ListView listView;
    private DataAdapter adapter;
    private DataAdapter emptyAdapter;
    private Context appContext;
    private TextView tvEmptyView;
    private boolean isWiFi = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = this;

        //ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
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


    }

    @Override
    public DataLoader onCreateLoader(int id, Bundle bundle) {
        Log.i("myApp", "onCreateLoeader running...");
        return new DataLoader(appContext);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Data>> loader, List<Data> earthquakes) {

        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        tvEmptyView.setText("No news today.");

        Log.i("myApp", "onLoadFinished running...");
        if (earthquakes != null && !earthquakes.isEmpty()) {
            // Clear the adapter of previous earthquake data
            //adapter.clear();
            adapter = new DataAdapter(appContext, earthquakes, 5);
            listView.setAdapter(adapter);
            Log.i("myApp", "Adapter Set.");
        } else {
            tvEmptyView.setVisibility(TextView.VISIBLE);
            Log.i("myApp", "Loader Error.");
            Toast.makeText(appContext, "HTTP Data Request Failed!", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Data>> loader) {
        Log.i("myApp", "Loader Reset.");
        adapter.clear();
    }


}

