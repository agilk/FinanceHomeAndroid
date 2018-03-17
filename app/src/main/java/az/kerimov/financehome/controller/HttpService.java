package az.kerimov.financehome.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import az.kerimov.financehome.LoginActivity;
import az.kerimov.financehome.TransactionActivity;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpService extends AsyncTask<String, String, String>{

    private String method;
    private String point;
    private Request request;
    private Context view;

    private static final String BASE_URL = "http://kerimov.az:33400/";

    private String getUrl(){
        return BASE_URL+point;
    }

    public HttpService(String method, String point, Request request, Context v) {
        this.method = method;
        this.point = point;
        this.request = request;
        this.view = v;
    }

    @Override
    protected String doInBackground(String... urls) {

        try {
            InputStream instr;
            String res = "";

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse resp = null;

            Gson gson = new Gson();

            StringEntity stringEntity = new StringEntity(gson.toJson(request));

            if (method.equals("post")) {
                HttpPost uri = new HttpPost(getUrl());
                uri.addHeader("content-type", "application/json");
                uri.setEntity(stringEntity);
                resp = client.execute(uri);
            }else if (method.equals("put")) {
                HttpPut uri = new HttpPut(getUrl());
                uri.addHeader("content-type", "application/json");
                uri.setEntity(stringEntity);
                resp = client.execute(uri);
            }
            instr = resp.getEntity().getContent();

            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(instr));
            String line = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }

            return res;

        } catch (Exception e) {
            return e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (view instanceof LoginActivity){
            LoginActivity activity = (LoginActivity)view;
            activity.setHttpResult(result);
        }else
        if (view instanceof TransactionActivity){
            TransactionActivity activity = (TransactionActivity)view;
            activity.setHttpResult(result, point);
        }
    }
}
