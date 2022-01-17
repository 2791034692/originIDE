package com.myopicmobile.textwarrior.bean;

import android.graphics.Bitmap;

public class ListItem {
    private CharSequence charSequence, charSequence2 = null;
    private Bitmap bitmap;
    private String text;

    public ListItem(Bitmap bitmap, String text) {
        this.bitmap = bitmap;
        this.text = text;
    }

    public ListItem(CharSequence sequence, String text) {
        this.charSequence = sequence;
        this.text = text;
    }

    public ListItem(CharSequence sequence, String text, CharSequence charSequence) {
        this.charSequence = sequence;
        this.text = text;
        this.charSequence2 = charSequence;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public CharSequence getCharSequence2() {
        return charSequence2;
    }

    public void setCharSequence2(CharSequence charSequence2) {
        this.charSequence2 = charSequence2;
    }

    public CharSequence getCharSequence() {
        return charSequence;
    }

    public void setCharSequence(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}