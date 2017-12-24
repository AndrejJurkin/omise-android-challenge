package jurkin.tamboon.view.charitylist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jurkin.tamboon.model.Charity;

/**
 * Created by Andrej Jurkin on 12/22/17.
 */

public class CharityAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    @NonNull
    private List<Charity> data;

    public CharityAdapter(@Nullable List<Charity> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}