package com.leo.mvp.utils.recorder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

import java.nio.ByteBuffer;

/**
 * 截屏
 * <p>
 * 使用步骤：
 * 1.VideoCapturer videoCapturer = VideoCapturer.getInstance();
 * 2.videoCapturer.init(this);
 * 3.MediaProjectionManager manager = videoCapturer.getMediaProjectionManager();
 * 4.startActivityForResult(manager.createScreenCaptureIntent(), RecorderConstant.SCREEN_SHOT);
 * 5.videoCapturer.setVideoListener((bytes, width, height, ts) -> {
 * //推流
 * });
 * <p>
 * created by Leo on 2018/6/6 23 : 22
 */


public class VideoCapturer {
    private static final int MESSAGE_ID_RECONNECTING = 0x01;

    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mMediaProjection;
    private ImageReader mImageReader;
    private byte[] lastFrame;
    public OnDataSuccessListener mListener;
    private boolean isInit = false;

    private Activity mActivity;

    private static volatile VideoCapturer mVideoCapturer;

    private VideoCapturer() {
    }

    public static VideoCapturer getInstance() {
        synchronized (VideoCapturer.class) {

            if (mVideoCapturer == null) {
                synchronized (VideoCapturer.class) {
                    if (mVideoCapturer == null) {
                        mVideoCapturer = new VideoCapturer();
                    }
                }

            }
            return mVideoCapturer;
        }
    }

    /**
     * 截屏数据回调
     *
     * @author Leo
     * created at 2018/5/23 上午10:02
     */
    public interface OnDataSuccessListener {
        void onDataListener(byte[] bytes, int width, int height, long ts);
    }

    public void setVideoListener(OnDataSuccessListener listener) {


        mListener = listener;
    }

    private void init(Activity activity) {
        //1.初始化状态
        this.isInit = true;
        //2.赋值activity
        this.mActivity = activity;
        //
        mMediaProjectionManager = (MediaProjectionManager) activity.getSystemService(activity.MEDIA_PROJECTION_SERVICE);
    }

    public void setUpMediaProjection(int resultCod, Intent data) {
        mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCod, data);
    }

    public void setUpVirtualDisplay() {
        mImageReader = ImageReader.newInstance(RecorderConstant.width, RecorderConstant.height, PixelFormat.RGBA_8888, 24);
        mMediaProjection.createVirtualDisplay("ScreenShot",
                RecorderConstant.width, RecorderConstant.height, RecorderConstant.dpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }

    public void startCapture() {
        Image image = mImageReader.acquireLatestImage();

        byte[] nv21Bytes;
        if (image == null) {
            if (lastFrame != null) {
                nv21Bytes = lastFrame;
            } else {
                return;
            }
        } else {
            int width = image.getWidth();
            int height = image.getHeight();
            final Image.Plane[] planes = image.getPlanes();
            final ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);

            if (bitmap != null) {
                nv21Bytes = ConvertData.getNV21(width, height, bitmap);
                lastFrame = nv21Bytes;

                image.close();

                //录屏数据回调
                mListener.onDataListener(nv21Bytes, RecorderConstant.width, RecorderConstant.height, System.nanoTime());
            }


        }


    }

    public MediaProjectionManager getMediaProjectionManager() {
        return mMediaProjectionManager;
    }


}
