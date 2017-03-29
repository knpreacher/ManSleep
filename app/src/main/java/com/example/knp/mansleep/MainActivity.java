package com.example.knp.mansleep;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Switch sw;
    SharedPreferences sp;
    MediaPlayer mp;
    private String TAG="TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("mpref",MODE_PRIVATE);
        sw = (Switch) findViewById(R.id.sw_start_service);

        //Toast.makeText(this, sp.getString("pref_song", ""), Toast.LENGTH_SHORT).show();
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mp = new MediaPlayer();
                    Log.i(TAG, "onCheckedChanged: "+sp.getString("song_path",""));
                    mp = MediaPlayer.create(getApplicationContext(),Uri.fromFile(new File(sp.getString("song_path",""))));
                    mp.setLooping(false);
                    mp.start();
                    Toast.makeText(MainActivity.this, "song is choosen", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(mp!=null){
                        mp.stop();
                        mp.release();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId()==R.id.menuSettingItem){
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.READ_EXTERNAL_STORAGE)
                     != PackageManager.PERMISSION_GRANTED) {

                 if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                         Manifest.permission.READ_EXTERNAL_STORAGE)) {
                 } else {
                     ActivityCompat.requestPermissions(this,
                             new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                             23232);

                 }
             } else {
                 Toast.makeText(this, "qwe", Toast.LENGTH_SHORT).show();
                 //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                 Intent intent = new Intent(MainActivity.this,PrefActivity.class);
                 //intent.setType("*/*");
                 startActivityForResult(intent,10023);
             }

         }
         return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10023)
            if(resultCode==RESULT_OK){
                String filePath = data.getData().getPath();
                Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==23232){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "qwe", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,PrefActivity.class);
                startActivityForResult(intent,10023);

            } else {
                //
            }
            return;
        }
    }
}
