package com.me.sample.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public LiveData<String> failed;
}
