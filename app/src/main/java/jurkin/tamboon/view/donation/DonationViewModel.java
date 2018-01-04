package jurkin.tamboon.view.donation;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import javax.inject.Inject;

import co.omise.android.TokenRequest;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import jurkin.tamboon.api.Repository;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.model.Donation;
import jurkin.tamboon.view.ViewModelState;

/**
 * Created by Andrej Jurkin on 12/29/17.
 */

public class DonationViewModel extends ViewModel {
    private static final String TAG = "DonationViewModel";

    private Charity charity;

    private Repository repo;

    private int amount;

    private String cardNumber;

    private String cvc;

    private String cardholderName;

    private String expiryDate;

    private PublishSubject<Boolean> isDonationButtonEnabled;

    private PublishSubject<ViewModelState> viewModelState;

    @Inject
    public DonationViewModel(Repository repository) {
        this.repo = repository;
        this.isDonationButtonEnabled = PublishSubject.create();
        this.viewModelState = PublishSubject.create();
    }

    /**
     * @return Observable that will be notifying when the donation button state should change
     */
    public Observable<Boolean> isDonateButtonEnabled() {
        return this.isDonationButtonEnabled;
    }

    /**
     * Set {@link Charity} model that we are working with
     * @param charity
     */
    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    /**
     * @return Main image that is displayed on top of the view
     */
    public String getMainImageUrl() {
        return charity.getLogoUrl();
    }

    /**
     * @return Title that is displayed on top of the view
     */
    public String getTitle() {
        return charity.getName();
    }

    /**
     * Create a token request from the credit card model, query Omise api and get the token,
     * after receiving the credit card token from the Omise API, create a Donation payload and
     * send it to the remote serer.
     */
    public void donate() {
        this.isDonationButtonEnabled.onNext(false);
        this.viewModelState.onNext(ViewModelState.Loading);
        this.repo
                // Send token request to the Omise API, this call will always fail, because
                // we're working with dummy data
                .getToken(createTokenRequest())
                .map(token -> new Donation(token.card.name, token.id, amount))
                // Since we are in the test mode, the Omise API will always fail to return a token
                // so for the test purposes I've added this line to continue with the flow
                // even though we should stop at the first step
                .onErrorReturn(throwable ->
                        new Donation(cardholderName, "dummy_token", amount))
                .flatMap(donation ->
                        repo.donate(donation))
                .subscribe(
                        onSuccess -> {
                            this.viewModelState.onNext(ViewModelState.Loaded);
                        },
                        throwable -> {
                            this.viewModelState.onNext(ViewModelState.Error);
                        });
    }

    public void setAmount(String amount) {
        if (amount == null || amount.isEmpty()) {
            this.amount = 0;
            validateCreditCardModel();
            return;
        }

        this.amount = Integer.valueOf(amount);
        validateCreditCardModel();
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        validateCreditCardModel();
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
        validateCreditCardModel();
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
        validateCreditCardModel();
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        validateCreditCardModel();
    }

    /**
     * Trigger credit card model validation, this will notify all the subscribers that rely
     * on the correctness of the model
     */
    public void validateCreditCardModel() {
        this.isDonationButtonEnabled.onNext(isCreditCardModelValid());
    }

    public Observable<ViewModelState> getViewModelState() {
        return viewModelState;
    }

    /*
     * Very simple validation, just for the test purposes
     */
    @VisibleForTesting
    boolean isCreditCardModelValid() {

        if (this.amount <= 0) {
            return false;
        }

        if (this.cardNumber == null || this.cardNumber.length() < 19) {
            return false;
        }

        if (this.expiryDate == null || this.expiryDate.length() != 5) {
            return false;
        }

        if (this.cvc == null || this.cvc.length() != 3) {
            return false;
        }

        return cardholderName != null && cardholderName.length() >= 3;
    }

    @VisibleForTesting
    TokenRequest createTokenRequest() {
        final String[] expiryValues = this.expiryDate.split("/");
        final int month = Integer.valueOf(expiryValues[0]);
        final int year = Integer.valueOf(expiryValues[1]);

        final TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.name = this.cardholderName;
        tokenRequest.number = this.cardNumber;
        tokenRequest.securityCode = this.cvc;
        tokenRequest.expirationMonth = month;
        tokenRequest.expirationYear = year;

        return tokenRequest;
    }
}
