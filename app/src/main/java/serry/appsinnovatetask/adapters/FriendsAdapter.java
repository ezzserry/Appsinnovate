package serry.appsinnovatetask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        Picasso.with(mContext).load(countries.getImage()).into(holder.ivPP, new Callback() {
            @Override
            public void onSuccess() {
                holder.ivPP.setVisibility(View.VISIBLE);
                holder.pbImage.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

                holder.ivPP.setVisibility(View.VISIBLE);
                holder.pbImage.setVisibility(View.GONE);
                holder.ivPP.setImageResource(R.drawable.ic_contact);
            }
        });
        holder.tvUsername.setText(countries.getName());
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPhoneNumber)
        TextView tvUsername;
        @BindView(R.id.ivContactImage)
        ImageView ivPP;
        @BindView(R.id.pbImage)
        ProgressBar pbImage;
        @BindView(R.id.flContainer)
        FrameLayout flContainer;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            flContainer.setVisibility(View.VISIBLE);

        }
    }
}
