package com.shofikul.androiddownloadexample;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shofikul.androiddownloadexample.utils.CheckForSDCard;
import com.shofikul.androiddownloadexample.utils.DownloadTask;
import com.shofikul.androiddownloadexample.utils.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static Button downloadPdf, downloadDoc, downloadZip, downloadVideo, downloadMp3, openDownloadedFolder,downloaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListeners();
    }

    //Initialize al Views
    private void initViews() {
        downloaImage = (Button) findViewById(R.id.downloaImage);
        downloadPdf = (Button) findViewById(R.id.downloadPdf);
        downloadDoc = (Button) findViewById(R.id.downloadDoc);
        downloadZip = (Button) findViewById(R.id.downloadZip);
        downloadVideo = (Button) findViewById(R.id.downloadVideo);
        downloadMp3 = (Button) findViewById(R.id.downloadMp3);
        openDownloadedFolder = (Button) findViewById(R.id.openDownloadedFolder);

    }

    //Set Listeners to Buttons
    private void setListeners() {
        downloaImage.setOnClickListener(this);
        downloadPdf.setOnClickListener(this);
        downloadDoc.setOnClickListener(this);
        downloadZip.setOnClickListener(this);
        downloadVideo.setOnClickListener(this);
        downloadMp3.setOnClickListener(this);
        openDownloadedFolder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Before starting any download check internet connection availability
        switch (view.getId()) {
            case R.id.downloaImage:
                if (isConnectingToInternet())
                    new DownloadTask(MainActivity.this, downloaImage, Utils.downloadIMGUrl,true);
                else
                    Toast.makeText(MainActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
                break;



            case R.id.downloadPdf:
                if (isConnectingToInternet())
                    new DownloadTask(MainActivity.this, downloadPdf, Utils.downloadPdfUrl,true);
                else
                    Toast.makeText(MainActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.downloadDoc:
                if (isConnectingToInternet())
                    new DownloadTask(MainActivity.this, downloadDoc, Utils.downloadDocUrl,false);
                else
                    Toast.makeText(MainActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.downloadZip:
                if (isConnectingToInternet())
                    new DownloadTask(MainActivity.this, downloadZip, Utils.downloadZipUrl,false);
                else
                    Toast.makeText(MainActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.downloadVideo:
                if (isConnectingToInternet())
                    new DownloadTask(MainActivity.this, downloadVideo, Utils.downloadVideoUrl,false);
                else
                    Toast.makeText(MainActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.downloadMp3:
                if (isConnectingToInternet())
                    new DownloadTask(MainActivity.this, downloadMp3, Utils.downloadMp3Url,false);
                else
                    Toast.makeText(MainActivity.this, "Oops!! There is no internet connection. Please enable internet connection and try again.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.openDownloadedFolder:
                openDownloadedFolder();
                break;

        }

    }

    //Open downloaded folder
    private void openDownloadedFolder() {
        //First check if SD Card is present or not
        if (new CheckForSDCard().isSDCardPresent()) {

            //Get Download Directory File
            File apkStorage = new File(
                    Environment.getExternalStorageDirectory() + "/"
                            + Utils.downloadDirectory);

            //If file is not present then display Toast
            if (!apkStorage.exists())
                Toast.makeText(MainActivity.this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();

            else {

                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/" + Utils.downloadDirectory);
                intent.setDataAndType(uri, "file/*");
                startActivity(Intent.createChooser(intent, "Open Download Folder"));
            }

        } else
            Toast.makeText(MainActivity.this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

    }

    //Check if internet is present or not
    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


}
