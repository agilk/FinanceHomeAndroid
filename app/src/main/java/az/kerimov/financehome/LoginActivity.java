package az.kerimov.financehome;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockContext;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.controller.HttpUtils;
import az.kerimov.financehome.pojo.Data;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText login = (EditText) findViewById(R.id.editText1);
        EditText password = (EditText) findViewById(R.id.editText2);

        Request request = new Request();
        request.setLogin(login.getText().toString());
        request.setPassword(password.getText().toString());

        Log.w("TEST: ", "request");

        FinamanceController finamanceController = new FinamanceController();
        Response response = FinamanceController.getResponse(view.getContext(), "login", request);

        Log.w("TEST: ", "controller -> response");

        //Data data = response.getData();

        Log.w("TEST: ", "response -> data");

        Gson gson = new Gson();


        Log.w("TEST: ", "OBJ:"+gson.toJson(response).toString());
        /*
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("login", login.getText().toString());
            jsonParams.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity jsonEntity = null;
        try {
            jsonEntity = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.post( view.getContext(),"login", jsonEntity, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("asd", "--->"+response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    Gson gson = new Gson();
                    Response x = gson.fromJson(response.toString(), Response.class);
                    Log.d("XX  "," === "+x.getData().getSessionKey());
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline){

            }
        });*/
    }
}
