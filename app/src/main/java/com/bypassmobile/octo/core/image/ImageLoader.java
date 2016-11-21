package com.bypassmobile.octo.core.image;


import android.content.Context;

import com.squareup.picasso.Picasso;

public final class ImageLoader {

    private static Picasso singleton;

    public static Picasso createImageLoader(Context context){
        if(singleton == null){
            singleton = new Picasso.Builder(context).memoryCache(new ImageCache()).build();
        }

        return singleton;
    }
}
