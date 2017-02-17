package serry.appsinnovatetask.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import serry.appsinnovatetask.R;
import serry.appsinnovatetask.adapters.CountriesAdapter;
import serry.appsinnovatetask.models.Countries;
import serry.appsinnovatetask.utils.MyApplication;
import serry.appsinnovatetask.utils.ServicesHelper;

import static android.R.id.list;

public class CountriesTaskActivity extends AppCompatActivity {
    @BindView(R.id.rvCountries)
    RecyclerView rvCountries;

    @BindView(R.id.etSearch)
    AppCompatEditText etSearch;
    private CountriesAdapter countriesAdapter;
    private List<Countries> countriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_task);
        ButterKnife.bind(this);
        initViews();
        loadCountries();
        addTextListener();

    }

    private void initViews() {
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        rvCountries.hasFixedSize();
    }

    private void loadCountries() {
        ServicesHelper.getInstance().getCountriesList(this, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                countriesList = new ArrayList<>();
                Gson gson = new GsonBuilder().serializeNulls().create();
                countriesList = Arrays.asList(gson.fromJson(response.toString(), Countries[].class));
                updateUI(countriesList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void updateUI(List<Countries> secondList) {
        countriesAdapter = new CountriesAdapter(secondList, this);
        rvCountries.setAdapter(countriesAdapter);
        countriesAdapter.notifyDataSetChanged();
    }

    public void addTextListener(){

        etSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<Countries> filteredList = new ArrayList<>();

                for (int i = 0; i < countriesList.size(); i++) {

                    final String text = countriesList.get(i).getsCountryName().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(countriesList.get(i));
                    }
                }

                rvCountries.setLayoutManager(new LinearLayoutManager(CountriesTaskActivity.this));
                countriesAdapter = new CountriesAdapter(filteredList,CountriesTaskActivity.this);
                rvCountries.setAdapter(countriesAdapter);
                countriesAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }
}
