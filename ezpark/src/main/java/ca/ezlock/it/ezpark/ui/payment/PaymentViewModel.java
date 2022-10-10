package ca.ezlock.it.ezpark.ui.payment;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    public PaymentViewModel(MutableLiveData<Button> btn) {

        mText = new MutableLiveData<>();
        mText.setValue("This is payment fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }
}