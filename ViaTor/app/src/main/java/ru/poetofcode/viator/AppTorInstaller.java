package ru.poetofcode.viator;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AppTorInstaller extends AndroidTorInstaller {
    public AppTorInstaller(Context context, File configDir) {
        super(context, configDir);
    }

    @Override
    public InputStream openBridgesStream() throws IOException {
        return null;
    }
}
