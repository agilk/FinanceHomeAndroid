package az.kerimov.financehome.controller;

import android.content.Context;
import android.widget.TextView;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class FinamanceController {

    public static void makeRequest(Context context, String service, String method, Request request){

        new HttpService(method, service, request, context).execute(service);
    }
}
