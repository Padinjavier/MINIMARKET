package com.example.minimarket.ui.ingreso;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IngresoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public IngresoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");



    }

    public LiveData<String> getText() {
        return mText;
    }


}