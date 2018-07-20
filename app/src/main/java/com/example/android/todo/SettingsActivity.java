package com.example.android.todo;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switchButton = findViewById(R.id.switchONOFF);
        pref = getApplicationContext().getSharedPreferences( "MyPref" , MODE_PRIVATE);
        Boolean value = pref.getBoolean( "SwitchON" , false );
        if(value==true){
            switchButton.setChecked(true);
        }
        else if(value==false){
            switchButton.setChecked(false);
        }

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {

                    if (!(ActivityCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(SettingsActivity.this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED)) {

                        String[] permissions = {Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
                        ActivityCompat.requestPermissions(SettingsActivity.this, permissions, 1011);
                    }

                }
                else if(isChecked==false){
                    pref = getApplicationContext().getSharedPreferences( "MyPref" , MODE_PRIVATE);
                    SharedPreferences.Editor editor;
                    editor = pref.edit();
                    editor.putBoolean( "SwitchON" , false );
                    editor.apply();

                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1011) {

            int smsReadPermission = grantResults[0];
            int smsReceivePermission = grantResults[1];

            if (smsReadPermission == PackageManager.PERMISSION_GRANTED && smsReceivePermission == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permissions Granted!", Toast.LENGTH_SHORT).show();
                pref = getApplicationContext().getSharedPreferences( "MyPref" , MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = pref.edit();
                editor.putBoolean( "SwitchON" , true );
                editor.apply();
            } else {
                pref = getApplicationContext().getSharedPreferences( "MyPref" , MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = pref.edit();
                editor.putBoolean( "SwitchON" , false );
                editor.apply();

                Toast.makeText(this, "Grant Permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
