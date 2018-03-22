package az.kerimov.financehome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.RateSettingsAdapter;
import az.kerimov.financehome.common.CommonUtils;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Rate;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class RateActivity extends AppCompatActivity {

    private static final String EXTRA_SESSION = "az.kerimov.financehome.SESSIONKEY";
    private static final String EXTRA_CURRENCY = "az.kerimov.financehome.CURRENCY";

    private String sessionKey;
    private String currencyCode;

    private ListView listRates;

    private HashMap<Integer, Rate> mapRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        listRates = (ListView) findViewById(R.id.listRates);

        Intent intent = getIntent();

        sessionKey = intent.getStringExtra(EXTRA_SESSION);
        currencyCode = intent.getStringExtra(EXTRA_CURRENCY);

        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCurrencyCode(currencyCode);
        request.setRateDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));

        FinamanceController.makeRequest(this, "getRateForDate", "post", request);
    }

    public void setHttpResult(String result, String point)

    {
        switch (point) {
            case "getRateForDate": {

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    if (response.getData() == null)
                        return;

                    List<Rate> rates = new ArrayList<>();
                    rates.add(response.getData().getRate());

                    mapRates = CommonUtils.fillMap(rates);

                    listRates.setAdapter(new RateSettingsAdapter(this, rates));
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            }
        }
    }
}
