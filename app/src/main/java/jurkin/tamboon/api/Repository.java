package jurkin.tamboon.api;

import java.util.List;

import io.reactivex.Observable;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.model.Donation;

/**
 * The central data source for the application.
 *
 * Created by Andrej Jurkin on 12/22/17.
 */

public class Repository {

    private OmiseService omiseService;

    /**
     * Build the Repository with OmiseService.
     * In production environment we would be using Remote and Local data sources instead.
     *
     * @param omiseService
     */
    public Repository(OmiseService omiseService) {
        this.omiseService = omiseService;
    }

    /**
     * Get charities from the remote data source
     *
     * @return Observable list of {@link Charity}
     */
    public Observable<List<Charity>> getCharities() {
        return this.omiseService.getCharities();
    }

    /**
     * Post a donation request to the remote data source
     *
     * @param donation The {@link Donation} payload
     * @return Empty observable
     */
    public Observable<Void> donate(Donation donation) {
        return this.donate(donation);
    }
}
