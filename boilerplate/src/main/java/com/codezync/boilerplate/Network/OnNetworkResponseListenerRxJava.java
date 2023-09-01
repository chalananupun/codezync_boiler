package com.codezync.boilerplate.Network;

// Network class to handle api request
public interface OnNetworkResponseListenerRxJava<T,X> {
    void  onSuccessResponse(T response);
    void  onErrorResponse(X error);
    void  onNetworkErrorResponse(String error);

}

