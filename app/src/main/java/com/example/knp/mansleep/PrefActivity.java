package com.example.knp.mansleep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import java.io.File;


/**
 * Created by knp on 3/28/17.
 */

public class PrefActivity extends PreferenceActivity {
    Preference spref;
    String TAG = "MTAG";
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: adsad");
        addPreferencesFromResource(R.xml.prefs);
        spref = findPreference("pref_song");
        final SharedPreferences sp = getSharedPreferences("mpref",MODE_PRIVATE);
        spref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.i(TAG, "onPreferenceClick: ueuuyu");

                File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");
                FileDialog fileDialog = new FileDialog(PrefActivity.this, mPath, ".mp3");
                fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                    public void fileSelected(File file) {
                        Log.d(getClass().getName(), "selected file " + file.toString());
                         //getPreferences(MODE_WORLD_WRITEABLE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("song_path",file.getPath());
                        editor.commit();

                    }
                });

                fileDialog.showDialog();
                return true;
            }
        });
        spref.setSummary(new File(sp.getString("song_path","")).getName());
    }
}
