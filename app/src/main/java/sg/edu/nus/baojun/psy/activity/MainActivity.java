package sg.edu.nus.baojun.psy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import sg.edu.nus.baojun.psy.model.PSIItem;
import sg.edu.nus.baojun.psy.model.PSIReading;
import sg.edu.nus.baojun.psy.model.PSIRegionReadings;
import sg.edu.nus.baojun.psy.model.RegionMetadata;
import sg.edu.nus.baojun.psy.network.GetPSIResponse;
import sg.edu.nus.baojun.psy.utils.FormatString;

public class MainActivity extends PsyActionBarActivity implements
        View.OnClickListener,
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

    @BindView(R.id.text_last_updated) TextView textLastUpdated;
    @BindView(R.id.activity_title) TextView activityTitle;
    @BindView(R.id.icon_info) RelativeLayout iconInfo;

    @BindView(R.id.layout_psi_west) LinearLayout layoutPSIWest;
    @BindView(R.id.layout_psi_north) LinearLayout layoutPSINorth;
    @BindView(R.id.layout_psi_central) LinearLayout layoutPSICentral;
    @BindView(R.id.layout_psi_south) LinearLayout layoutPSISouth;
    @BindView(R.id.layout_psi_east) LinearLayout layoutPSIEast;

    @BindView(R.id.label_psi_west) TextView labelPSIWest;
    @BindView(R.id.label_psi_north) TextView labelPSINorth;
    @BindView(R.id.label_psi_central) TextView labelPSICentral;
    @BindView(R.id.label_psi_south) TextView labelPSISouth;
    @BindView(R.id.label_psi_east) TextView labelPSIEast;

    @BindView(R.id.text_psi_west) TextView textPSIWest;
    @BindView(R.id.text_psi_north) TextView textPSINorth;
    @BindView(R.id.text_psi_central) TextView textPSICentral;
    @BindView(R.id.text_psi_south) TextView textPSISouth;
    @BindView(R.id.text_psi_east) TextView textPSIEast;

    private GoogleMap map;
    private Marker marker;
    private PSIItem psiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        activityTitle.setText(getString(R.string.title_singapore_psi));
        layoutPSIWest.setOnClickListener(this);
        layoutPSINorth.setOnClickListener(this);
        layoutPSICentral.setOnClickListener(this);
        layoutPSISouth.setOnClickListener(this);
        layoutPSIEast.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iconInfo.setOnClickListener(this);
        textLastUpdated.setVisibility(View.GONE);
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

    public void alertDialogLegend() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.title_alert_legend));
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_legend, null));

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
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

    private void setPsiReadingText(PSIReading psiReading) {
        textPSIWest.setText(String.valueOf(psiReading.getWest()));
        textPSINorth.setText(String.valueOf(psiReading.getNorth()));
        textPSICentral.setText(String.valueOf(psiReading.getCentral()));
        textPSISouth.setText(String.valueOf(psiReading.getSouth()));
        textPSIEast.setText(String.valueOf(psiReading.getEast()));
    }

    private void getCurrentPSILevel() {
        final PsyApplication application = getPsyApplication();

        Call<GetPSIResponse> call = application.getPsyService().getPsiByDateTime(application.getDateTime());

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {
                textLastUpdated.setVisibility(View.VISIBLE);

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

                        psiItem = psiResponse.getPsiItemList().get(0);
                        PSIReading psiReading = psiItem.getPsiReadings().getPsiTwentyFourHourly();
                        setPsiReadingText(psiReading);

                        activityTitle.setText(String.format(getString(R.string.title_national_psi),
                                String.valueOf(psiReading.getNational()),
                                FormatString.camelCase(psiResponse.getApiInfo().getStatus())));

                        textLastUpdated.setVisibility(View.VISIBLE);
                        textLastUpdated.setText(application.getCurrentTime());
                    } else {
                        handleAPICallFailure(response);
                        if(textLastUpdated.getText().toString().isEmpty()) {
                            textLastUpdated.setVisibility(View.GONE);
                        } else {
                            textLastUpdated.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    handleAPICallFailure(response);
                    if(textLastUpdated.getText().toString().isEmpty()) {
                        textLastUpdated.setVisibility(View.GONE);
                    } else {
                        textLastUpdated.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPSIResponse> call, Throwable t) {
                handleNetworkFailure();
                if(textLastUpdated.getText().toString().isEmpty()) {
                    textLastUpdated.setVisibility(View.GONE);
                } else {
                    textLastUpdated.setVisibility(View.VISIBLE);
                }
            }
        };

        call.enqueue(callback);
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

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.icon_info) {
            alertDialogLegend();
        } else if(map != null && psiItem != null) {
            String region = "";
            String o3SubIndex = "";
            String pm10TwentyFourHourly = "";
            String pm10SubIndex = "";
            String coSubIndex = "";
            String pm25TwentyFourHourly = "";
            String so2SubIndex = "";
            String coEightHourMax = "";
            String no2OneHourMax = "";
            String so2TwentyFourHourly = "";
            String pm25SubIndex = "";
            String psiTwentyFourHourly = "";
            String o3EightHourMax = "";

            switch (view.getId()) {
                case R.id.layout_psi_west: {
                    region = PSIReading.WEST;
                    o3SubIndex = String.valueOf(psiItem.getPsiReadings().getO3SubIndex().getWest());
                    pm10TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm10TwentyFourHourly().getWest());
                    pm10SubIndex = String.valueOf(psiItem.getPsiReadings().getPm10SubIndex().getWest());
                    coSubIndex = String.valueOf(psiItem.getPsiReadings().getCoSubIndex().getWest());
                    pm25TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm25TwentyFourHourly().getWest());
                    so2SubIndex = String.valueOf(psiItem.getPsiReadings().getSo2SubIndex().getWest());
                    coEightHourMax = String.valueOf(psiItem.getPsiReadings().getCoEightHourMax().getWest());
                    no2OneHourMax = String.valueOf(psiItem.getPsiReadings().getNo2OneHourMax().getWest());
                    so2TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getSo2TwentyFourHourly().getWest());
                    pm25SubIndex = String.valueOf(psiItem.getPsiReadings().getPm25SubIndex().getWest());
                    psiTwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPsiTwentyFourHourly().getWest());
                    o3EightHourMax = String.valueOf(psiItem.getPsiReadings().getO3EightHourMax().getWest());
                    break;
                }

                case R.id.layout_psi_north: {
                    region = PSIReading.NORTH;
                    o3SubIndex = String.valueOf(psiItem.getPsiReadings().getO3SubIndex().getNorth());
                    pm10TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm10TwentyFourHourly().getNorth());
                    pm10SubIndex = String.valueOf(psiItem.getPsiReadings().getPm10SubIndex().getNorth());
                    coSubIndex = String.valueOf(psiItem.getPsiReadings().getCoSubIndex().getNorth());
                    pm25TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm25TwentyFourHourly().getNorth());
                    so2SubIndex = String.valueOf(psiItem.getPsiReadings().getSo2SubIndex().getNorth());
                    coEightHourMax = String.valueOf(psiItem.getPsiReadings().getCoEightHourMax().getNorth());
                    no2OneHourMax = String.valueOf(psiItem.getPsiReadings().getNo2OneHourMax().getNorth());
                    so2TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getSo2TwentyFourHourly().getNorth());
                    pm25SubIndex = String.valueOf(psiItem.getPsiReadings().getPm25SubIndex().getNorth());
                    psiTwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPsiTwentyFourHourly().getNorth());
                    o3EightHourMax = String.valueOf(psiItem.getPsiReadings().getO3EightHourMax().getNorth());
                    break;
                }

                case R.id.layout_psi_central: {
                    region = PSIReading.CENTRAL;
                    o3SubIndex = String.valueOf(psiItem.getPsiReadings().getO3SubIndex().getCentral());
                    pm10TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm10TwentyFourHourly().getCentral());
                    pm10SubIndex = String.valueOf(psiItem.getPsiReadings().getPm10SubIndex().getCentral());
                    coSubIndex = String.valueOf(psiItem.getPsiReadings().getCoSubIndex().getCentral());
                    pm25TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm25TwentyFourHourly().getCentral());
                    so2SubIndex = String.valueOf(psiItem.getPsiReadings().getSo2SubIndex().getCentral());
                    coEightHourMax = String.valueOf(psiItem.getPsiReadings().getCoEightHourMax().getCentral());
                    no2OneHourMax = String.valueOf(psiItem.getPsiReadings().getNo2OneHourMax().getCentral());
                    so2TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getSo2TwentyFourHourly().getCentral());
                    pm25SubIndex = String.valueOf(psiItem.getPsiReadings().getPm25SubIndex().getCentral());
                    psiTwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPsiTwentyFourHourly().getCentral());
                    o3EightHourMax = String.valueOf(psiItem.getPsiReadings().getO3EightHourMax().getCentral());
                    break;
                }

                case R.id.layout_psi_south: {
                    region = PSIReading.SOUTH;
                    o3SubIndex = String.valueOf(psiItem.getPsiReadings().getO3SubIndex().getSouth());
                    pm10TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm10TwentyFourHourly().getSouth());
                    pm10SubIndex = String.valueOf(psiItem.getPsiReadings().getPm10SubIndex().getSouth());
                    coSubIndex = String.valueOf(psiItem.getPsiReadings().getCoSubIndex().getSouth());
                    pm25TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm25TwentyFourHourly().getSouth());
                    so2SubIndex = String.valueOf(psiItem.getPsiReadings().getSo2SubIndex().getSouth());
                    coEightHourMax = String.valueOf(psiItem.getPsiReadings().getCoEightHourMax().getSouth());
                    no2OneHourMax = String.valueOf(psiItem.getPsiReadings().getNo2OneHourMax().getSouth());
                    so2TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getSo2TwentyFourHourly().getSouth());
                    pm25SubIndex = String.valueOf(psiItem.getPsiReadings().getPm25SubIndex().getSouth());
                    psiTwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPsiTwentyFourHourly().getSouth());
                    o3EightHourMax = String.valueOf(psiItem.getPsiReadings().getO3EightHourMax().getSouth());
                    break;
                }

                case R.id.layout_psi_east: {
                    region = PSIReading.EAST;
                    o3SubIndex = String.valueOf(psiItem.getPsiReadings().getO3SubIndex().getEast());
                    pm10TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm10TwentyFourHourly().getEast());
                    pm10SubIndex = String.valueOf(psiItem.getPsiReadings().getPm10SubIndex().getEast());
                    coSubIndex = String.valueOf(psiItem.getPsiReadings().getCoSubIndex().getEast());
                    pm25TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPm25TwentyFourHourly().getEast());
                    so2SubIndex = String.valueOf(psiItem.getPsiReadings().getSo2SubIndex().getEast());
                    coEightHourMax = String.valueOf(psiItem.getPsiReadings().getCoEightHourMax().getEast());
                    no2OneHourMax = String.valueOf(psiItem.getPsiReadings().getNo2OneHourMax().getEast());
                    so2TwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getSo2TwentyFourHourly().getEast());
                    pm25SubIndex = String.valueOf(psiItem.getPsiReadings().getPm25SubIndex().getEast());
                    psiTwentyFourHourly = String.valueOf(psiItem.getPsiReadings().getPsiTwentyFourHourly().getEast());
                    o3EightHourMax = String.valueOf(psiItem.getPsiReadings().getO3EightHourMax().getEast());
                    break;
                }

                default: {
                    break;
                }
            }

            PSIRegionReadings regionReadings = new PSIRegionReadings(
                    o3SubIndex, pm10TwentyFourHourly, pm10SubIndex,
                    coSubIndex, pm25TwentyFourHourly, so2SubIndex,
                    coEightHourMax, no2OneHourMax, so2TwentyFourHourly,
                    pm25SubIndex, psiTwentyFourHourly, o3EightHourMax);

            Intent intent = new Intent(this, RegionPSIActivity.class);
            intent.putExtra(RegionPSIActivity.KEY_REGION, region);
            intent.putExtra(RegionPSIActivity.KEY_REGION_PSI, regionReadings);
            startActivity(intent);
        }
    }
}
