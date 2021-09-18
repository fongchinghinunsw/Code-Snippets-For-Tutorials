package com.stephenfong.servercertificateverification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button verify_btn;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        verify_btn = findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyServer("https://www.drpepper.com/en");
            }
        });
    }

    private void verifyServer(String httpsURL) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    verify(httpsURL);
                    Log.d("DEBUG", "The certificate is valid");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CertificateNotYetValidException e) {
                    e.printStackTrace();
                } catch (CertificateExpiredException e) {
                    e.printStackTrace();
                    Log.d("DEBUG", "The certificate is invalid");
                }
            }
        }).start();
    }

    private void verify(String httpsURL) throws IOException, CertificateNotYetValidException, CertificateExpiredException {
        url = new URL(httpsURL);

        final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);

        con.connect();

        final Certificate[] certs = con.getServerCertificates();
        final Certificate subjectCert = certs[0];
        final Certificate rootCert = certs[certs.length-1];

        X509Certificate sc = (X509Certificate) subjectCert;
        X509Certificate rc = (X509Certificate) rootCert;

        printX509CertificateDetail(sc);
        printX509CertificateDetail(rc);

        sc.checkValidity();
    }

    private void printX509CertificateDetail(X509Certificate cert) {
        Log.d("DEBUG - Subject DN", cert.getSubjectX500Principal().toString());
        Log.d("DEBUG - Issuer DN", cert.getIssuerDN().toString());
        Log.d("DEBUG - Not After", cert.getNotAfter().toString());
        Log.d("DEBUG - Not Before", cert.getNotBefore().toString());
        Log.d("DEBUG - URL DN", url.getHost());
    }
}