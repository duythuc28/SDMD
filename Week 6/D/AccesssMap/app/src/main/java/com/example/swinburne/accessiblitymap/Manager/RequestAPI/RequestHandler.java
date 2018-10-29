package com.example.swinburne.accessiblitymap.Manager.RequestAPI;

public interface RequestHandler<T> {
    public void onResponse(T t);
    public void onFailure(String error);
}
