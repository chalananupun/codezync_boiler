package com.codezync.boilerplate.Listeners;


import com.codezync.boilerplate.model.google_direction.GoogleDirectionResponse;

public interface OnDirectionFetchedListener {
    void onResult(GoogleDirectionResponse result);
}
