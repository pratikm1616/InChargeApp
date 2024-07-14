package com.Inchargenext.loginactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Inchargenext.loginactivity.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageButton btnback;
    int distance = 5000;
    private GoogleMap mGoogleMap;
    private AppPermissions appPermissions;
    private boolean isLocationPermissionOk;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private Marker currentMarker;
    private ActivityMapsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        appPermissions = new AppPermissions();
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        setContentView(binding.getRoot());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (appPermissions.isLocationOk(getApplicationContext())) {
            isLocationPermissionOk = true;
            setUpGoogleMap();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(getApplicationContext())
                    .setTitle("Location Permission")
                    .setMessage("InCharge requires location permission")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestLocation();
                        }
                    })
                    .create().show();
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_BACKGROUND_LOCATION}, AllConstant.LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AllConstant.LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionOk = true;
                setUpGoogleMap();
            } else {
                isLocationPermissionOk = false;
                Toast.makeText(getApplicationContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpGoogleMap() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
        setUpLocationUpdate();
    }

    private void setUpLocationUpdate() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        Log.d("TAG", "onLocationResult: " + location.getLongitude() + " " + location.getLatitude());
                    }
                }
                super.onLocationResult(locationResult);
            }
        };
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Location updated started", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation = location;
                moveCameraToLocation(location);
            }
        });
    }

    private void moveCameraToLocation(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        if (currentMarker != null) {
            currentMarker.remove();
        }
        currentMarker = mGoogleMap.addMarker(markerOptions);
        currentMarker.setTag(703);
        mGoogleMap.animateCamera(cameraUpdate);

        float[] results = new float[1];

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.526755942966794, 73.83006286664099,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.526755942966794, 73.83006286664099))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.553842246784384, 73.80660792197605,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.553842246784384, 73.80660792197605))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.567534670213465, 73.81188687843914,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.567534670213465, 73.81188687843914))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.5240548964857, 73.84191999300639,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.5240548964857, 73.84191999300639))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.523252049383128, 73.84762601988427,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.523252049383128, 73.84762601988427))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.55708432290174, 73.79308577306682,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.55708432290174, 73.79308577306682))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.565409362342344, 73.799252924703,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.565409362342344, 73.799252924703))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.562990943383923, 73.77933223452453,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.562990943383923, 73.77933223452453))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.511436531644154, 73.829399849603,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.511436531644154, 73.829399849603))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.516678687496498, 73.84785857082464,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.516678687496498, 73.84785857082464))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.516970434109606, 73.85109281839344,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.516970434109606, 73.85109281839344))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.515672857627553, 73.85500194104272,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.515672857627553, 73.85500194104272))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.52684448883289, 73.86577349618831,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.52684448883289, 73.86577349618831))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.54038872900504, 73.88250006409635,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.54038872900504, 73.88250006409635))
                    .title("EV Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        //BY PRATIK
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.513994000857807, 73.8607495354819,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.513994000857807, 73.8607495354819))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.527852528934677, 73.84822196551649,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.527852528934677, 73.84822196551649
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.487263670040623, 73.86857926671027,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.487263670040623, 73.86857926671027
                    ))
                    .title("Ather Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.490233912826433, 73.85709566090866,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.490233912826433, 73.85709566090866
                    ))
                    .title("Kazam Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.50805428799173, 73.87327710544729,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.50805428799173, 73.87327710544729
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.45395778072481, 73.82253487809675,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.45395778072481, 73.82253487809675
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.46702338552186, 73.80433298561329,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.46702338552186, 73.80433298561329
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.4575584117622, 73.8174250226849,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.4575584117622, 73.8174250226849
                    ))
                    .title("Go Easy Smart Technology Pvt Ltd")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.477362488969376, 73.83099655681409,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.477362488969376, 73.83099655681409
                    ))
                    .title("Evigo Charge Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.47340185642895, 73.85605169674487,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.47340185642895, 73.85605169674487
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.45310217941402, 73.87588701585675,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.45310217941402, 73.87588701585675
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.469936227923696, 73.88893656790404,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.469936227923696, 73.88893656790404
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                18.486273577670882, 73.82003493309436,
                results);
        if (results[0] <= distance) {
            //DUMMY MARKER
            MarkerOptions dummyMarker = new MarkerOptions()
                    .position(new LatLng(18.486273577670882, 73.82003493309436
                    ))
                    .title("Electric Vehicle Charging Station")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(dummyMarker);
        }

    }

    private void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        Log.d("TAG", "stopLocationUpdate: Location Update stop");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationProviderClient != null)
            stopLocationUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fusedLocationProviderClient != null) {
            startLocationUpdates();
            if (currentMarker != null) {
                currentMarker.remove();
            }
        }
    }
}