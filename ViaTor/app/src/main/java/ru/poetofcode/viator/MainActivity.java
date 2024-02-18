package ru.poetofcode.viator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.msopentech.thali.android.toronionproxy.AndroidOnionProxyManager;
import com.msopentech.thali.android.toronionproxy.AndroidTorConfig;
import com.msopentech.thali.toronionproxy.OnionProxyManager;
import com.msopentech.thali.toronionproxy.TorConfig;
import com.msopentech.thali.toronionproxy.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            startTor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.e("ViaTor", e.toString());
        }
    }

    void startTor() throws IOException, InterruptedException {
        String fileStorageLocation = "torfiles";

        copyFileTo(this, fileStorageLocation + "/torrc", getCacheDir().getAbsolutePath() + "/torrc");
        copyFileTo(this, fileStorageLocation + "/geoip", getCacheDir().getAbsolutePath() + "/geoip");
        copyFileTo(this, fileStorageLocation + "/geoip6", getCacheDir().getAbsolutePath() + "/geoip6");
        copyFileTo(this, fileStorageLocation + "/bridges.txt", getCacheDir().getAbsolutePath() + "/bridges.txt");

        TorConfig torConfig = AndroidTorConfig.createConfig(getFilesDir(), getCacheDir(), this.getApplicationContext());
        OnionProxyManager onionProxyManager =
                new AndroidOnionProxyManager(
                        this.getApplicationContext(),
                        torConfig,
                        new AppTorInstaller(
                                getApplicationContext(),
                                getCacheDir()
                        ),
                        null,
                        null,
                        null
                );

                // fileStorageLocation


        //new AndroidOnionProxyManager(getContext(), torConfig, new TestTorInstaller(getContext(), installDir),
        //                null, null, null);


        int totalSecondsPerTorStartup = 4 * 60;
        int totalTriesPerTorStartup = 5;

        // Start the Tor Onion Proxy
        if (onionProxyManager.startWithRepeat(totalSecondsPerTorStartup, totalTriesPerTorStartup, true) == false) {
            Log.e("TorTest", "Couldn't start Tor!");
            return;
        }

        // Start a hidden service listener
        int hiddenServicePort = 80;
        int localPort = 9343;
        String onionAddress = onionProxyManager.publishHiddenService(hiddenServicePort, localPort);

        // It can taken anywhere from 30 seconds to a few minutes for Tor to start properly routing
        // requests to to a hidden service. So you generally want to try to test connect to it a
        // few times. But after the previous call the Tor Onion Proxy will route any requests
        // to the returned onionAddress and hiddenServicePort to 127.0.0.1:localPort. So, for example,
        // you could just pass localPort into the NanoHTTPD constructor and have a HTTP server listening
        // to that port.

        // Connect via the TOR network
        // In this case we are trying to connect to the hidden service but any IP/DNS address and port can be
        // used here.
        Socket clientSocket =
                Utilities.socks4aSocketConnection(onionAddress, hiddenServicePort, "127.0.0.1", localPort);

        // Now the socket is open but note that it can take some time before the Tor network has everything
        // connected and connection requests can fail for spurious reasons (especially when connecting to
        // hidden services) so have lots of retry logic.
    }

    static public boolean copyFileTo(Context c, String orifile,
                                     String desfile) throws IOException {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(desfile);
        myInput = c.getAssets().open(orifile);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();

        return true;
    }
}