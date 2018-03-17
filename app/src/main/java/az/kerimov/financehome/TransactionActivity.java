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
    private HashMap<Integer, Integer> spinnerMapCategory = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> spinnerMapSubCategory;
    private HashMap<Integer, String>  spinnerMapCurrency;
    private HashMap<Integer, Integer> spinnerMapWallet;
    private HashMap<Integer, Integer> spinnerMapWalletOther;

    //String id = spinnerMap.get(spinner.getSelectedItemPosition());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        sessionKey = message;

        fillSpinners();

        Spinner categorySpinner = (Spinner) findViewById(R.id.edCategory);

        //final HashMap<Integer, Integer> local = new HashMap<>();
        //local.putAll(spinnerMapCategory);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //(Spinner) selectedItemView.getSelectedItemPosition()
                //Log.w("HHH", "ssds = "+id+" --- "+spinnerMapCategory.get(id).toString());
                fillSubCategory((int)id);
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

    public void clickAdd(View view){
        Request request = new Request();

        EditText edAmount = (EditText) findViewById(R.id.edAmount);
        Spinner edCategory = (Spinner) findViewById(R.id.edCategory);
        Spinner edCurrency = (Spinner) findViewById(R.id.edCurrency);
        Spinner edSubCategory = (Spinner) findViewById(R.id.edSubCategory);
        Spinner edWallet = (Spinner) findViewById(R.id.edWallet);
        Spinner edWalletOther = (Spinner) findViewById(R.id.edWalletOther);
        EditText edNotes = (EditText) findViewById(R.id.edNotes);
        EditText edDate = (EditText) findViewById(R.id.edDate);




        request.setAmount(Double.parseDouble(String.valueOf(edAmount.getText())));
        request.setCategoryId(spinnerMapCategory.get(edCategory.getSelectedItemPosition()));
        request.setSessionKey(sessionKey);
        request.setCurrencyCode(spinnerMapCurrency.get(edCurrency.getSelectedItemPosition()).toString());
        request.setSubCategoryId(spinnerMapSubCategory.get(edSubCategory.getSelectedItemPosition()));
        request.setWalletId(spinnerMapWallet.get(edWallet.getSelectedItemPosition()));
        if (edWalletOther.getSelectedItemPosition() > 0){
            request.setWalletIdOther(spinnerMapWalletOther.get(edWalletOther.getSelectedItemPosition()));
        }
        request.setNotes(edNotes.getText().toString());
        request.setDate(edDate.getText().toString()+" 00:00:00");
        FinamanceController.makeRequest(this, "addTransaction", "put", request);
    }

    public void fillSubCategory(Integer categoryId){
        Log.w("HHH", "XX: "+spinnerMapCategory.size());
        Log.w("HHH", "ssds = "+categoryId+" --- "+spinnerMapCategory.get(categoryId).toString());
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCategoryId(spinnerMapCategory.get(categoryId));

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
                spinnerMapCategory.clear();
                //spinnerMapCategory = new HashMap<Integer, Integer>();
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
                spinnerMapSubCategory = new HashMap<Integer, Integer>();
                for (int i = 0; i < response.getData().getSubCategories().size(); i++) {
                    spinnerMapSubCategory.put(i, response.getData().getSubCategories().get(i).getId());
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
                    spinnerMapCurrency.put(i, response.getData().getCurrencies().get(i).getCurrency().getCode().toString());
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
                Spinner spinnerOther = (Spinner) findViewById(R.id.edWalletOther);

                String[] spinnerArray = new String[response.getData().getWallets().size()];
                spinnerMapWallet = new HashMap<Integer, Integer>();
                for (int i = 0; i < response.getData().getWallets().size(); i++) {
                    spinnerMapWallet.put(i, response.getData().getWallets().get(i).getId());
                    spinnerArray[i] = response.getData().getWallets().get(i).getCustomName();
                }

                ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);


                String[] spinnerArrayOther = new String[response.getData().getWallets().size()+1];
                spinnerMapWalletOther = new HashMap<Integer, Integer>();

                spinnerMapWalletOther.put(0, 0);
                spinnerArrayOther[0] = "NONE";

                for (int i = 0; i < response.getData().getWallets().size(); i++) {
                    spinnerMapWalletOther.put(i+1, response.getData().getWallets().get(i).getId());
                    spinnerArrayOther[i+1] = response.getData().getWallets().get(i).getCustomName();
                }

                ArrayAdapter<String> adapterOther =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArrayOther);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                adapterOther.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOther.setAdapter(adapterOther);
            }catch (Exception e){

            }
        }
    }
}
