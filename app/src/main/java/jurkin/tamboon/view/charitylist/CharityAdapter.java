package jurkin.tamboon.view.charitylist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jurkin.tamboon.R;
import jurkin.tamboon.model.Charity;

/**
 * Created by Andrej Jurkin on 12/22/17.
 */

class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    /**
     * The item view callback interface
     */
    interface OnItemClickListener {
        void onItemClick(Charity charity);
    }

    @NonNull
    private List<Charity> data;

    @Nullable
    private OnItemClickListener onItemClickListener;

    CharityAdapter(@Nullable List<Charity> data) {
        setData(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_charity, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Charity charity = data.get(position);
        holder.charityName.setText(charity.getName());

        Glide.with(holder.itemView.getContext())
                .load(charity.getLogoUrl())
                .error(R.drawable.ic_placeholder)
                .into(holder.charityImage);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(charity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Update adapter data and notify changes
     *
     * @param data The new charity data
     */
    public void setData(@Nullable List<Charity> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.data = data;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.charity_image)
        ImageView charityImage;

        @BindView(R.id.charity_name)
        TextView charityName;

        @BindView(R.id.donate_button)
        Button donateButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}