package az.kerimov.financehome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.common.CommonUtils;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "az.kerimov.financehome.SESSIONKEY";

    //private String httpResult;

    private void setMessage(String str){

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(str);
    }

    public void setHttpResult(String httpResult) {
        //this.httpResult = httpResult;
        Gson gson = new Gson();
        Response response;
        try{
            response = gson.fromJson(httpResult, Response.class);

            if (!response.getData().getSessionKey().isEmpty()){
                setMessage(response.getData().getSessionKey());
            }
            Intent intent = new Intent(this, TransactionActivity.class);
            intent.putExtra(EXTRA_MESSAGE, response.getData().getSessionKey());
            startActivity(intent);


        }catch(Exception e){
            setMessage("Error "+httpResult+"   "+e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        EditText login = (EditText) findViewById(R.id.edLogin);
        EditText password = (EditText) findViewById(R.id.edPassword);

        String passHash = CommonUtils.encryptPassword(login.getText().toString().toLowerCase()+password.getText().toString());

        Request request = new Request();
        request.setLogin(login.getText().toString().toLowerCase());
        request.setPassword(passHash);

        FinamanceController.makeRequest(this, "login", "post", request);

    }
}
