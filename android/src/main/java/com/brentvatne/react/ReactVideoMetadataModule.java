package com.brentvatne.react;

import android.graphics.Bitmap;
import android.media.MediaExtractor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by shan on 10/21/16.
 */

public class ReactVideoMetadataModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private final Object mCallerContext;

    public ReactVideoMetadataModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mCallerContext = this;
    }

    public ReactVideoMetadataModule(ReactApplicationContext reactContext, Object callerContext) {
        super(reactContext);
        mCallerContext = callerContext;
    }

    @Override
    public String getName() {
        return "VideoManager";
    }

    @Override
    public void onHostResume() {
        // Activity `onResume`
    }

    @Override
    public void onHostPause() {
        // Activity `onPause`
    }

    @Override
    public void onHostDestroy() {
        // Activity `onDestroy`
    }

    @ReactMethod
    public void getFPS(
            String videoSrc,
            Callback callback) {
        MediaExtractor extractor = new MediaExtractor();
        try {
            extractor.setDataSource(getReactApplicationContext().getApplicationContext(), Uri.parse(videoSrc), null);

            extractor.advance();
Log.i("sportsmetrics", "getFPS getSampleTime: "+extractor.getSampleTime());
            float fps = 1000000f / (float) extractor.getSampleTime();

            callback.invoke(null,fps);
        } catch (IOException e) {
            callback.invoke(e.getMessage());
        } catch (RuntimeException e) {
            callback.invoke(e.getMessage());
        } finally {
            try {
                extractor.release();
            } catch (RuntimeException ex) {
            }
        }
    }

    @ReactMethod
    public void getFrameForSeconds(
            float seekTime,
            String videoSrc,
            Callback callback) {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        FileOutputStream out = null;

        try {
            retriever.setDataSource(getReactApplicationContext().getApplicationContext(), Uri.parse(videoSrc));

            Log.i("sportsmetrics", "getFrameForSeconds: "+(seekTime*1000000));

            Bitmap b = retriever.getFrameAtTime((long)(seekTime*1000000),MediaMetadataRetriever.OPTION_CLOSEST);

            try {
                File outputDir = getReactApplicationContext().getCacheDir();
                File dest = File.createTempFile("single-frame-", ".jpg", outputDir);

                out = new FileOutputStream(dest);
                b.compress(Bitmap.CompressFormat.JPEG, 100, out);

                out.flush();
                out.close();

                b.recycle();

                callback.invoke(null,dest.getAbsolutePath());
            } catch (Exception e) {
                callback.invoke(e.getMessage());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    callback.invoke(e.getMessage());
                }            }
        } catch (IllegalArgumentException e) {
            callback.invoke(e.getMessage());
        } catch (RuntimeException e) {
            callback.invoke(e.getMessage());
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
    }

    @ReactMethod
    public void getThumbForSeconds(
            float seekTime,
            String videoSrc,
            int height,
            int width,
            Callback callback) {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        FileOutputStream out = null;

        try {
            retriever.setDataSource(getReactApplicationContext().getApplicationContext(), Uri.parse(videoSrc));

            Log.i("sportsmetrics", "getThumbForSeconds: "+(seekTime*1000000));

            Bitmap b = retriever.getFrameAtTime((long)(seekTime*1000000),MediaMetadataRetriever.OPTION_CLOSEST);

            try {
                File outputDir = getReactApplicationContext().getCacheDir();
                File dest = File.createTempFile("single-frame-", ".jpg", outputDir);

                b = Bitmap.createScaledBitmap(b,width,height,false);

                out = new FileOutputStream(dest);
                b.compress(Bitmap.CompressFormat.JPEG, 100, out);

                out.flush();
                out.close();

                b.recycle();

                callback.invoke(null,dest.getAbsolutePath());
            } catch (Exception e) {
                callback.invoke(e.getMessage());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    callback.invoke(e.getMessage());
                }            }
        } catch (IllegalArgumentException e) {
            callback.invoke(e.getMessage());
        } catch (RuntimeException e) {
            callback.invoke(e.getMessage());
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
    }
}
