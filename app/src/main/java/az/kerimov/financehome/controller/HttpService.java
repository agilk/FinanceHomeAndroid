package az.kerimov.financehome.controller;

import android.content.Context;
import android.os.AsyncTask;
import az.kerimov.financehome.activity.*;
import az.kerimov.financehome.pojo.Request;
import com.google.gson.Gson;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpService extends AsyncTask<String, String, String> {

    private String method;
    private String point;
    private Request request;
    private Context view;

    private static final String BASE_URL = "http://kerimov.az:33400/";

    private String getUrl() {
        return BASE_URL + point;
    }

    public HttpService(Context v, String point, String method, Request request) {
        this.method = method;
        this.point = point;
        this.request = request;
        this.view = v;
    }

    class HttDelete extends HttpPost
    {
        public HttDelete(String url){
            super(url);
        }
        @Override
        public String getMethod() {
            return "DELETE";
        }
    }

    @Override
    protected String doInBackground(String... urls) {

        try {
            InputStream instr;
            StringBuilder res = new StringBuilder();

            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse resp = null;

            Gson gson = new Gson();

            StringEntity stringEntity = new StringEntity(gson.toJson(request));

            if (method.equals("post")) {
                HttpPost uri = new HttpPost(getUrl());
                uri.addHeader("content-type", "application/json;charset=UTF-8");
                uri.setEntity(stringEntity);
                resp = client.execute(uri);
            } else if (method.equals("put")) {
                HttpPut uri = new HttpPut(getUrl());
                uri.addHeader("content-type", "application/json;charset=UTF-8");
                uri.setEntity(stringEntity);
                resp = client.execute(uri);

            } else if (method.equals("delete")) {
                HttDelete uri = new HttDelete(getUrl());
                uri.addHeader("content-type", "application/json;charset=UTF-8");
                uri.setEntity(stringEntity);
                resp = client.execute(uri);
            }
            assert resp != null;
            instr = resp.getEntity().getContent();

            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(instr));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }

            return res.toString();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (view instanceof LoginActivity) {
            LoginActivity activity = (LoginActivity) view;
            activity.setHttpResult(result);
        } else if (view instanceof TransactionActivity) {
            TransactionActivity activity = (TransactionActivity) view;
            activity.setHttpResult(result, point);
        } else if (view instanceof CurrencySettingsActivity) {
            CurrencySettingsActivity activity = (CurrencySettingsActivity) view;
            activity.setHttpResult(result, point);
        } else if (view instanceof WalletSettingsActivity) {
            WalletSettingsActivity activity = (WalletSettingsActivity) view;
            activity.setHttpResult(result, point);
        } else if (view instanceof RateActivity) {
            RateActivity activity = (RateActivity) view;
            activity.setHttpResult(result, point);
        } else if (view instanceof CategoryActivity) {
            CategoryActivity activity = (CategoryActivity) view;
            activity.setHttpResult(result, point);
        } else if (view instanceof SubCategoryActivity) {
            SubCategoryActivity activity = (SubCategoryActivity) view;
            activity.setHttpResult(result, point);
        }
    }
}
