package com.myopicmobile.textwarrior.interfaces;

public interface ProgressObserver {
    public void onComplete(int requestCode, Object result);

    public void onError(int requestCode, int errorCode, String message);

    public void onCancel(int requestCode);
}
