package az.kerimov.financehome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.pojo.Category;

import java.util.List;

public class CategorySettingsAdapter  extends BaseAdapter{

    private List<Category> data;
    private static LayoutInflater inflater = null;

    public CategorySettingsAdapter(Context context, List<Category> data) {
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
            vi = inflater.inflate(R.layout.layout_category_row, null);
        }
        TextView name = (TextView) vi.findViewById(R.id.lvCategoryName);
        TextView orient = (TextView) vi.findViewById(R.id.lvCategoryOrientation);
        ImageView debtImg = (ImageView) vi.findViewById(R.id.imgDebtIcon);

        name.setText(data.get(i).getName());
        orient.setText(data.get(i).getOrientation().getName());
        if (data.get(i).isDebt()) {
            debtImg.setVisibility(View.VISIBLE);
        }else{
            debtImg.setVisibility(View.INVISIBLE);
        }
        if (data.get(i).getOrientation().getSign()<0){
            orient.setTypeface(null, Typeface.BOLD);
            orient.setTextColor(Color.parseColor("#008000"));
        }else {
            orient.setTypeface(null, Typeface.NORMAL);
            orient.setTextColor(Color.parseColor("#FF8080"));
        }
        return vi;
    }
}
