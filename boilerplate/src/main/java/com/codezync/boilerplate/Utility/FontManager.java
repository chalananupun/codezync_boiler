package com.codezync.boilerplate.Utility;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class FontManager {

    public Typeface reqular;
    public Typeface bold;
    public Typeface thin;
    TextView textView1, textView2;

    HashMap<String, MyFont> fontMap = new HashMap<>();


    public FontManager(Context context) {
        reqular = Typeface.createFromAsset(context.getAssets(), "proxima_nova_regular.otf");
        bold = Typeface.createFromAsset(context.getAssets(), "proxima_nova_bold.otf");
        thin = Typeface.createFromAsset(context.getAssets(), "proxima_nova_thin.otf");
    }

    public void m(TextView ar[]) {
    }
}


class MyFont {
    private List<FontCollection> fonts;

    public MyFont(List<FontCollection> fonts) {
        this.fonts = fonts;
    }

    public List<FontCollection> getFonts() {
        return fonts;
    }

    public void setFonts(List<FontCollection> fonts) {
        this.fonts = fonts;
    }
}


class FontCollection {

    String fontName, path, style;

    public FontCollection(String fontName, String path, String style) {
        this.fontName = fontName;
        this.path = path;
        this.style = style;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
