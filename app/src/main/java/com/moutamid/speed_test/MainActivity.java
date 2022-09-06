package com.moutamid.speed_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jignesh13.speedometer.SpeedoMeterView;

import java.text.DateFormat;
import java.util.Calendar;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    SpeedoMeterView speedoMeterView;
    TextView textView;

    public static SQLiteHelper mSQLiteHelper;

    ImageView button_stop , button_history , button_start;
    GifImageView gif;

    TextView privacy_text;
    Button thumbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        speedoMeterView = findViewById(R.id.speedometerview);
        button_stop = findViewById(R.id.button_stop);
        button_history = findViewById(R.id.button_history);
        button_start = findViewById(R.id.button_start);
        gif = findViewById(R.id.gif);
        thumbs = findViewById(R.id.btn_thumbs);
        privacy_text = findViewById(R.id.privacy_policy);


        button_start.setVisibility(View.VISIBLE);

        mSQLiteHelper = new SQLiteHelper(this, "RECORDDB.sqlite", null, 1);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_start.setVisibility(View.GONE);
                button_stop.setVisibility(View.VISIBLE);
                gif.setVisibility(View.VISIBLE);
                new SpeedTestTask().execute();
            }
        });

        privacy_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.getterms.io/view/SPfNE/privacy/en-us"));
                startActivity(browserIntent);
            }
        });

        thumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , DonateActivity.class);
                startActivity(intent);
            }
        });

        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , MainActivity.class);
                startActivity(intent);
                speedoMeterView.setSpeed(0, false);

                button_start.setVisibility(View.VISIBLE);
                button_stop.setVisibility(View.GONE);
                gif.setVisibility(View.GONE);

                String myCurrent_dateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                try {
                    mSQLiteHelper.insertData(
                            textView.getText().toString().trim(),
                            myCurrent_dateTime
                    );
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , History_Check.class);
                startActivity(intent);
            }
        });

    }

    public class SpeedTestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            SpeedTestSocket speedTestSocket = new SpeedTestSocket();

            // add a listener to wait for speedtest completion and progress
            speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

                @Override
                public void onCompletion(SpeedTestReport report) {
                    // called when download/upload is finished
                    Log.v("speedtest", "[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
                    Log.v("speedtest", "[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());
                }

                @Override
                public void onError(SpeedTestError speedTestError, String errorMessage) {
                    // called when a download/upload error occur
                }

                @Override
                public void onProgress(float percent, SpeedTestReport report) {

                    final int deger = (int) report.getTransferRateBit().intValue()/100000;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            speedoMeterView.setSpeed(deger, true);
                            textView.setText(deger+ " Mbps");
                        }
                    });


                    // called to notify download/upload progress
                    Log.v("speedtest", "[PROGRESS] progress : " + percent + "%");
                    Log.v("speedtest", "[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
                    Log.v("speedtest", "[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
                }
            });

            speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/1M.iso");
            return null;
        }
    }
}