package serry.appsinnovatetask.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import serry.appsinnovatetask.R;
import serry.appsinnovatetask.utils.MyApplication;
import serry.appsinnovatetask.utils.ServicesHelper;

public class CountriesTaskActivity extends AppCompatActivity {
    @BindView(R.id.rvCountries)
    RecyclerView rvCountries;

    @BindView(R.id.etSearch)
    AppCompatEditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_task);
        ButterKnife.bind(this);
        initViews();
        loadCountries();

    }

    private void initViews() {
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        rvCountries.hasFixedSize();
//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    // Perform action on key press
//                    String query = etSearch.getText().toString();
//                    if (MyApplication.myList.containsKey(query)) {
//                        adapter = new MyRecyclerViewAdapter(CountriesTaskActivity.this, MyApplication.myList.get(query));
//                        rvCountries.setAdapter(adapter);
//                    } else
//                        search(query);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private void loadCountries() {
        ServicesHelper.getInstance().getCountriesList(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
