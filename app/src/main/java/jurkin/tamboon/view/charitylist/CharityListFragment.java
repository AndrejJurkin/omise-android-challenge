package jurkin.tamboon.view.charitylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import jurkin.tamboon.R;
import jurkin.tamboon.view.BaseFragment;
import jurkin.tamboon.view.donation.DonationActivity;

/**
 * Created by Andrej Jurkin on 12/21/17.
 */

public class CharityListFragment extends BaseFragment {

    private static final String TAG = "CharityListFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private CharityListViewModel viewModel;

    private CharityAdapter adapter;

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
        bindViewModelState();
        bindAdapterClickListener();
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

    private void bindViewModelState() {
        Disposable subscription = this.viewModel.getViewModelState()
                .subscribe(viewModelState -> {
                    switch (viewModelState) {
                        case Loaded:
                            stopLoading();
                            break;
                        case Loading:
                            startLoading();
                            break;
                        case Empty:
                            showEmptyState();
                            break;
                        case Error:
                            // Falls through
                        default:
                            showLoadingError();
                            break;
                    }
                });

        disposables.add(subscription);
    }

    private void bindAdapterClickListener() {
        this.adapter.setOnItemClickListener((charity, charityImage, charityName) -> {
            final String transitionImage = getString(R.string.transition_charity_image);
            final String transitionName = getString(R.string.transition_charity_name);

            DonationActivity.start(getActivity(), charity,
                    new Pair<>(charityImage, transitionImage),
                    new Pair<>(charityName, transitionName)
            );
        });
    }

    private void stopLoading() {
        // TODO: Stop loading
    }

    private void startLoading() {
        // TODO: Start loading
    }

    private void showLoadingError() {
        // TODO: Show loading error
    }

    private void showEmptyState() {
        // TODO: show empty state
    }
}
