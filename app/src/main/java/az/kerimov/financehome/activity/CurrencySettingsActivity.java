package az.kerimov.financehome.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.CurrencySettingsAdapter;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Currency;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import az.kerimov.financehome.pojo.UserCurrency;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class CurrencySettingsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "az.kerimov.financehome.SESSIONKEY";

    private String sessionKey;

    private Context context;

    private HashMap<Integer, UserCurrency> mapCurrency;
    private HashMap<Integer, Currency> mapSysCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_settings);

        context = this;

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        sessionKey = message;

        Request request = new Request();
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
        FinamanceController.makeRequest(this, "getAllCurrencies", "post", request);
    }

    public void clickAddCurrency(View view) {
        Request request = new Request();
        request.setSessionKey(sessionKey);
        Spinner spinner = (Spinner) findViewById(R.id.spnCurrencies);
        request.setCurrencyCode(mapSysCurrency.get(spinner.getSelectedItemPosition()).getCode());

        FinamanceController.makeRequest(this, "addCurrency", "put", request);
    }

    public void setHttpResult(String result, String point) {

        if (point.equals("addCurrency")) {
            Request request = new Request();
            request.setSessionKey(sessionKey);
            FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
        } else if (point.equals("getAllCurrencies")) {

            Gson gson = new Gson();
            Response response = new Response();
            try {
                response = gson.fromJson(result, Response.class);

                List<Currency> currencies = response.getData().getSysCurrencies();

                String[] spinnerArray = new String[currencies.size()];

                mapSysCurrency = new HashMap<Integer, Currency>();
                for (int i = 0; i < currencies.size(); i++) {
                    mapSysCurrency.put(i, currencies.get(i));
                    spinnerArray[i] = currencies.get(i).getShortDescription();
                }


                Spinner spinner = (Spinner) findViewById(R.id.spnCurrencies);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (point.equals("getUserCurrencies") || point.equals("setDefaultCurrency")) {


            Gson gson = new Gson();
            Response response = new Response();
            try {
                response = gson.fromJson(result, Response.class);

                ListView listCurrencies = (ListView) findViewById(R.id.listCurrencies);

                List<UserCurrency> currencies = response.getData().getCurrencies();

                mapCurrency = new HashMap<Integer, UserCurrency>();
                for (int i = 0; i < currencies.size(); i++) {
                    mapCurrency.put(i, currencies.get(i));
                }

                listCurrencies.setAdapter(new CurrencySettingsAdapter(this, currencies));

                listCurrencies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView xxx = (TextView) findViewById(R.id.xxx);
                        xxx.setText(mapCurrency.get(i).getCurrency().getDescription() + " has been set as your default Currency ");

                        Request request = new Request();
                        request.setSessionKey(sessionKey);
                        request.setCurrencyCode(mapCurrency.get(i).getCurrency().getCode());

                        FinamanceController.makeRequest(context, "setDefaultCurrency", "post", request);

                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }
}
