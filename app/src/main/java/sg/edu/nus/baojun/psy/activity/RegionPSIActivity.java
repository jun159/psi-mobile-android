package sg.edu.nus.baojun.psy.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.nus.baojun.psy.R;
import sg.edu.nus.baojun.psy.model.PSIRegionReadings;
import sg.edu.nus.baojun.psy.utils.FormatString;

/**
 * Created by BAOJUN on 9/9/17.
 */

public class RegionPSIActivity extends PsyActionBarActivity {

    public static final String KEY_REGION = "KEY_REGION";
    public static final String KEY_REGION_PSI = "KEY_REGION_PSI";

    private static final int VALUE_HAZARDOUS = 300;
    private static final int PERCENTAGE = 100;

    private static final int SUBSCRIPT_TYPE_A = 0;
    private static final int SUBSCRIPT_TYPE_B = 1;
    private static final int SUBSCRIPT_TYPE_C = 2;

    @BindView(R.id.activityTitle)
    TextView activityTitle;
    @BindView(R.id.progress_status)
    ProgressBar progressStatus;
    @BindView(R.id.text_psi_region)
    TextView textPSIRegion;
    @BindView(R.id.text_psi_status)
    TextView textPSIStatus;

    @BindView(R.id.progress_pm10_sub_index)
    ProgressBar progressPm10SubIndex;
    @BindView(R.id.progress_pm25_sub_index)
    ProgressBar progressPm25SubIndex;
    @BindView(R.id.progress_o3_sub_index)
    ProgressBar progressO3SubIndex;
    @BindView(R.id.progress_co_sub_index)
    ProgressBar progressCoSubIndex;
    @BindView(R.id.progress_so2_sub_index)
    ProgressBar progressSo2SubIndex;
    @BindView(R.id.progress_no2_one_hour_max)
    ProgressBar progressNo2OneHourMax;

    @BindView(R.id.label_pm10_sub_index)
    TextView labelPm10SubIndex;
    @BindView(R.id.label_pm25_sub_index)
    TextView labelPm25SubIndex;
    @BindView(R.id.label_o3_sub_index)
    TextView labelO3SubIndex;
    @BindView(R.id.label_co_sub_index)
    TextView labelCoSubIndex;
    @BindView(R.id.label_so2_sub_index)
    TextView labelSo2SubIndex;
    @BindView(R.id.label_no2_sub_index)
    TextView labelNo2SubIndex;

    @BindView(R.id.text_pm10_sub_index)
    TextView textPm10SubIndex;
    @BindView(R.id.text_pm25_sub_index)
    TextView textPm25SubIndex;
    @BindView(R.id.text_o3_sub_index)
    TextView textO3SubIndex;
    @BindView(R.id.text_co_sub_index)
    TextView textCoSubIndex;
    @BindView(R.id.text_so2_sub_index)
    TextView textSo2SubIndex;
    @BindView(R.id.text_no2_one_hour_max)
    TextView textNo2OneHourMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_psi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        PSIRegionReadings regionReadings = getIntent().getExtras().getParcelable(KEY_REGION_PSI);
        String region = getIntent().getExtras().getString(KEY_REGION);

        if(region != null && regionReadings != null) {
            double psi = Double.parseDouble(regionReadings.getPsiTwentyFourHourly());
            activityTitle.setText(String.format(getString(R.string.title_region_psi), FormatString.camelCase(region)));
            progressStatus.setProgress((int) (psi * PERCENTAGE) / VALUE_HAZARDOUS);
            textPSIRegion.setText(regionReadings.getPsiTwentyFourHourly());
            textPSIStatus.setText(String.format(getString(R.string.region_psi_status), determineStatus(psi)));
            setPSIValues(regionReadings);
        } else {
            activityTitle.setText(getString(R.string.title_singapore_psi));
        }
    }

    private SpannableStringBuilder superSubScript(String chemical, int subscriptType) {
        SpannableStringBuilder cs = new SpannableStringBuilder(chemical);

        switch (subscriptType) {
            case SUBSCRIPT_TYPE_A : {
                cs.setSpan(new SubscriptSpan(), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(0.75f), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            case SUBSCRIPT_TYPE_B : {
                cs.setSpan(new SubscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(0.75f), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            case SUBSCRIPT_TYPE_C : {
                cs.setSpan(new SubscriptSpan(), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(0.75f), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            default: {
                break;
            }
        }

        return cs;
    }

    private void setPSIValues(PSIRegionReadings regionReadings) {
        labelPm10SubIndex.setText(superSubScript(getString(R.string.pm10_sub_index), SUBSCRIPT_TYPE_A));
        progressPm10SubIndex.setProgress((int) Double.parseDouble(regionReadings.getPm10SubIndex()) * PERCENTAGE / VALUE_HAZARDOUS);
        textPm10SubIndex.setText(regionReadings.getPm10SubIndex());

        labelPm25SubIndex.setText(superSubScript(getString(R.string.pm25_sub_index), SUBSCRIPT_TYPE_A));
        progressPm25SubIndex.setProgress((int) Double.parseDouble(regionReadings.getPm25SubIndex()) * PERCENTAGE / VALUE_HAZARDOUS);
        textPm25SubIndex.setText(regionReadings.getPm25SubIndex());

        labelO3SubIndex.setText(superSubScript(getString(R.string.o3_sub_index), SUBSCRIPT_TYPE_B));
        progressO3SubIndex.setProgress((int) Double.parseDouble(regionReadings.getO3SubIndex()) * PERCENTAGE / VALUE_HAZARDOUS);
        textO3SubIndex.setText(regionReadings.getO3SubIndex());

        labelCoSubIndex.setText(getString(R.string.co_sub_index));
        progressCoSubIndex.setProgress((int) Double.parseDouble(regionReadings.getCoSubIndex()) * PERCENTAGE / VALUE_HAZARDOUS);
        textCoSubIndex.setText(regionReadings.getCoSubIndex());

        labelSo2SubIndex.setText(superSubScript(getString(R.string.so2_sub_index), SUBSCRIPT_TYPE_C));
        progressSo2SubIndex.setProgress((int) Double.parseDouble(regionReadings.getSo2SubIndex()) * PERCENTAGE / VALUE_HAZARDOUS);
        textSo2SubIndex.setText(regionReadings.getSo2SubIndex());

        labelNo2SubIndex.setText(superSubScript(getString(R.string.no2_sub_index), SUBSCRIPT_TYPE_C));
        progressNo2OneHourMax.setProgress((int) Double.parseDouble(regionReadings.getNo2OneHourMax()) * PERCENTAGE / VALUE_HAZARDOUS);
        textNo2OneHourMax.setText(regionReadings.getNo2OneHourMax());
    }

    private String determineStatus(double psiLevel) {
        if(psiLevel <= 50) {
            return getString(R.string.status_healthy);
        } else if(psiLevel > 50 && psiLevel <= 100) {
            return getString(R.string.status_moderate);
        } else if(psiLevel > 101 && psiLevel <= 200) {
            return getString(R.string.status_unhealthy);
        } else if(psiLevel > 201 && psiLevel <= 300) {
            return getString(R.string.status_very_unhealthy);
        }

        return getString(R.string.status_hazardous);
    }
}
