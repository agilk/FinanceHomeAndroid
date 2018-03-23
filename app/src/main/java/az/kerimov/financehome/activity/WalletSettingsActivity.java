package az.kerimov.financehome.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.WalletSettingsAdapter;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import az.kerimov.financehome.pojo.UserCurrency;
import az.kerimov.financehome.pojo.Wallet;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import static az.kerimov.financehome.common.CommonUtils.fillMap;

public class WalletSettingsActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "az.kerimov.financehome.SESSIONKEY";

    private String sessionKey;


    private Context context;

    private ListView listWallets;
    private Spinner edCurrency;
    private EditText edCustomName;


    private HashMap<Integer, Wallet> mapWallets;
    private HashMap<Integer, UserCurrency> mapCurrencies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_settings);

        context = this;

        Intent intent = getIntent();
        sessionKey = intent.getStringExtra(EXTRA_MESSAGE);

        listWallets = (ListView) findViewById(R.id.listWallets);
        edCurrency = (Spinner) findViewById(R.id.edUserCurrency);
        edCustomName = (EditText) findViewById(R.id.edCustomName);

        Request request = new Request();
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getUserWallets", "post", request);
        FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
    }

    public void clickAddWallet(View view){

        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCurrencyCode(mapCurrencies.get(edCurrency.getSelectedItemPosition()).getCurrency().getCode());
        request.setCustomName(edCustomName.getText().toString());
        FinamanceController.makeRequest(this, "addWallet", "put", request);

        FinamanceController.makeRequest(this, "getUserWallets", "post", request);
    }

    public void setHttpResult(String result, String point) {

        switch (point) {
            case "getUserWallets":
            case "setDefaultWallet":{

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    List<Wallet> wallets = response.getData().getWallets();

                    mapWallets = fillMap(response.getData().getWallets());


                    listWallets.setAdapter(new WalletSettingsAdapter(this, wallets));

                    listWallets.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Request request = new Request();
                            request.setSessionKey(sessionKey);
                            request.setWalletId(mapWallets.get(i).getId());

                            FinamanceController.makeRequest(context, "setDefaultWallet", "post", request);

                            return false;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            case "getUserCurrencies":{
                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    List<UserCurrency> userCurrencies = response.getData().getCurrencies();

                    mapCurrencies = fillMap(userCurrencies);

                    String[] spinnerArray = new String[userCurrencies.size()];
                    for(int i = 0; i < userCurrencies.size(); i++){
                        spinnerArray[i] = userCurrencies.get(i).getCurrency().getShortDescription();
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    edCurrency.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                    break;
            }
        }
    }
}

