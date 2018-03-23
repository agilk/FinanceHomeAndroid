package az.kerimov.financehome.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.*;

public class RateActivity extends AppCompatActivity {

    private static final String EXTRA_SESSION = "az.kerimov.financehome.SESSIONKEY";
    private static final String EXTRA_CURRENCY = "az.kerimov.financehome.CURRENCY";

    private final DateFormat df = new SimpleDateFormat("yyyyMMdd");

    private String sessionKey;
    private String currencyCode;

    private ListView listRates;
    private EditText edDate;
    private EditText edRate;

    private HashMap<Integer, Rate> mapRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        listRates = (ListView) findViewById(R.id.listRates);
        edDate = (EditText) findViewById(R.id.edRateDate);
        edRate = (EditText) findViewById(R.id.edRateValue);

        final Calendar calendar = Calendar.getInstance();
        edDate.setText(df.format(new Date()));


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edDate.setText(df.format(calendar.getTime()));
            }

        };

        edDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(RateActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Intent intent = getIntent();

        sessionKey = intent.getStringExtra(EXTRA_SESSION);
        currencyCode = intent.getStringExtra(EXTRA_CURRENCY);

        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCurrencyCode(currencyCode);
        request.setRateDate(df.format(new Date()));

        FinamanceController.makeRequest(this, "getRateForDate", "post", request);
    }

    public void clickAddRate(View view){
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCurrencyCode(currencyCode);
        request.setRateDate(edDate.getText().toString());
        request.setRate(Double.parseDouble(String.valueOf(edRate.getText())));
        FinamanceController.makeRequest(this, "addRate", "put", request);

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
