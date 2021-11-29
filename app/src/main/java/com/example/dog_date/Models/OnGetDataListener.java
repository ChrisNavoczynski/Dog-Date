package com.example.dog_date.models;

public interface OnGetDataListener<T> {
    void onSuccess(T dataResponse);
    void onFailure();
}
