package com.example.dog_date.Models;

public interface OnGetDataListener<T> {
    void onSuccess(T dataResponse);
    void onFailure();
}
