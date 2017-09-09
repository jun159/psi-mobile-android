package sg.edu.nus.baojun.psy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.widget.LinearLayout;
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

    @BindView(R.id.icon_status)
    TextView iconStatus;

    @BindView(R.id.label_pm10)
    TextView labelPm10;
    @BindView(R.id.label_pm25)
    TextView labelPm25;
    @BindView(R.id.label_o3)
    TextView labelO3;
    @BindView(R.id.label_co)
    TextView labelCo;
    @BindView(R.id.label_so2)
    TextView labelSo2;
    @BindView(R.id.label_no2)
    TextView labelNo2;

    @BindView(R.id.text_pm10_before)
    TextView textPm10Before;
    @BindView(R.id.text_pm25_before)
    TextView textPm25Before;
    @BindView(R.id.text_o3_before)
    TextView textO3Before;
    @BindView(R.id.text_co_before)
    TextView textCoBefore;
    @BindView(R.id.text_so2_before)
    TextView textSo2Before;
    @BindView(R.id.text_no2_before)
    TextView textNo2Before;

    @BindView(R.id.text_pm10_after)
    TextView textPm10After;
    @BindView(R.id.text_pm25_after)
    TextView textPm25After;
    @BindView(R.id.text_o3_after)
    TextView textO3After;
    @BindView(R.id.text_co_after)
    TextView textCoAfter;
    @BindView(R.id.text_so2_after)
    TextView textSo2After;
    @BindView(R.id.text_no2_after)
    TextView textNo2After;

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
            iconStatus.setBackgroundResource(getHealthStatusDrawable(psi));
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
        labelPm10.setText(superSubScript(getString(R.string.pm10_sub_index), SUBSCRIPT_TYPE_A));
        setCurrentPsiLevel(textPm10Before, textPm10After, Float.parseFloat(regionReadings.getPm10SubIndex()));

        labelPm25.setText(superSubScript(getString(R.string.pm25_sub_index), SUBSCRIPT_TYPE_A));
        setCurrentPsiLevel(textPm25Before, textPm25After, Float.parseFloat(regionReadings.getPm25SubIndex()));

        labelO3.setText(superSubScript(getString(R.string.o3_sub_index), SUBSCRIPT_TYPE_B));
        setCurrentPsiLevel(textO3Before, textO3After, Float.parseFloat(regionReadings.getO3SubIndex()));

        labelCo.setText(getString(R.string.co_sub_index));
        setCurrentPsiLevel(textCoBefore, textCoAfter, Float.parseFloat(regionReadings.getCoSubIndex()));

        labelSo2.setText(superSubScript(getString(R.string.so2_sub_index), SUBSCRIPT_TYPE_C));
        setCurrentPsiLevel(textSo2Before, textSo2After, Float.parseFloat(regionReadings.getSo2SubIndex()));

        labelNo2.setText(superSubScript(getString(R.string.no2_sub_index), SUBSCRIPT_TYPE_C));
        setCurrentPsiLevel(textNo2Before, textNo2After, Float.parseFloat(regionReadings.getNo2OneHourMax()));
    }

    private void setCurrentPsiLevel(TextView psiLevelBefore, TextView psiLevelAfter, float psi) {
        float ratioBefore = psi / VALUE_HAZARDOUS;
        float ratioAfter = (VALUE_HAZARDOUS - psi) / VALUE_HAZARDOUS;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = ratioAfter;
        psiLevelBefore.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = ratioBefore;
        psiLevelAfter.setLayoutParams(params);

        psiLevelAfter.setText("▲");
    }

    private int getHealthStatusDrawable(double psiLevel) {
        if(psiLevel <= 100) {
            return R.drawable.ic_thumb_up_white_24dp;
        }

        return R.drawable.ic_thumb_down_white_24dp;
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
