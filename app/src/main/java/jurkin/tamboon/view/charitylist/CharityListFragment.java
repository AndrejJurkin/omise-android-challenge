package jurkin.tamboon.view.charitylist;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import jurkin.tamboon.R;
import jurkin.tamboon.view.BaseFragment;

/**
 * Created by Andrej Jurkin on 12/21/17.
 */

public class CharityListFragment extends BaseFragment {

    private static final String TAG = "CharityListFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private CharityListViewModel viewModel;

    private CharityAdapter adapter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = this.viewModelFactory.create(CharityListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_charity_list, container, false);
        ButterKnife.bind(this, v);

        this.adapter = new CharityAdapter(null);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        bindData();
    }

    private void bindData() {
        Disposable subscription = this.viewModel.getCharities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charities -> {
                    Log.d(TAG, "Charity list has been updated");
                    this.adapter.setData(charities);
                }, throwable -> {
                   // TODO: handle exceptions
                });

        disposables.add(subscription);
    }
}
