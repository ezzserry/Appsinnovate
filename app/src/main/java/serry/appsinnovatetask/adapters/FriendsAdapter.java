package serry.appsinnovatetask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import serry.appsinnovatetask.R;
import serry.appsinnovatetask.models.Friend;

/**
 * Created by PC on 2/17/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private List<Friend> countriesList;
    private Context mContext;

    public FriendsAdapter(List<Friend> countriesList, Context mContext) {
        this.countriesList = countriesList;
        this.mContext = mContext;
    }

    @Override
    public FriendsAdapter.FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.contacts_row, null);
        FriendsViewHolder countriesViewHolder = new FriendsViewHolder(view);
        return countriesViewHolder;
    }


    @Override
    public void onBindViewHolder(final FriendsViewHolder holder, int position) {
        Friend countries = countriesList.get(position);
        holder.tvUsername.setText(countries.getName());
        Glide.with(mContext).load(countries.getImage()).fitCenter().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }
        }).into(holder.ivPP);
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername;
        ImageView ivPP;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            tvUsername = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            ivPP = (ImageView) itemView.findViewById(R.id.ivContactImage);

        }
    }
}
