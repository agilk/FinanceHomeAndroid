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

import java.util.List;

public class CurrencySettingsAdapter extends BaseAdapter{

    private List<UserCurrency> data;
    private static LayoutInflater inflater = null;

    public CurrencySettingsAdapter(Context context, List<UserCurrency> data) {
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
            vi = inflater.inflate(R.layout.layout_currency_row, null);
        }
        TextView code = (TextView) vi.findViewById(R.id.currCode);
        TextView descr = (TextView) vi.findViewById(R.id.currDescr);
        code.setText(data.get(i).getCurrency().getCode());
        String descrStr = "   " + data.get(i).getCurrency().getDescription();
        descr.setText(descrStr);
        if (data.get(i).getDefaultElement()){
            code.setTypeface(null, Typeface.BOLD);
            descr.setTypeface(null, Typeface.BOLD);
            code.setTextColor(Color.parseColor("#800000"));
        }else{
            code.setTypeface(null, Typeface.NORMAL);
            descr.setTypeface(null, Typeface.NORMAL);
            code.setTextColor(Color.parseColor("#000000"));
        }
        return vi;
    }
}
