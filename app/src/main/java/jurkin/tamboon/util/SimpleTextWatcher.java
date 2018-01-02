package jurkin.tamboon.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * A simple text watcher that does not require to implement any of the {@link TextWatcher} methods
 * thus reducing unnecessary boilerplate amount of code.
 *
 * Created by Andrej Jurkin on 1/2/18.
 */

public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
