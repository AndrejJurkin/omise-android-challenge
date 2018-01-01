package jurkin.tamboon.api;

import java.util.List;

import co.omise.android.Client;
import co.omise.android.TokenRequest;
import co.omise.android.TokenRequestListener;
import co.omise.android.models.Token;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.model.Donation;

/**
 * The central data source for the application
 *
 * Created by Andrej Jurkin on 12/22/17.
 */

public class Repository {

    private OmiseService omiseService;

    private Client omiseClient;

    /**
     * Build the Repository with OmiseService and OmiseClient
     *
     * In production environment we would be using Remote and Local data sources instead.
     *
     * @param omiseService The API interface provided from retrofit implementation
     * @param omiseClient The Omise {@link Client} instance
     */
    public Repository(OmiseService omiseService, Client omiseClient) {
        this.omiseService = omiseService;
        this.omiseClient = omiseClient;
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

    /**
     * Get credit card token from Omise client
     *
     * @param tokenRequest {@link TokenRequest}
     * @return Observable {@link Token}
     */
    public Observable<Token> getToken(TokenRequest tokenRequest) {
        PublishSubject<Token> response = PublishSubject.create();

        this.omiseClient.send(tokenRequest, new TokenRequestListener() {
            @Override
            public void onTokenRequestSucceed(TokenRequest tokenRequest, Token token) {
                response.onNext(token);
                response.onComplete();
            }

            @Override
            public void onTokenRequestFailed(TokenRequest tokenRequest, Throwable throwable) {
                response.onError(throwable);
                response.onComplete();
            }
        });

        return response;
    }
}
