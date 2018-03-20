package az.kerimov.financehome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import az.kerimov.financehome.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "az.kerimov.financehome.SESSIONKEY";

    private String sessionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        sessionKey = message;
    }


    public void clickCurrencies(View view){
        Intent intent = new Intent(this, CurrencySettingsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, sessionKey);
        startActivity(intent);
    }

}
