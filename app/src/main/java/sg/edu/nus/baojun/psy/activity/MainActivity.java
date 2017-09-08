package sg.edu.nus.baojun.psy.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.nus.baojun.psy.PsyApplication;
import sg.edu.nus.baojun.psy.R;
import sg.edu.nus.baojun.psy.model.PSIReading;
import sg.edu.nus.baojun.psy.model.RegionMetadata;
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

    @BindView(R.id.label_psi_west)
    TextView labelPSIWest;
    @BindView(R.id.label_psi_north)
    TextView labelPSINorth;
    @BindView(R.id.label_psi_central)
    TextView labelPSICentral;
    @BindView(R.id.label_psi_south)
    TextView labelPSISouth;
    @BindView(R.id.label_psi_east)
    TextView labelPSIEast;

    @BindView(R.id.text_psi_west)
    TextView textPSIWest;
    @BindView(R.id.text_psi_north)
    TextView textPSINorth;
    @BindView(R.id.text_psi_central)
    TextView textPSICentral;
    @BindView(R.id.text_psi_south)
    TextView textPSISouth;
    @BindView(R.id.text_psi_east)
    TextView textPSIEast;

    @BindView(R.id.activityTitle)
    TextView activityTitle;

    private GoogleMap map;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        activityTitle.setText(getString(R.string.title_singapore_psi));

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
        if (this.marker != null) {
            this.marker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }
        this.marker = marker;
        this.marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        String currentMarker = this.marker.getTitle();
        switch (currentMarker) {
            case PSIReading.WEST : {
                changeRegionSelectedColor(labelPSIWest, textPSIWest);
                changeRegionUnSelectedColor(labelPSINorth, textPSINorth);
                changeRegionUnSelectedColor(labelPSICentral, textPSICentral);
                changeRegionUnSelectedColor(labelPSISouth, textPSISouth);
                changeRegionUnSelectedColor(labelPSIEast, textPSIEast);
                break;
            }

            case PSIReading.NORTH : {
                changeRegionSelectedColor(labelPSINorth, textPSINorth);
                changeRegionUnSelectedColor(labelPSIWest, textPSIWest);
                changeRegionUnSelectedColor(labelPSICentral, textPSICentral);
                changeRegionUnSelectedColor(labelPSISouth, textPSISouth);
                changeRegionUnSelectedColor(labelPSIEast, textPSIEast);
                break;
            }

            case PSIReading.CENTRAL : {
                changeRegionSelectedColor(labelPSICentral, textPSICentral);
                changeRegionUnSelectedColor(labelPSIWest, textPSIWest);
                changeRegionUnSelectedColor(labelPSINorth, textPSINorth);
                changeRegionUnSelectedColor(labelPSISouth, textPSISouth);
                changeRegionUnSelectedColor(labelPSIEast, textPSIEast);
                break;
            }

            case PSIReading.SOUTH : {
                changeRegionSelectedColor(labelPSISouth, textPSISouth);
                changeRegionUnSelectedColor(labelPSIWest, textPSIWest);
                changeRegionUnSelectedColor(labelPSINorth, textPSINorth);
                changeRegionUnSelectedColor(labelPSICentral, textPSICentral);
                changeRegionUnSelectedColor(labelPSIEast, textPSIEast);
                break;
            }

            case PSIReading.EAST : {
                changeRegionSelectedColor(labelPSIEast, textPSIEast);
                changeRegionUnSelectedColor(labelPSIWest, textPSIWest);
                changeRegionUnSelectedColor(labelPSINorth, textPSINorth);
                changeRegionUnSelectedColor(labelPSICentral, textPSICentral);
                changeRegionUnSelectedColor(labelPSISouth, textPSISouth);
                break;
            }

            default: {
                break;
            }
        }

        return true;
    }

    private void changeRegionUnSelectedColor(TextView labelView, TextView textView) {
        labelView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        labelView.setTextColor(ContextCompat.getColor(this, R.color.white));
        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void changeRegionSelectedColor(TextView labelView, TextView textView) {
        labelView.setBackgroundColor(ContextCompat.getColor(this, R.color.white_three));
        labelView.setTextColor(ContextCompat.getColor(this, R.color.black));
        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.white_three));
        textView.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    private void getCurrentPSILevel() {
        final PsyApplication application = getPsyApplication();

        Call<GetPSIResponse> call = application.getPsyService().getPsiByDateTime("2016-12-12T09:45:00");

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("Load PSI Success", response.message());
                    GetPSIResponse psiResponse = response.body();

                    if(psiResponse != null && map != null) {
                        List<RegionMetadata> regionMetadataList = psiResponse.getRegionMetadataList();
                        for (RegionMetadata regionMetadata : regionMetadataList) {
                            double latitude = regionMetadata.getLabelLocation().getLatitude();
                            double longitude = regionMetadata.getLabelLocation().getLongitude();

                            if(latitude != 0 && longitude != 0) {
                                LatLng region = new LatLng(latitude, longitude);
                                map.addMarker(new MarkerOptions().position(region).title(regionMetadata.getName()));
                            }
                        }

                        PSIReading psiReading = psiResponse.getPsiItemList().get(0).getPsiReadings().getPsiTwentyFourHourly();
                        textPSIWest.setText(String.valueOf(psiReading.getWest()));
                        textPSINorth.setText(String.valueOf(psiReading.getNorth()));
                        textPSICentral.setText(String.valueOf(psiReading.getCentral()));
                        textPSISouth.setText(String.valueOf(psiReading.getSouth()));
                        textPSIEast.setText(String.valueOf(psiReading.getEast()));

                        activityTitle.setText(String.format(getString(R.string.title_national_psi),
                                String.valueOf(psiReading.getNational())));
                    }
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
