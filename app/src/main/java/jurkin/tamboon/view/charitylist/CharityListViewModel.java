package jurkin.tamboon.view.charitylist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import jurkin.tamboon.api.Repository;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.view.ViewModelState;

/**
 * Created by Andrej Jurkin on 12/22/17.
 */

public class CharityListViewModel extends ViewModel {

    private Repository repository;

    private PublishSubject<ViewModelState> viewModelState;

    @Inject
    public CharityListViewModel(Repository repository) {
        this.repository = repository;
        this.viewModelState = PublishSubject.create();
    }

    public PublishSubject<ViewModelState> getViewModelState() {
        return viewModelState;
    }

    /**
     * Get the list of charities and update the view model states accordingly.
     *
     * In this simple example this would only emit once we have a response from the server.
     * We could extend the functionality to use local data sources or cache, so for example
     * this observable would emit data when the we change something in local DB, therefore
     * trigger the UI updates automatically.
     *
     * @return The {@link Observable} that will emit every time the data has been updated.
     */
    public Observable<List<Charity>> getCharities() {
        setViewModelState(ViewModelState.Loading);
        return this.repository.getCharities()
                .doOnNext(charities -> {
                    if (charities.isEmpty()) {
                        setViewModelState(ViewModelState.Empty);
                    } else {
                        setViewModelState(ViewModelState.Loaded);
                    }
                })
                .doOnError(throwable -> {
                    setViewModelState(ViewModelState.Error);
                });
    }

    public void setViewModelState(ViewModelState state) {
        this.viewModelState.onNext(state);
    }
}
