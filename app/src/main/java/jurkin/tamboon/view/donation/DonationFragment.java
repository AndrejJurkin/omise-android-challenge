package jurkin.tamboon.view.donation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.omise.android.ui.CreditCardEditText;
import io.reactivex.disposables.Disposable;
import jurkin.tamboon.R;
import jurkin.tamboon.util.SimpleTextWatcher;
import jurkin.tamboon.model.Charity;
import jurkin.tamboon.view.BaseFragment;

/**
 * Created by Andrej Jurkin on 12/29/17.
 */

public class DonationFragment extends BaseFragment {
    private static final String TAG = "DonationFragment";

    @BindView(R.id.root)
    LinearLayout root;

    @BindView(R.id.charity_image)
    ImageView charityImage;

    @BindView(R.id.charity_name)
    TextView charityName;

    @BindView(R.id.donation_amount)
    TextInputEditText donationAmount;

    @BindView(R.id.card_number)
    CreditCardEditText cardNumber;

    @BindView(R.id.cardholder_name)
    TextInputEditText cardholderName;

    @BindView(R.id.cvc_code)
    TextInputEditText cvc;

    @BindView(R.id.expiry_date)
    TextInputEditText expiryDate;

    @BindView(R.id.donate_button)
    AppCompatButton donateButton;

    private DonationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = this.viewModelFactory.create(DonationViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView:");
        View view = inflater.inflate(R.layout.fragment_donation, container, false);
        ButterKnife.bind(this, view);

        final Charity extraCharity = getActivity().getIntent()
                .getParcelableExtra(DonationActivity.EXTRA_CHARITY);
        viewModel.setCharity(extraCharity);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
        charityName.setText(viewModel.getTitle());

        Glide.with(getActivity())
                .load(viewModel.getMainImageUrl())
                .into(charityImage);

        // Donate button is disabled by default
        donateButton.setEnabled(false);

        // Add text watchers
        donationAmount.addTextChangedListener(donationAmountTextWatcher);
        cardNumber.addTextChangedListener(cardNumberTextWatcher);
        expiryDate.addTextChangedListener(expiryDateTextWatcher);
        cvc.addTextChangedListener(cvcTextWatcher);
        cardholderName.addTextChangedListener(cardholderNameTextWatcher);
        cardholderName.setOnEditorActionListener(editorListener);

        Disposable donateButtonSubscription = viewModel.isDonateButtonEnabled()
                .subscribe(enabled -> this.donateButton.setEnabled(enabled));
        disposables.add(donateButtonSubscription);
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.donate_button)
    public void onDonationButtonClick() {
        this.viewModel.donate();
    }

    /*
     * Text watcher that updates view model when donation amount value changes
     */
    private TextWatcher donationAmountTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            viewModel.setAmount(s.toString());
        }
    };

    /*
     * Text watcher that automatically requests a next focus
     * after the credit card number has been filled in
     */
    private TextWatcher cardNumberTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 19) {
                expiryDate.requestFocus();
            }
            viewModel.setCardNumber(s.toString());
        }
    };

    /*
     * Text watcher that formats a number input into a mm/yy format,requests a next focus and
     * updates view model when expiry date value changes
     */
    private TextWatcher expiryDateTextWatcher = new SimpleTextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0 && (s.length() % 3) == 0) {
                final char c = s.charAt(s.length() - 1);

                if (c == '/') {
                    s.delete(s.length() - 1, s.length());
                }
            }

            if (s.length() > 0 && (s.length() % 3) == 0) {
                char c = s.charAt(s.length() - 1);

                if (Character.isDigit(c) && TextUtils.split(
                        s.toString(), String.valueOf("/")).length <= 2) {
                    s.insert(s.length() - 1, String.valueOf("/"));
                }
            }

            // The expiry date has been filled, focus next view
            if (s.length() == 5) {
                cvc.requestFocus();
            }
            viewModel.setExpiryDate(s.toString());
        }
    };

    /*
     * Text watcher that automatically requests a next focus when the cvc code is filled
     * and updates view model when cvc value changes
     */
    private TextWatcher cvcTextWatcher = new SimpleTextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 3) {
                cardholderName.requestFocus();
            }
            viewModel.setCvc(s.toString());
        }
    };

    /*
     * Text watcher that updates the view model when the cardholder name value changes
     */
    private TextWatcher cardholderNameTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            viewModel.setCardholderName(s.toString());
        }
    };

    /*
     * Editor action listener that hides soft keyboard when the user clicks on done action button
     */
    private TextView.OnEditorActionListener editorListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            root.requestFocus();
            closeSoftInput();
            return true;
        }
        return false;
    };
}
