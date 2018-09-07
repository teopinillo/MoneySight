package me.theofrancisco.moneysight;

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class DataLoader extends AsyncTaskLoader<List<Data>> {
    private Context context;
    private String theGuardianApiKey;
    private String url;

    public DataLoader(Context context) {
        super(context);
        this.context = context;
        theGuardianApiKey = context.getString(R.string.THE_GUARDIAN_API_TOKEN);
        url = "http://content.guardianapis.com/search?q=money&api-key=" + theGuardianApiKey + "&show-fields=thumbnail";
    }

    @Override
    protected void onStartLoading() {
        Log.i("myApp", "onStartingLoading Called ...");
        forceLoad();
    }

    public List<Data> loadInBackground() {
        if (url == null) return null;
        HttpHandler httpHandler = new HttpHandler();
        // Making a request to url and getting response
        Log.i("myApp", "url: " + url.substring(0, 30));
        String jsonStr = httpHandler.makeServiceCall(url);
        if (jsonStr != null) {
            Log.i("myApp", "jsonStr: " + jsonStr.substring(0, 30));
            return QueryUtils.extractData(jsonStr, context, R.drawable.ic_launcher_foreground);
        } else {
            Log.e("myApp", "httpHndler returned a null json String");
            return null;
        }
    }


}
