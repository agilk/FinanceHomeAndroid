package az.kerimov.financehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.controller.HttpService;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;

import java.util.HashMap;

public class TransactionActivity extends AppCompatActivity {

    private String sessionKey;
    private HashMap<Integer, Integer> spinnerMapCategory;
    private HashMap<Integer, String> spinnerMapSubCategory;
    private HashMap<Integer, String> spinnerMapCurrency;
    private HashMap<Integer, String> spinnerMapWallet;

    //String id = spinnerMap.get(spinner.getSelectedItemPosition());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        sessionKey = message;
        EditText textView = (EditText) findViewById(R.id.edNotes);
        textView.setText(message);

        fillSpinners();

        Spinner categorySpinner = (Spinner) findViewById(R.id.edCategory);

        final HashMap<Integer, Integer> local = new HashMap<>();
        local.putAll(spinnerMapCategory);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //(Spinner) selectedItemView.getSelectedItemPosition()
                Log.w("HHH", "ssds"+id+" --- "+local.get(id));
                fillSubCategory(local.get(id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //categorySpinner.set

    }

    public void fillSpinners(){
        Request request = new Request();
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
        FinamanceController.makeRequest(this, "getCategories", "post", request);
        FinamanceController.makeRequest(this, "getUserWallets", "post", request);
    }

    public void fillSubCategory(Integer categoryId){
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCategoryId(categoryId);

        FinamanceController.makeRequest(this, "getSubCategories", "post", request);
    }

    public void setHttpResult(String result, String point){
        if (point.equals("getCategories")){

            Gson gson = new Gson();
            Response response = new Response();
            try {
                response = gson.fromJson(result, Response.class);

                Spinner spinner = (Spinner) findViewById(R.id.edCategory);

                String[] spinnerArray = new String[response.getData().getCategories().size()];
                spinnerMapCategory = new HashMap<Integer, Integer>();
                for (int i = 0; i < response.getData().getCategories().size(); i++) {
                    spinnerMapCategory.put(i, response.getData().getCategories().get(i).getId());
                    spinnerArray[i] = response.getData().getCategories().get(i).getName();
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }catch (Exception e){

            }
        }else
        if (point.equals("getSubCategories")){

            Gson gson = new Gson();
            Response response = new Response();
            try {
                response = gson.fromJson(result, Response.class);

                Spinner spinner = (Spinner) findViewById(R.id.edSubCategory);

                String[] spinnerArray = new String[response.getData().getSubCategories().size()];
                spinnerMapSubCategory = new HashMap<Integer, String>();
                for (int i = 0; i < response.getData().getSubCategories().size(); i++) {
                    spinnerMapSubCategory.put(i, response.getData().getSubCategories().get(i).getId().toString());
                    spinnerArray[i] = response.getData().getSubCategories().get(i).getName();
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }catch (Exception e){

            }
        }else
        if (point.equals("getUserCurrencies")){

            Gson gson = new Gson();
            Response response = new Response();
            try {
                response = gson.fromJson(result, Response.class);

                Spinner spinner = (Spinner) findViewById(R.id.edCurrency);

                String[] spinnerArray = new String[response.getData().getCurrencies().size()];
                spinnerMapCurrency = new HashMap<Integer, String>();
                for (int i = 0; i < response.getData().getCurrencies().size(); i++) {
                    spinnerMapCurrency.put(i, response.getData().getCurrencies().get(i).getId().toString());
                    spinnerArray[i] = response.getData().getCurrencies().get(i).getCurrency().getShortDescription();
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }catch (Exception e){

            }
        }else
        if (point.equals("getUserWallets")){

            Gson gson = new Gson();
            Response response = new Response();
            try {
                response = gson.fromJson(result, Response.class);

                Spinner spinner = (Spinner) findViewById(R.id.edWallet);

                String[] spinnerArray = new String[response.getData().getWallets().size()];
                spinnerMapWallet = new HashMap<Integer, String>();
                for (int i = 0; i < response.getData().getWallets().size(); i++) {
                    spinnerMapWallet.put(i, response.getData().getWallets().get(i).getId().toString());
                    spinnerArray[i] = response.getData().getWallets().get(i).getCustomName();
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }catch (Exception e){

            }
        }
    }
}
