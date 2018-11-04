package com.bypassmobile.octo.image;


import android.graphics.Bitmap;

import com.squareup.picasso.Cache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageCache implements Cache{

    private Map<String,Bitmap> cacheMap = new ExpiringHashMap<String,Bitmap>();

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

    @Override
    public void clearKeyUri(String keyPrefix) {
        for (String key: cacheMap.keySet()) {
            if (key.toLowerCase().startsWith(keyPrefix.toLowerCase())) {
                cacheMap.remove(key);
            }
        }
    }


    class ExpiringHashMap<K, V> extends ConcurrentHashMap<K, V> {

        private Map<K, Long> timeMap = new ConcurrentHashMap<>();
        private final long expiryInMillis = 60000;
        private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:SSS");

        private ExpiringHashMap() {
            initialize();
        }

        void initialize() {
            new CleanerThread().start();
        }

        @Override
        public V put(K key, V value) {
            Date date = new Date();
            timeMap.put(key, date.getTime());
            return super.put(key, value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            for (K key : m.keySet()) {
                put(key, m.get(key));
            }
        }

        @Override
        public V putIfAbsent(K key, V value) {
            if (!containsKey(key))
                return put(key, value);
            else
                return get(key);
        }

        class CleanerThread extends Thread {
            @Override
            public void run() {
                while (true) {
                    cleanMap();
                    try {
                        Thread.sleep(expiryInMillis);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void cleanMap() {
                long currentTime = new Date().getTime();
                for (K key : timeMap.keySet()) {
                    try {
                        if (currentTime > (timeMap.get(key) + expiryInMillis)) {
                            timeMap.remove(key);
                        }
                    } catch (NullPointerException e) { e.printStackTrace(); }
                }
            }
        }
    }
}
