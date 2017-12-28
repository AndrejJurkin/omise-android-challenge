package jurkin.tamboon.view.charitylist;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import jurkin.tamboon.api.Repository;
import jurkin.tamboon.model.Charity;

/**
 * Created by Andrej Jurkin on 12/22/17.
 */

public class CharityListViewModel extends ViewModel {

    private Repository repository;

    @Inject
    public CharityListViewModel(Repository repository) {
        this.repository = repository;
    }

    /**
     * Get the list of charities.
     *
     * In this simple example this would only emit once we have a response from the server.
     * We could extend the functionality to use local data sources or cache, so for example
     * this observable would emit data when the we change something in local DB, therefore
     * trigger the UI updates automatically.
     *
     * @return The {@link Observable} that will emit every time the data has been updated.
     */
    public Observable<List<Charity>> getCharities() {
        return this.repository.getCharities();
    }
}
