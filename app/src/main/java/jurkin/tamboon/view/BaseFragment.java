package jurkin.tamboon.view;

import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Andrej Jurkin on 12/21/17.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * Dispose bag for the subscriptions. All subscriptions will are disposed onStop.
     */
    protected CompositeDisposable disposables;

    @Override
    public void onStart() {
        super.onStart();
        disposables = new CompositeDisposable();
    }

    @Override
    public void onStop() {
        super.onStop();

        // Clear subscriptions from the dispose bag
        disposables.clear();
    }
}
