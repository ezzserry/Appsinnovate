package serry.appsinnovatetask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import serry.appsinnovatetask.R;
import serry.appsinnovatetask.models.Countries;

/**
 * Created by PC on 2/16/2017.
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    private List<Countries> countriesList;
    private Context mContext;

    public CountriesAdapter(List<Countries> countriesList, Context mContext) {
        this.countriesList = countriesList;
        this.mContext = mContext;
    }

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contacts_row, null);
        CountriesAdapter.CountriesViewHolder countriesViewHolder = new CountriesAdapter.CountriesViewHolder(view);
        return countriesViewHolder;
    }

    @Override
    public void onBindViewHolder(CountriesViewHolder holder, int position) {
        Countries countries = countriesList.get(position);
        holder.tvCountryName.setText(countries.getsCountryName());
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public static class CountriesViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountryName;

        public CountriesViewHolder(View itemView) {
            super(itemView);
            tvCountryName = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        }
    }
}
