package com.codezync.boilerplate.Listeners;

public interface OnResponseListener<X> {
    void  onSuccessResponse(X response);
    void  onErrorResponse(String error);
    void  onNetworkFailureResponse(String error);

}
