package az.kerimov.financehome.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.LastTransactionsAdapter;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import az.kerimov.financehome.pojo.UserCurrency;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "az.kerimov.financehome.SESSIONKEY";

    private String sessionKey;
    private HashMap<Integer, Integer> spinnerMapCategory;
    private HashMap<Integer, Integer> spinnerMapSubCategory;
    private HashMap<Integer, String>  spinnerMapCurrency;
    private HashMap<Integer, Integer> spinnerMapWallet;
    private HashMap<Integer, Integer> spinnerMapWalletOther;


    public void clickSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, sessionKey);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        sessionKey = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        final EditText edDate = (EditText) findViewById(R.id.edDate);
        final Calendar calendar = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        edDate.setText(df.format(calendar.getTime()));

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
                new DatePickerDialog(TransactionActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fillSpinners();

        Spinner categorySpinner = (Spinner) findViewById(R.id.edCategory);

        spinnerMapCategory = new HashMap<>();
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

    private void fillSpinners(){
        Request request = new Request();
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getUserCurrencies", "post", request);
        FinamanceController.makeRequest(this, "getCategories", "post", request);
        fillWallets();
    }

    private void fillWallets(){
        Request request = new Request();
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getUserWallets", "post", request);
        FinamanceController.makeRequest(this, "getLastTransactions", "post", request);
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

        if (!Objects.equals(spinnerMapWalletOther.get(edWalletOther.getSelectedItemPosition()), spinnerMapWallet.get(edWallet.getSelectedItemPosition()))) {


            request.setAmount(Double.parseDouble(String.valueOf(edAmount.getText())));
            request.setCategoryId(spinnerMapCategory.get(edCategory.getSelectedItemPosition()));
            request.setSessionKey(sessionKey);
            request.setCurrencyCode(spinnerMapCurrency.get(edCurrency.getSelectedItemPosition()));
            request.setSubCategoryId(spinnerMapSubCategory.get(edSubCategory.getSelectedItemPosition()));
            request.setWalletId(spinnerMapWallet.get(edWallet.getSelectedItemPosition()));
            if (edWalletOther.getSelectedItemPosition() > 0) {
                request.setWalletIdOther(spinnerMapWalletOther.get(edWalletOther.getSelectedItemPosition()));
            }
            request.setNotes(edNotes.getText().toString());
            request.setDate(edDate.getText().toString() + " 00:00:00");
            FinamanceController.makeRequest(this, "addTransaction", "put", request);
        }else{
            edNotes.setText(".");
        }
    }

    private void fillSubCategory(Integer categoryId){
        Log.w("HHH", "XX: "+spinnerMapCategory.size());
        Log.w("HHH", "ssds = "+categoryId+" --- "+spinnerMapCategory.get(categoryId).toString());
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCategoryId(spinnerMapCategory.get(categoryId));

        FinamanceController.makeRequest(this, "getSubCategories", "post", request);
    }

    @SuppressLint("UseSparseArrays")
    public void setHttpResult(String result, String point){
        switch (point) {
            case "getLastTransactions":{
                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    ListView listTransactions = (ListView) findViewById(R.id.lvLastTransactions);

                    listTransactions.setAdapter(new LastTransactionsAdapter(this, response.getData().getTransactions()));

                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            }
            case "getCategories": {

                Gson gson = new Gson();
                Response response;
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.fn_item_spinner, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            }
            case "getSubCategories": {

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    Spinner spinner = (Spinner) findViewById(R.id.edSubCategory);

                    String[] spinnerArray = new String[response.getData().getSubCategories().size()];
                    spinnerMapSubCategory = new HashMap<>();
                    for (int i = 0; i < response.getData().getSubCategories().size(); i++) {
                        spinnerMapSubCategory.put(i, response.getData().getSubCategories().get(i).getId());
                        spinnerArray[i] = response.getData().getSubCategories().get(i).getName();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.fn_item_spinner, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "getUserCurrencies": {

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    int defaultCurrency = 0;

                    Spinner spinner = (Spinner) findViewById(R.id.edCurrency);

                    List<UserCurrency> currencies = response.getData().getCurrencies();

                    String[] spinnerArray = new String[currencies.size()];
                    spinnerMapCurrency = new HashMap<>();
                    for (int i = 0; i < currencies.size(); i++) {
                        if (currencies.get(i).getDefaultElement()) {
                            defaultCurrency = i;
                        }
                        spinnerMapCurrency.put(i, currencies.get(i).getCurrency().getCode());
                        spinnerArray[i] = currencies.get(i).getCurrency().getShortDescription();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.fn_item_spinner, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(defaultCurrency);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "addTransaction":
                fillWallets();

                EditText edAmount = (EditText) findViewById(R.id.edAmount);
                Spinner edCategory = (Spinner) findViewById(R.id.edCategory);
                Spinner edSubCategory = (Spinner) findViewById(R.id.edSubCategory);
                EditText edNotes = (EditText) findViewById(R.id.edNotes);

                edAmount.setText("");
                edCategory.setSelection(0);
                edSubCategory.setSelection(0);
                edNotes.setText("");

                break;
            case "getUserWallets": {

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    int defaultWallet = 0;

                    Spinner spinner = (Spinner) findViewById(R.id.edWallet);
                    Spinner spinnerOther = (Spinner) findViewById(R.id.edWalletOther);

                    String[] spinnerArray = new String[response.getData().getWallets().size()];
                    spinnerMapWallet = new HashMap<>();
                    for (int i = 0; i < response.getData().getWallets().size(); i++) {

                        if (response.getData().getWallets().get(i).getDefaultElement()) {
                            defaultWallet = i;
                        }
                        spinnerMapWallet.put(i, response.getData().getWallets().get(i).getId());
                        spinnerArray[i] = response.getData().getWallets().get(i).getWalletName() + " (" +
                                response.getData().getWallets().get(i).getBalanceAmount() + " " +
                                response.getData().getWallets().get(i).getCurrency().getCurrency().getShortDescription() + ")";
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.fn_item_spinner, spinnerArray);


                    String[] spinnerArrayOther = new String[response.getData().getWallets().size() + 1];
                    spinnerMapWalletOther = new HashMap<>();

                    spinnerMapWalletOther.put(0, 0);
                    spinnerArrayOther[0] = "NONE";

                    for (int i = 0; i < response.getData().getWallets().size(); i++) {
                        spinnerMapWalletOther.put(i + 1, response.getData().getWallets().get(i).getId());
                        spinnerArrayOther[i + 1] = response.getData().getWallets().get(i).getWalletName() + " (" +
                                response.getData().getWallets().get(i).getBalanceAmount() + " " +
                                response.getData().getWallets().get(i).getCurrency().getCurrency().getShortDescription() + ")";
                    }

                    ArrayAdapter<String> adapterOther = new ArrayAdapter<>(this, R.layout.fn_item_spinner, spinnerArrayOther);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(defaultWallet);
                    adapterOther.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerOther.setAdapter(adapterOther);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
