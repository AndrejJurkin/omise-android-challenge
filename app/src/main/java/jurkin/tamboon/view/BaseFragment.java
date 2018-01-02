package jurkin.tamboon.view;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Base fragment class that contains common code for all fragments in the app
 *
 * Created by Andrej Jurkin on 12/21/17.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * Dispose bag for the subscriptions. All subscriptions will be disposed onStop.
     */
    protected CompositeDisposable disposables;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

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

    /**
     * Close soft keyboard
     */
    public void closeSoftInput() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
