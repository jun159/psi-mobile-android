package sg.edu.nus.baojun.psy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.edu.nus.baojun.psy.R;
import sg.edu.nus.baojun.psy.model.PSIRegionReadings;
import sg.edu.nus.baojun.psy.model.enums.PSIStatus;
import sg.edu.nus.baojun.psy.utils.FormatString;

/**
 * Created by BAOJUN on 9/9/17.
 */

public class RegionPSIActivity extends PsyActionBarActivity implements View.OnClickListener {

    public static final String KEY_REGION = "KEY_REGION";
    public static final String KEY_REGION_PSI = "KEY_REGION_PSI";

    private static final int SUPERSCRIPT_TWO_SUBSCRIPT_TWO = 0;
    private static final int SUPERSCRIPT_TWO_SUBSCRIPT_THREE = 1;
    private static final int SUPERSCRIPT_ONE_SUBSCRIPT_ONE = 2;
    private static final int SUPERSCRIPT_TWO_SUBSCRIPT_ONE = 3;

    @BindView(R.id.activity_title) TextView activityTitle;
    @BindView(R.id.text_psi_region) TextView textPSIRegion;
    @BindView(R.id.text_psi_status) TextView textPSIStatus;
    @BindView(R.id.button_toggle) TextView buttonToggle;
    @BindView(R.id.icon_status) TextView iconStatus;
    @BindView(R.id.icon_info) RelativeLayout iconInfo;

    @BindView(R.id.layout_no2) LinearLayout layoutNo2;
    @BindView(R.id.divider_no2) View dividerNo2;

    @BindView(R.id.label_pm10) TextView labelPm10;
    @BindView(R.id.label_pm25) TextView labelPm25;
    @BindView(R.id.label_o3) TextView labelO3;
    @BindView(R.id.label_co) TextView labelCo;
    @BindView(R.id.label_so2) TextView labelSo2;
    @BindView(R.id.label_no2) TextView labelNo2;

    @BindView(R.id.text_pm10_before) TextView textPm10Before;
    @BindView(R.id.text_pm25_before) TextView textPm25Before;
    @BindView(R.id.text_o3_before) TextView textO3Before;
    @BindView(R.id.text_co_before) TextView textCoBefore;
    @BindView(R.id.text_so2_before) TextView textSo2Before;
    @BindView(R.id.text_no2_before) TextView textNo2Before;

    @BindView(R.id.text_pm10_after) TextView textPm10After;
    @BindView(R.id.text_pm25_after) TextView textPm25After;
    @BindView(R.id.text_o3_after) TextView textO3After;
    @BindView(R.id.text_co_after) TextView textCoAfter;
    @BindView(R.id.text_so2_after) TextView textSo2After;
    @BindView(R.id.text_no2_after) TextView textNo2After;

    private PSIRegionReadings regionReadings;
    private boolean isSubIndex;

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

        iconInfo.setOnClickListener(this);
        buttonToggle.setOnClickListener(this);
        buttonToggle.setText(getString(R.string.button_toggle_see_more));
        isSubIndex = true;

        regionReadings = getIntent().getExtras().getParcelable(KEY_REGION_PSI);
        String region = getIntent().getExtras().getString(KEY_REGION);

