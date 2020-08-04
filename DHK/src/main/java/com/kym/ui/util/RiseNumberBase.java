package com.kym.ui.util;

public interface RiseNumberBase {
    void start();
    RiseNumberTextView withNumber(float number);
    RiseNumberTextView setDuration(long duration);
}
