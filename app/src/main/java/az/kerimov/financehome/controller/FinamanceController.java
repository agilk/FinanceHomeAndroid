package az.kerimov.financehome.controller;

import android.content.Context;
import az.kerimov.financehome.pojo.Request;

public class FinamanceController {

    public static void makeRequest(Context context, String service, String method, Request request){

        new HttpService(context, service, method, request).execute(service);
    }
}
