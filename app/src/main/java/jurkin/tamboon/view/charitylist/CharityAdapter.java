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
 * Charity adapter takes a list of charity objects and displays them in the recycler view.
 *
 * The CharityAdapter will display an empty list if data set is null or empty.
 *
 * Created by Andrej Jurkin on 12/22/17.
 */

class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    /**
     * The item view click callback interface.
     */
    interface OnItemClickListener {
        /**
         * @param charity The Charity object associated with the clicked item
         * @param charityImage The charity image view, used for the shared element transition
         * @param charityName The charity name view, used for the shared element transition
         */
        void onItemClick(Charity charity, View charityImage, View charityName);
    }

    /**
     * The charity list data that will be displayed in the recycler view
     */
    private List<Charity> data;

    /**
     * On item click listener that is notified on item view clicks
     */
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
                onItemClickListener.onItemClick(charity, holder.charityImage, holder.charityName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Update adapter data and notify changes.
     *
     * @param data The new charity data, it can be null or empty
     */
    public void setData(@Nullable List<Charity> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.data = data;
        this.notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.charity_image)
        ImageView charityImage;

        @BindView(R.id.charity_name)
        TextView charityName;

        @BindView(R.id.donate_button)
        Button donateButton;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}