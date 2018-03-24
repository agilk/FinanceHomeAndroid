package az.kerimov.financehome.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.CategorySettingsAdapter;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Category;
import az.kerimov.financehome.pojo.Orientation;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import static az.kerimov.financehome.common.CommonUtils.fillMap;

public class CategoryActivity extends AppCompatActivity {

    private static final String EXTRA_SESSION = "az.kerimov.financehome.SESSIONKEY";
    private static final String EXTRA_CATEGORY = "az.kerimov.financehome.CATEGORY";

    private String sessionKey;

    private Integer categoryId;
    private int selectedItem=-1;

    private Context context;

    private ListView listCategories;
    private EditText edCustomName;
    private Spinner edOrientation;
    private CheckBox chkDebt;
    private LinearLayout panelTools;

    private HashMap<Integer, Category> mapCategory;
    private HashMap<Integer, Orientation> mapOrientations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        context = this;

        Intent intent = getIntent();
        sessionKey = intent.getStringExtra(EXTRA_SESSION);
        categoryId = intent.getIntExtra(EXTRA_CATEGORY, 0);

        listCategories = (ListView) findViewById(R.id.listCategories);
        edCustomName = (EditText) findViewById(R.id.edCategoryName);
        edOrientation = (Spinner) findViewById(R.id.edOrientation);
        chkDebt = (CheckBox) findViewById(R.id.chkCategoryDebt);
        panelTools = (LinearLayout)findViewById(R.id.panelCategoryTools);

        Request request = new Request();
        request.setSessionKey(sessionKey);
        FinamanceController.makeRequest(this, "getCategories", "post", request);
        FinamanceController.makeRequest(this, "getOrientations", "post", request);
    }

    public void clickDeleteCategory(View view){
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCategoryId(categoryId);
        selectedItem = -1;
        categoryId = null;
        panelTools.setVisibility(View.INVISIBLE);
        FinamanceController.makeRequest(this, "deleteCategory", "delete", request);
        FinamanceController.makeRequest(this, "getCategories", "post", request);
    }

    public void clickOpenSubCategories(View view){

    }

    public void clickAddCategory(View view) {

        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCustomName(edCustomName.getText().toString());
        request.setOrientationId(mapOrientations.get(edOrientation.getSelectedItemPosition()).getId());
        request.setDebt(chkDebt.isChecked());
        FinamanceController.makeRequest(this, "addCategory", "put", request);
        edCustomName.setText("");
        chkDebt.setChecked(false);
        edOrientation.setSelection(0);
        FinamanceController.makeRequest(this, "getCategories", "post", request);
    }

    public void setHttpResult(String result, String point) {

        switch (point) {
            case "getOrientations": {

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    List<Orientation> orientations = response.getData().getOrientations();
                    mapOrientations = fillMap(orientations);

                    String[] stringArray = new String[orientations.size()];
                    for (int i = 0; i < orientations.size(); i++) {
                        stringArray[i] = orientations.get(i).getName();
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArray);
                    edOrientation.setAdapter(adapter);

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

                    List<Category> categories = response.getData().getCategories();
                    mapCategory = fillMap(categories);

                    listCategories.setAdapter(new CategorySettingsAdapter(this, categories));

                    listCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != selectedItem) {
                                selectedItem = i;
                                categoryId = mapCategory.get(i).getId();
                                panelTools.setVisibility(View.VISIBLE);
                            }else {
                                selectedItem = -1;
                                categoryId = null;
                                panelTools.setVisibility(View.INVISIBLE);
                            }

                            return false;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }
}

