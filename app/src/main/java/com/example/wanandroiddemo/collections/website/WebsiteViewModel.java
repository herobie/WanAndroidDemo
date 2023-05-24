package com.example.wanandroiddemo.collections.website;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class WebsiteViewModel extends AndroidViewModel {
    private WebRepository repository;
    private MutableLiveData<Boolean> isWebAcquired;
    private MutableLiveData<Boolean> isAdded;
    private NewWebDialog dialog;
    private WebAdapter webAdapter;
    public WebsiteViewModel(@NonNull Application application) {
        super(application);
        repository = new WebRepository(application , this);
    }

    public WebRepository getRepository() {
        return repository;
    }

    public MutableLiveData<Boolean> getIsWebAcquired() {
        if(isWebAcquired == null){
            isWebAcquired = new MutableLiveData<>();
            isWebAcquired.setValue(false);
        }
        return isWebAcquired;
    }

    public MutableLiveData<Boolean> getIsAdded() {
        if (isAdded == null){
            isAdded = new MutableLiveData<>();
            isAdded.setValue(false);
        }
        return isAdded;
    }

    public NewWebDialog getDialog(Context context) {
        if (dialog == null){
            dialog = new NewWebDialog(context , this);
        }
        return dialog;
    }

    public WebAdapter getWebAdapter() {
        if (webAdapter == null){
            webAdapter = new WebAdapter(this , getApplication());
        }
        return webAdapter;
    }
}
