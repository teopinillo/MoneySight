package me.theofrancisco.moneysight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of Data that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Data> extractData(String jsonString, Context context, int defaultImg) {

        // Create an empty ArrayList that we can start adding data to
        ArrayList<Data> data = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject root = new JSONObject(jsonString);
            JSONObject response = root.getJSONObject("response");

            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject news = results.getJSONObject(i);
                String sectionId = news.getString("sectionId");
                String sectionName = news.getString("sectionName");
                String webPublicationDate = news.getString("webPublicationDate");
                webPublicationDate = webPublicationDate.substring(0, 10) + " " +
                        webPublicationDate.substring(11, 16);
                String webTitle = news.getString("webTitle");
                String webUrl = news.getString("webUrl");
                String pillarName = news.getString("pillarName");
                JSONObject fields = news.getJSONObject("fields");
                String thumbnail = fields.getString("thumbnail");
                Bitmap bitmap = getBitmapFromURL(thumbnail, context, defaultImg);
                String author = "";
                //finding the author

                JSONArray tags = news.getJSONArray("tags");
                if (tags.length() > 0) {
                    JSONObject authorOb = tags.getJSONObject(0);
                    String firstName = authorOb.getString("firstName");
                    String lastName = authorOb.getString("lastName");
                    author = firstName + " " + lastName;
                }

                data.add(new Data(sectionId, sectionName, webPublicationDate, webTitle, webUrl, pillarName, thumbnail, bitmap, author));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("myApp", "Problem parsing the T JSON results", e);
        }

        // Return the list of data
        return data;
    }

    //exmaple of use
    /*
    ImageView iv = (ImageView) findViewById (R.id.myImageView);
    Bitmap bitmap = dataLoader.getBitmapFromUrl ( myUrl, context, R.drawable.ic_launcher_foreground );
    iv.setImageBitmap (bitmap);
     */
    private static Bitmap getBitmapFromURL(String srcUrl, Context context, int defaultImg) {
        Bitmap image;
        try {
            URL url = new URL(srcUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream stream = httpURLConnection.getInputStream();
            image = BitmapFactory.decodeStream(stream);
            httpURLConnection.disconnect();
        } catch (Exception e) {
            image = BitmapFactory.decodeResource(context.getResources(), defaultImg);
        }
        return image;
    }
}
