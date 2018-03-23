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
import az.kerimov.financehome.pojo.Rate;

import java.util.List;

public class RateSettingsAdapter extends BaseAdapter{

    private List<Rate> data;
    private static LayoutInflater inflater = null;

    public RateSettingsAdapter(Context context, List<Rate> data) {
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
            vi = inflater.inflate(R.layout.layout_rate_row, null);
        }
        TextView date = (TextView) vi.findViewById(R.id.rateDate);
        TextView value = (TextView) vi.findViewById(R.id.rateValue);

        try {
            date.setText(data.get(i).getCtime());
            value.setText(data.get(i).getRate().toString());
        }catch (NullPointerException ex){
            date.setText("19700101");
            value.setText("0");
        }

        return vi;
    }
}
