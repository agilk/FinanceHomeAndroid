package az.kerimov.financehome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.pojo.TransactionReport;

import java.util.List;

public class LastTransactionsAdapter extends BaseAdapter {
    private List<TransactionReport> data;
    private static LayoutInflater inflater = null;

    public LastTransactionsAdapter(Context context, List<TransactionReport> data) {
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
            vi = inflater.inflate(R.layout.layout_transaction_row, null);
        }
        TextView transAmount  = (TextView) vi.findViewById(R.id.txTransAmount);
        TextView transAmountLocal = (TextView) vi.findViewById(R.id.txTransAmountLocal);
        TextView transAmountUsd = (TextView) vi.findViewById(R.id.txTransAmountUsd);
        TextView transCategory = (TextView) vi.findViewById(R.id.txTransCategory);
        TextView transSubCategory = (TextView) vi.findViewById(R.id.txTransSubCategory);
        TextView transDate = (TextView) vi.findViewById(R.id.txTransDate);
        TextView transWallet1 = (TextView) vi.findViewById(R.id.txTransWallet1);
        TextView transWallet2 = (TextView) vi.findViewById(R.id.txTransWallet2);
        TextView transCurr = (TextView) vi.findViewById(R.id.txTransCurr);
        TextView transNotes = (TextView) vi.findViewById(R.id.txTransNotes);

        transAmount.setText(String.format("%.2f ",data.get(i).getAmount()));
        transAmountLocal.setText(String.format("%.2f ",data.get(i).getAmountLocal())+" (L) ");
        transAmountUsd.setText(String.format("%.2f ",data.get(i).getAmountUsd())+" (U) ");

        transDate.setText(data.get(i).getCdate().substring(0,10));
        transCategory.setText(data.get(i).getCategory());
        transSubCategory.setText(data.get(i).getSubCategory());
        transWallet1.setText(data.get(i).getWallet1());
        if (data.get(i).getWallet2()!=null)
            transWallet2.setText(data.get(i).getWallet2());
        transCurr.setText(data.get(i).getCurrency());
        transNotes.setText(data.get(i).getNotes());



        return vi;
    }
}
