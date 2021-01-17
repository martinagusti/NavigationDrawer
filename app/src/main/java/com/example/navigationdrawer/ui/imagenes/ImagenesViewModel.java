package com.example.navigationdrawer.ui.imagenes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ImagenesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ImagenesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}