package az.kerimov.financehome.controller;

import android.content.Context;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final String URL = "http://kerimov.az:33400/";

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");

        client.addHeader("Content-Type", "application/json");
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, StringEntity params, AsyncHttpResponseHandler responseHandler) {

        client.addHeader("Accept", "application/json");

        client.addHeader("Content-Type", "application/json");
        Log.d("adssad","--- "+getAbsoluteUrl(url)+" ---- "+params.toString());
        client.post(context, getAbsoluteUrl(url), params, "application/json", responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");

        client.addHeader("Content-Type", "application/json");
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl){
        return URL+relativeUrl;
    }
}
