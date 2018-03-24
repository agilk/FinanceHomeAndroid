package az.kerimov.financehome.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.pojo.UserCurrency;
import az.kerimov.financehome.pojo.Wallet;

import java.util.List;

public class WalletSettingsAdapter extends BaseAdapter{

    private List<Wallet> data;
    private static LayoutInflater inflater = null;

    public WalletSettingsAdapter(Context context, List<Wallet> data) {
        //Context context1 = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.layout_wallet_row, null);
        }
        TextView code = (TextView) vi.findViewById(R.id.currCode);
        TextView name = (TextView) vi.findViewById(R.id.customName);
        TextView balance = (TextView) vi.findViewById(R.id.currentBalance);

        code.setText(data.get(i).getCurrency().getCurrency().getShortDescription());
        name.setText(data.get(i).getWalletName());
        balance.setText(String.format("%.2f ",data.get(i).getBalanceAmount()));


        if (data.get(i).getDefaultElement()){
            code.setTypeface(null, Typeface.BOLD);
            name.setTypeface(null, Typeface.BOLD);
            name.setTextColor(Color.parseColor("#800000"));
        }

        return vi;
    }
}
