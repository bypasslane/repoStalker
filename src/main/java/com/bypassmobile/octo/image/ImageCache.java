package com.bypassmobile.octo.image;


import android.graphics.Bitmap;

import com.squareup.picasso.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class ImageCache implements Cache{

    private Map<String,Bitmap> cacheMap = new LinkedHashMap<String,Bitmap>();

    @Override
    public Bitmap get(String stringResource) {
        return cacheMap.get(stringResource);
    }

    @Override
    public void set(String stringResource, Bitmap bitmap) {
        cacheMap.put(stringResource,bitmap);
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public int maxSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }
}
