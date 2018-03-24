package az.kerimov.financehome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.pojo.SubCategory;

import java.util.List;

public class SubCategorySettingsAdapter extends BaseAdapter {

    private List<SubCategory> data;
    private static LayoutInflater inflater = null;

    public SubCategorySettingsAdapter(Context context, List<SubCategory> data) {
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
            vi = inflater.inflate(R.layout.layout_sub_category_row, null);
        }
        TextView name = (TextView) vi.findViewById(R.id.lvSubCategoryName);

        name.setText(data.get(i).getName());
        return vi;
    }
}
