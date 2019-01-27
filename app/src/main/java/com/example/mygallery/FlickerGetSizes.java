package com.example.mygallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlickerGetSizes {
    private static final String TAG = "FlickerGetSizes";
    private static final String API_KEY = "a7442e722134253a45ed5612bfbd6ddd";

    public String getJsonString(String UrlSpec) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlSpec)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        return result;
    }

    public List<PhotoItem> getSizes(String id) {
        List<PhotoItem> PhotoItems = new ArrayList<>();

        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getSizes")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("photo_id", id)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .build().toString();
            String jsonString = getJsonString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(PhotoItems, jsonBody);
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка парсинга JSON", e);
        } catch (IOException e) {
            Log.e(TAG, "Ошибка загрузки данных", e);
        }

        return PhotoItems;
    }

    public void parseItems(List<PhotoItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONObject sizesJsonObject = jsonBody.getJSONObject("sizes");
        JSONArray sizeJsonArray = sizesJsonObject.getJSONArray("size");

        for (int i = 0; i < sizeJsonArray.length(); i++) {
            JSONObject photoJsonObject = sizeJsonArray.getJSONObject(i);
            PhotoItem item = new PhotoItem();
            item.setWidth(photoJsonObject.getString("width"));
            item.setHeight(photoJsonObject.getString("height"));
            item.setUrl(photoJsonObject.getString("source"));
            items.add(item);
        }
    }
}
