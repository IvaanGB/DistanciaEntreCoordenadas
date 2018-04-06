package gpsdistance.com.gpsdistance;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION=1;
    private TextView latEnd, lonEnd, metros;
    private EditText latSt, lonSt;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        latSt = findViewById(R.id.txtLatitud);
        lonSt = findViewById(R.id.txtLongitud);
        metros = findViewById(R.id.metros);
        latEnd = findViewById(R.id.tvLatitud);
        lonEnd = findViewById(R.id.tvLongitud);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationChanged(Location location) {
                if (Metodos.validar(latSt, lonSt)) {
                    Metodos.mayorA50(MainActivity.this, metros, latSt, lonSt, latEnd, lonEnd);
                    latEnd.setText("" + location.getLatitude());
                    lonEnd.setText("" + location.getLongitude());
                }else {
                    metros.setText("0.00");
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        getLocation();
    }



    @SuppressLint("SetTextI18n")
    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null){
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);

                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latEnd.setText(""+latti);
                lonEnd.setText(""+longi);
            }
        }
    }

}