package az.kerimov.financehome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import az.kerimov.financehome.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String EXTRA_SESSIONKEY = "az.kerimov.financehome.SESSIONKEY";
    private static final String EXTRA_ACTIVE = "az.kerimov.financehome.ACTIVE";

    private String sessionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Intent intent = getIntent();
        sessionKey = intent.getStringExtra(EXTRA_SESSIONKEY);
    }

    private void openActivity(Class c, Boolean active){
        Intent intent = new Intent(this, c);
        intent.putExtra(EXTRA_SESSIONKEY, sessionKey);
        intent.putExtra(EXTRA_ACTIVE, active);
        startActivity(intent);
    }

    public void clickCurrencies(View view){
        openActivity(CurrencySettingsActivity.class, null);
        /*
        Intent intent = new Intent(this, CurrencySettingsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, sessionKey);
        startActivity(intent);*/
    }

    public void clickWallets(View view){
        openActivity(WalletSettingsActivity.class, true);
    }

    public void clickInactiveWallets(View view){
        openActivity(WalletSettingsActivity.class, false);
    }

    public void clickCategories(View view){
        openActivity(CategoryActivity.class, null);
    }

}
