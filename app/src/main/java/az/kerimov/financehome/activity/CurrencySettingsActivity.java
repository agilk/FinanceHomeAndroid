package az.kerimov.financehome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.CurrencySettingsAdapter;
import az.kerimov.financehome.common.CommonUtils;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Currency;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import az.kerimov.financehome.pojo.UserCurrency;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import static az.kerimov.financehome.common.CommonUtils.fillMap;

public class CurrencySettingsActivity extends AppCompatActivity {

    private static final String EXTRA_SESSION = "az.kerimov.financehome.SESSIONKEY";
    private static final String EXTRA_CURRENCY = "az.kerimov.financehome.CURRENCY";

    private String sessionKey;

    private Context context;

    private ListView listCurrencies;

    private HashMap<Integer, UserCurrency> mapCurrency;
    private HashMap<Integer, Currency> mapSysCurrency;

    private List<Currency> sysCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_settings);
        listCurrencies = (ListView) findViewById(R.id.listCurrencies);
        context = this;

        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        sessionKey = intent.getStringExtra(EXTRA_SESSION);

        Request request = new Request();
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
        FinamanceController.makeRequest(this, "getAllCurrencies", "post", request);
    }


    private void openActivity(Class c, String currencyCode){
        Intent intent = new Intent(this, c);
        intent.putExtra(EXTRA_SESSION, sessionKey);
        intent.putExtra(EXTRA_CURRENCY, currencyCode);
        startActivity(intent);
    }

    public void clickAddCurrency(View view) {
        Request request = new Request();
        request.setSessionKey(sessionKey);
        Spinner spinner = (Spinner) findViewById(R.id.spnCurrencies);
        request.setCurrencyCode(mapSysCurrency.get(spinner.getSelectedItemPosition()).getCode());

        FinamanceController.makeRequest(this, "addCurrency", "put", request);
    }

    private boolean alreadyHasCurrency(Currency currency){

        for (int i = 0; i < mapCurrency.size(); i++){
            try {
                String codeL = currency.getCode();
                String codeR = mapCurrency.get(i).getCurrency().getCode();
                if (codeL.equals(codeR)) {
                    return true;
                }
            }catch (NullPointerException e){
                Log.e("ERROR: NULL: ", currency.toString());
                Log.e("ERROR: NULL: ", mapCurrency.get(i).getCurrency().toString());
            }
        }

        return false;
    }

    private void fillSysCurrencies(){

        int arrSize = 0;
        mapSysCurrency = new HashMap<>();
        for (Currency currency : sysCurrencies) {
            if (!alreadyHasCurrency(currency)) {
                mapSysCurrency.put(arrSize, currency);
                arrSize++;
            }
        }

        String[] spinnerArray = new String[arrSize];
        for (int i = 0; i < arrSize; i++){
            spinnerArray[i] = mapSysCurrency.get(i).getShortDescription();
        }


        Spinner spinner = (Spinner) findViewById(R.id.spnCurrencies);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void setHttpResult(String result, String point) {

        switch (point) {
            case "addCurrency": {

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);
                    if (response.getData().getNewId()!=null) {
                        fillSysCurrencies();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Request request = new Request();
                request.setSessionKey(sessionKey);
                FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
                break;
            }
            case "getAllCurrencies": {

                Gson gson = new Gson();
                Response response ;
                try {
                    response = gson.fromJson(result, Response.class);
                    sysCurrencies = response.getData().getSysCurrencies();
                    fillSysCurrencies();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "getUserCurrencies":
            case "setDefaultCurrency": {


                Gson gson = new Gson();
                Response response ;
                try {
                    response = gson.fromJson(result, Response.class);


                    final List<UserCurrency> currencies = response.getData().getCurrencies();

                    mapCurrency = fillMap(currencies);

                    listCurrencies.setAdapter(new CurrencySettingsAdapter(this, currencies));

                    listCurrencies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView xxx = (TextView) findViewById(R.id.xxx);
                            String text = mapCurrency.get(i).getCurrency().getDescription() + " has been set as your default Currency ";
                            xxx.setText(text);

                            Request request = new Request();
                            request.setSessionKey(sessionKey);
                            request.setCurrencyCode(mapCurrency.get(i).getCurrency().getCode());

                            FinamanceController.makeRequest(context, "setDefaultCurrency", "post", request);

                            return false;
                        }
                    });

                    listCurrencies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            openActivity(RateActivity.class, currencies.get(i).getCurrency().getCode());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                }

                break;
            }
        }

    }
}
