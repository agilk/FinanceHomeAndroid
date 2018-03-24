package az.kerimov.financehome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import az.kerimov.financehome.R;
import az.kerimov.financehome.adapter.SubCategorySettingsAdapter;
import az.kerimov.financehome.controller.FinamanceController;
import az.kerimov.financehome.pojo.Request;
import az.kerimov.financehome.pojo.Response;
import az.kerimov.financehome.pojo.SubCategory;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import static az.kerimov.financehome.common.CommonUtils.fillMap;

public class SubCategoryActivity extends AppCompatActivity {

    private static final String EXTRA_SESSION = "az.kerimov.financehome.SESSIONKEY";
    private static final String EXTRA_CATEGORY = "az.kerimov.financehome.CATEGORY";

    private String sessionKey;

    private Integer categoryId;

    private Integer subCategoryId;
    private int selectedItem=-1;

    private ListView listSubCategories;
    private LinearLayout panelTools;
    private EditText edSubCategoryName;

    HashMap<Integer, SubCategory> mapSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Intent intent = getIntent();
        sessionKey = intent.getStringExtra(EXTRA_SESSION);
        categoryId = intent.getIntExtra(EXTRA_CATEGORY, 0);

        listSubCategories = (ListView) findViewById(R.id.listSubCategories);
        panelTools = (LinearLayout) findViewById(R.id.panelSubCategoryTools);
        edSubCategoryName = (EditText) findViewById(R.id.edSubCategoryName);

        Request request = new Request();
        request.setCategoryId(categoryId);
        request.setSessionKey(sessionKey);

        FinamanceController.makeRequest(this, "getSubCategories", "post", request);
    }

    public void clickDeleteSubCategory(View view){
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setSubCategoryId(subCategoryId);

        panelTools.setVisibility(View.INVISIBLE);
        selectedItem = -1;
        subCategoryId = null;
        FinamanceController.makeRequest(this, "deleteSubCategory", "delete", request);
        FinamanceController.makeRequest(this, "getSubCategories", "post", request);
    }

    public void clickAddSubCategory(View view){
        Request request = new Request();
        request.setSessionKey(sessionKey);
        request.setCategoryId(categoryId);
        request.setCustomName(edSubCategoryName.getText().toString());
        FinamanceController.makeRequest(this, "addSubCategory", "put", request);
        FinamanceController.makeRequest(this, "getSubCategories", "post", request);
    }

    public void setHttpResult(String result, String point){
        switch (point){
            case "getSubCategories":{

                Gson gson = new Gson();
                Response response;
                try {
                    response = gson.fromJson(result, Response.class);

                    List<SubCategory> subCategories = response.getData().getSubCategories();
                    mapSubCategory = fillMap(subCategories);

                    listSubCategories.setAdapter(new SubCategorySettingsAdapter(this, subCategories));

                    listSubCategories.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i != selectedItem) {
                                selectedItem = i;
                                subCategoryId = mapSubCategory.get(i).getId();
                                panelTools.setVisibility(View.VISIBLE);
                            }else {
                                selectedItem = -1;
                                subCategoryId = null;
                                panelTools.setVisibility(View.INVISIBLE);
                            }

                            return false;
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }

    }
}
