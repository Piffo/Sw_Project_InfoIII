package com.bc.bookcrossing.bookcrossing.GUI.Observer;

import com.bc.bookcrossing.bookcrossing.Structures.SignInStatus;

import java.util.List;

public interface ObserverDataSignIn extends ObserverForUiInformation {
    public void callbackSignIn(List<SignInStatus> status);
}
