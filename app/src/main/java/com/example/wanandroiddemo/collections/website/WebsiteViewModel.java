package com.example.wanandroiddemo.collections.website;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class WebsiteViewModel extends AndroidViewModel {
    private WebRepository repository;
    private MutableLiveData<Boolean> isWebAcquired;
    private NewWebDialog dialog;
    private WebAdapter webAdapter;
    public WebsiteViewModel(@NonNull Application application) {
        super(application);
        repository = new WebRepository(application , this);
        isWebAcquired = new MutableLiveData<>();
        isWebAcquired.setValue(false);
    }

    public WebRepository getRepository() {
        return repository;
    }

    public MutableLiveData<Boolean> getIsWebAcquired() {
        return isWebAcquired;
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
