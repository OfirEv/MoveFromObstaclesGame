package com.example.exe1;

import com.example.exe1.Fragments.fragmentActivity;
import com.google.android.material.textview.MaterialTextView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

public class startActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MaterialButton sensor_button;
    private MaterialButton arrows_button;
    private MaterialTextView startTextView;
    private MaterialTextView welcomeStartTextView;
    private MaterialTextView nameStartTextView;
    private AppCompatImageView trophy;
    private EditText editText;
    private String usersName;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        findView();
        initView();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();
    }

    private void findView() {
        sensor_button = findViewById(R.id.sensor_button);
        arrows_button = findViewById(R.id.arrows_button);
        editText = findViewById(R.id.editText);
        trophy = findViewById(R.id.trophy);
    }

    private void initView() {
        arrows_button.setOnClickListener(v -> changeActivity(1));
        sensor_button.setOnClickListener(v -> changeActivity(2));
        trophy.setOnClickListener(v -> recordTableActivity());
    }

    private void changeActivity(int choice) {
        usersName = editText.getText().toString();
        if (usersName.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_CHOICE, choice);
        intent.putExtra(MainActivity.KEY_NAME, usersName);
        intent.putExtra(MainActivity.KEY_LATITUDE, latitude);
        intent.putExtra(MainActivity.KEY_LONGITUDE, longitude);
        startActivity(intent);
        finish();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getUserLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            } else {
                                Toast.makeText(startActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void recordTableActivity() {
        Intent intent = new Intent(this, fragmentActivity.class);
        startActivity(intent);
        finish();
    }
}
