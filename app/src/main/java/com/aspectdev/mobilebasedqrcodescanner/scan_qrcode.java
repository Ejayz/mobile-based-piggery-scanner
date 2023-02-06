package com.aspectdev.mobilebasedqrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class scan_qrcode extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CAMERA=0;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Button btn_scan,btn_back_dashboard;
    private String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        previewView=findViewById(R.id.preview_camera);
        cameraProviderFuture=ProcessCameraProvider.getInstance(this);
        requestCamera();
        btn_scan=findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(scan_qrcode.this,view_information.class);
                i.putExtra("tag",qrCode);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"Tag Scanned:"+qrCode,Toast.LENGTH_SHORT).show();
                btn_scan.setVisibility(View.INVISIBLE);

            }
        });
        btn_back_dashboard=findViewById(R.id.btn_back_dashboard);
        btn_back_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(scan_qrcode.this,dashboard.class));
            }
        });
    }
    private void requestCamera(){
        if(ActivityCompat.checkSelfPermission(scan_qrcode.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(scan_qrcode.this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CAMERA);
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CAMERA);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_REQUEST_CAMERA){
            if(grantResults.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startCamera();
            }else{
                Toast.makeText(this,"Camera Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startCamera(){
    cameraProviderFuture.addListener(()->{
        try {
    ProcessCameraProvider cameraProvider= cameraProviderFuture.get();
    bindCameraPreview(cameraProvider);
        }catch (ExecutionException | InterruptedException e){
Toast.makeText(this,"Error starting camera"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }, ContextCompat.getMainExecutor(this));
    }
   public void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider){
previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);
Preview preview =new Preview.Builder()
        .build();
       CameraSelector cameraSelector=new CameraSelector.Builder()
               .requireLensFacing(CameraSelector.LENS_FACING_BACK)
               .build();
       preview.setSurfaceProvider(previewView.createSurfaceProvider());

       ImageAnalysis imageAnalysis=new ImageAnalysis.Builder()
               .setTargetResolution(new Size(1280,720))
               .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
               .build();
       imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this),new QRCodeAnalyzer(new QRCodeFoundListener() {
           @Override
           public void onQRCodeFound(String _qrCode) {
               qrCode=_qrCode;
               btn_scan.setVisibility(View.VISIBLE);
           }

           @Override
           public void qrCodeNotFound() {

           }
       }));


       Camera camera=cameraProvider.bindToLifecycle((LifecycleOwner) this,cameraSelector,imageAnalysis,preview);
    }

}