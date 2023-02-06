package com.aspectdev.mobilebasedqrcodescanner;

public interface QRCodeFoundListener {
    void onQRCodeFound(String qrCode);
    void qrCodeNotFound();
}