        if(region != null && regionReadings != null) {
            double psi = Double.parseDouble(regionReadings.getPsiTwentyFourHourly());
            activityTitle.setText(String.format(getString(R.string.title_region_psi), FormatString.camelCase(region)));
            textPSIRegion.setText(String.format(getString(R.string.region_psi), FormatString.camelCase(region), regionReadings.getPsiTwentyFourHourly()));
            textPSIStatus.setText(String.format(getString(R.string.region_psi_status), determinePSIStatus(psi)));
            iconStatus.setBackgroundResource(getHealthStatusDrawable(psi));
            setPSIValuesSubIndex(regionReadings);
        } else {
            activityTitle.setText(getString(R.string.title_singapore_psi));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_info : {
                alertDialogLegend();
                break;
            }

            case R.id.button_toggle : {
                if(isSubIndex) {
                    buttonToggle.setText(getString(R.string.button_toggle_sub_index));
                    isSubIndex = false;
                    setPSIValuesHours(regionReadings);
                } else {
                    buttonToggle.setText(getString(R.string.button_toggle_see_more));
                    isSubIndex = true;
                    setPSIValuesSubIndex(regionReadings);
                }

                break;
            }

            default: {
                break;
            }
        }
    }

    public void alertDialogLegend() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegionPSIActivity.this);
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

    private int getHealthStatusDrawable(double psiLevel) {
        if(psiLevel <= PSIStatus.MODERATE_MAX.getValue()) {
            return R.drawable.ic_thumb_up_white_24dp;
        }

        return R.drawable.ic_thumb_down_white_24dp;
    }

    private String determinePSIStatus(double psiLevel) {
        if(psiLevel <= PSIStatus.HEALTHY_MAX.getValue()) {
            return getString(R.string.status_healthy);
        } else if(psiLevel >= PSIStatus.MODERATE_MIN.getValue()
                && psiLevel <= PSIStatus.MODERATE_MAX.getValue()) {
            return getString(R.string.status_moderate);
        } else if(psiLevel >= PSIStatus.UNHEALTHY_MIN.getValue()
                && psiLevel <= PSIStatus.UNHEALTHY_MAX.getValue()) {
            return getString(R.string.status_unhealthy);
        } else if(psiLevel >= PSIStatus.VERY_UNHEALTHY_MIN.getValue()
                && psiLevel <= PSIStatus.VERY_UNHEALTHY_MAX.getValue()) {
            return getString(R.string.status_very_unhealthy);
        }

        return getString(R.string.status_hazardous);
    }

    private SpannableStringBuilder superSubScript(String chemical, int subscriptType) {
        SpannableStringBuilder cs = new SpannableStringBuilder(chemical);

        switch (subscriptType) {
            case SUPERSCRIPT_TWO_SUBSCRIPT_TWO : {
                cs.setSpan(new SubscriptSpan(), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(0.75f), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            case SUPERSCRIPT_TWO_SUBSCRIPT_THREE : {
                cs.setSpan(new SubscriptSpan(), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(0.75f), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            case SUPERSCRIPT_ONE_SUBSCRIPT_ONE : {
                cs.setSpan(new SubscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(0.75f), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            }

            case SUPERSCRIPT_TWO_SUBSCRIPT_ONE : {
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

    private void setPSIValuesSubIndex(PSIRegionReadings regionReadings) {
        labelPm10.setText(superSubScript(getString(R.string.pm10_sub_index), SUPERSCRIPT_TWO_SUBSCRIPT_TWO));
        setCurrentPsiLevel(textPm10Before, textPm10After, Float.parseFloat(regionReadings.getPm10SubIndex()));

        labelPm25.setText(superSubScript(getString(R.string.pm25_sub_index), SUPERSCRIPT_TWO_SUBSCRIPT_THREE));
        setCurrentPsiLevel(textPm25Before, textPm25After, Float.parseFloat(regionReadings.getPm25SubIndex()));

        labelO3.setText(superSubScript(getString(R.string.o3_sub_index), SUPERSCRIPT_ONE_SUBSCRIPT_ONE));
        setCurrentPsiLevel(textO3Before, textO3After, Float.parseFloat(regionReadings.getO3SubIndex()));

        labelCo.setText(getString(R.string.co_sub_index));
        setCurrentPsiLevel(textCoBefore, textCoAfter, Float.parseFloat(regionReadings.getCoSubIndex()));

        labelSo2.setText(superSubScript(getString(R.string.so2_sub_index), SUPERSCRIPT_TWO_SUBSCRIPT_ONE));
        setCurrentPsiLevel(textSo2Before, textSo2After, Float.parseFloat(regionReadings.getSo2SubIndex()));

        layoutNo2.setVisibility(View.GONE);
        dividerNo2.setVisibility(View.GONE);
    }

    private void setPSIValuesHours(PSIRegionReadings regionReadings) {
        labelPm10.setText(superSubScript(getString(R.string.pm10_twenty_four_hourly), SUPERSCRIPT_TWO_SUBSCRIPT_TWO));
        setCurrentPsiLevel(textPm10Before, textPm10After, Float.parseFloat(regionReadings.getPm10TwentyFourHourly()));

        labelPm25.setText(superSubScript(getString(R.string.pm25_twenty_four_hourly), SUPERSCRIPT_TWO_SUBSCRIPT_THREE));
        setCurrentPsiLevel(textPm25Before, textPm25After, Float.parseFloat(regionReadings.getPm25TwentyFourHourly()));

        labelO3.setText(superSubScript(getString(R.string.o3_eight_hour_max), SUPERSCRIPT_ONE_SUBSCRIPT_ONE));
        setCurrentPsiLevel(textO3Before, textO3After, Float.parseFloat(regionReadings.getO3EightHourMax()));

        labelCo.setText(getString(R.string.co_eight_hour_max));
        setCurrentPsiLevel(textCoBefore, textCoAfter, Float.parseFloat(regionReadings.getCoEightHourMax()));

        labelSo2.setText(superSubScript(getString(R.string.so2_twenty_four_hourly), SUPERSCRIPT_TWO_SUBSCRIPT_ONE));
        setCurrentPsiLevel(textSo2Before, textSo2After, Float.parseFloat(regionReadings.getSo2TwentyFourHourly()));

        layoutNo2.setVisibility(View.VISIBLE);
        dividerNo2.setVisibility(View.VISIBLE);
        labelNo2.setText(superSubScript(getString(R.string.no2_one_hour_max), SUPERSCRIPT_TWO_SUBSCRIPT_TWO));
        setCurrentPsiLevel(textNo2Before, textNo2After, Float.parseFloat(regionReadings.getNo2OneHourMax()));
    }

    private void setCurrentPsiLevel(TextView psiLevelBefore, TextView psiLevelAfter, float psi) {
        float ratioBefore = psi / PSIStatus.HAZARDOUS.getValue();
        float ratioAfter = (PSIStatus.HAZARDOUS.getValue() - psi) / PSIStatus.HAZARDOUS.getValue();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = ratioAfter;
        psiLevelBefore.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = ratioBefore;
        psiLevelAfter.setLayoutParams(params);

        psiLevelAfter.setText(FormatString.padRight("▲\n" + psi));
    }
}
