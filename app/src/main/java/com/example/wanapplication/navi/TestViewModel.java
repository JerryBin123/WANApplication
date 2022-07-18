package com.example.wanapplication.navi;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {
    private MutableLiveData<Integer> page = new MutableLiveData<>();

    public MutableLiveData<Integer> getPage() {
        return page;
    }
}
