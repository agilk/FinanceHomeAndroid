package az.kerimov.financehome.controller;

import android.content.Context;
import android.util.Log;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class FinamanceController {

    private static Response x = new Response();

    public void setX(Response x){
        this.x = x;
    }

    public Response getX() {
        return x;
    }

    public static Response getResponse(Context context, String service, Request request){
        StringEntity jsonEntity = null;
        x = (new Response());
        try {
            jsonEntity = new StringEntity((new Gson()).toJson(request).toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.post( context,service, jsonEntity, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Gson gson = new Gson();
                    Response aaaa = gson.fromJson(response.toString(), Response.class);
                    Log.w("ddddd",aaaa.getData().getSessionKey());
                    x = aaaa;
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                super.onPostProcessResponse(instance, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline){

            }


        });
        return x;
    }
}
