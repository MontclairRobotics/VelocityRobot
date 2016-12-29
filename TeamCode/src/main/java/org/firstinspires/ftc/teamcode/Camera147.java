package org.firstinspires.ftc.teamcode;


import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Camera147 {

    CameraManager manager;
    CameraDevice cam;
    boolean cameraReady = false;
    ImageReader reader;
    boolean isRed = true;

    public Camera147(CameraManager manager) {
        this.manager = manager;
        String camId = null;
        reader = ImageReader.newInstance(270, 480, ImageFormat.FLEX_RGB_888, 2);

        try {
            for(String id : manager.getCameraIdList()) {
                CameraCharacteristics info = manager.getCameraCharacteristics(id);
                if(info.get(CameraCharacteristics.LENS_FACING).equals(CameraMetadata.LENS_FACING_FRONT)) {
                    camId = id;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        
        if(camId == null) {
            return;
        }

        try {
            manager.openCamera(camId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(CameraDevice camera) {
                    cam = camera;
                    try {
                        cam.createCaptureSession(Arrays.asList(reader.getSurface()), new CameraCaptureSession.StateCallback() {
                            @Override
                            public void onConfigured(CameraCaptureSession session) {
                                try {
                                    session.setRepeatingRequest(cam.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).build(), new CameraCaptureSession.CaptureCallback() {
                                        @Override
                                        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                                            super.onCaptureCompleted(session, request, result);
                                        }
                                    }, null);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConfigureFailed(CameraCaptureSession session) {

                            }
                        }, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    cameraReady = true;
                }

                @Override
                public void onDisconnected(CameraDevice camera) {
                    cameraReady = false;
                }

                @Override
                public void onError(CameraDevice camera, int error) {
                    cameraReady = false;
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void process() {
        if(!cameraReady) {
            return;
        }
        Image i = reader.acquireLatestImage();
        int middlePixel = i.getPlanes()[0].getRowStride() * i.getHeight()/2 + i.getPlanes()[0].getPixelStride() * i.getWidth()/2;
        ByteBuffer red = i.getPlanes()[0].getBuffer();
        ByteBuffer blue = i.getPlanes()[1].getBuffer();

        double redVal = red.get(middlePixel);
        double blueVal = blue.get(middlePixel);

        isRed = redVal > blueVal;
    }

    public boolean isRed() {
        return isRed;
    }

}
