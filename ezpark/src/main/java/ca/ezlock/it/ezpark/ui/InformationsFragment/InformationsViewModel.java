package ca.ezlock.it.ezpark.ui.InformationsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InformationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InformationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Information fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}