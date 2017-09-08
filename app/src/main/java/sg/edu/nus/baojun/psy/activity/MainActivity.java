package sg.edu.nus.baojun.psy.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.nus.baojun.psy.PsyApplication;
import sg.edu.nus.baojun.psy.R;
import sg.edu.nus.baojun.psy.network.GetPSIResponse;

public class MainActivity extends PsyActionBarActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    private static final double SINGAPORE_LATITUDE = 1.290270;
    private static final double SINGAPORE_LONGTITUDE = 103.851959;
    private static final double SINGAPORE_SOUTH_LATITUDE = 1.149600;
    private static final double SINGAPORE_WEST_LATITUDE = 103.594000;
    private static final double SINGAPORE_NORTH_LATITUDE = 1.478400;
    private static final double SINGAPORE_EAST_LATITUDE = 104.094500;
    private static final float ZOOM = 10.3F;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng singapore = new LatLng(SINGAPORE_LATITUDE, SINGAPORE_LONGTITUDE);
        LatLngBounds singaporeBounds = new LatLngBounds(
                new LatLng(SINGAPORE_SOUTH_LATITUDE, SINGAPORE_WEST_LATITUDE),
                new LatLng(SINGAPORE_NORTH_LATITUDE, SINGAPORE_EAST_LATITUDE));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(singapore, ZOOM));
        googleMap.setLatLngBoundsForCameraTarget(singaporeBounds);
        googleMap.setOnMarkerClickListener(this);

        getCurrentPSILevel();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        handleNetworkFailure();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        handleNetworkFailure();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void getCurrentPSILevel() {
        final PsyApplication application = getPsyApplication();

        Call<GetPSIResponse> call = application.getPsyService().getPsiByDateTime("2016-12-12T09:45:00");

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("Load PSI Success", response.message());
                } else {
                    handleAPICallFailure(response);
                }
            }

            @Override
            public void onFailure(Call<GetPSIResponse> call, Throwable t) {
                handleNetworkFailure();
            }
        };

        call.enqueue(callback);
    }
}
