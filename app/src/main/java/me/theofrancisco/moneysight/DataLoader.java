package me.theofrancisco.moneysight;

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class DataLoader extends AsyncTaskLoader<List<Data>> {
    private Context context;
    private String url;

    DataLoader(Context context) {
        super(context);
        Log.i("myApp", "[DataLoader] called ...");
        this.context = context;
        String theGuardianApiKey = context.getString(R.string.THE_GUARDIAN_API_TOKEN);
        url = "http://content.guardianapis.com/search?q=money&show-tags=contributor&show-fields=thumbnail&api-key=" + theGuardianApiKey;
    }

    @Override
    protected void onStartLoading() {
        Log.i("myApp", "[DataLoader.onStartingLoading] called ...");
        forceLoad();
    }

    private String addFilter(String filter, String value) {
        String result = "";
        if (value != null) {
            result = value.trim();
            //%20AND%20bitcoin
            if (result.length() > 0) result = "%20" + filter + "%20" + value;
        }
        return result;
    }
    public List<Data> loadInBackground() {
        Log.i("myApp", "[DataLoader.loadInBackground] called ...");
        if (url == null) return null;
        //++++++++++++++setting the filters
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String andFilter = sharedPreferences.getString(context.getString(R.string.edit_and_preference_key),
                context.getString(R.string.and_filter_default_value));
        Log.i("Mypp", "[DataLoader.loadInBackground] and filter: " + andFilter);

        String orFilter = sharedPreferences.getString(context.getString(R.string.edit_or_preference_key),
                context.getString(R.string.or_filter_default_value));
        Log.i("Mypp", "[DataLoader.loadInBackground] or filter: " + orFilter);

        String notFilter = sharedPreferences.getString(context.getString(R.string.edit_not_preference_key),
                context.getString(R.string.not_filter_default_value));
        Log.i("Mypp", "[DataLoader.loadInBackground] not filter: " + notFilter);

        String filters = addFilter("AND", andFilter).concat(addFilter("OR", orFilter)).concat(addFilter("NOT", notFilter));
        Log.i("Mypp", "[DataLoader.loadInBackground] filter: " + filters);

        HttpHandler httpHandler = new HttpHandler();
        // Making a request to url and getting response
        Log.i("myApp", "[DataLoader.loadInBackground] url: " + url.substring(0, 30) + "...");
        String jsonStr = httpHandler.makeServiceCall(url);
        if (jsonStr != null) {
            Log.i("myApp", "[DataLoader.loadInBackground] jsonStr: " + jsonStr.substring(0, 30) + "...");
            return QueryUtils.extractData(jsonStr, context, R.drawable.ic_launcher_foreground);
        } else {
            Log.e("myApp", "[DataLoader.loadInBackground] httpHandler returned a null json String");
            return null;
        }
    }


}
