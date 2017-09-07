package sg.edu.nus.baojun.psy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import sg.edu.nus.baojun.psy.PsyApplication;
import sg.edu.nus.baojun.psy.R;

/**
 * Created by BAOJUN on 9/7/17.
 */

public class PsyBaseActivity extends AppCompatActivity {

    protected CircleProgressBar loadingView;

    public PsyApplication getPsyApplication() {
        return (PsyApplication) getApplication();
    }

    public void startLoading() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (loadingView == null) {
            loadingView = (CircleProgressBar) findViewById(R.id.loadingView);
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    public void stopLoading() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (loadingView == null) {
            loadingView = (CircleProgressBar) findViewById(R.id.loadingView);
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
    }

    protected void dismissKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingView = (CircleProgressBar) findViewById(R.id.loadingView);
    }


    public void handleAPICallFailure() {
        Snackbar snackbar = Snackbar.make(loadingView, R.string.hint_unknown_api_error, Snackbar.LENGTH_SHORT);
        snackbar.show();
        stopLoading();
    }

    public void handleNetworkFailure() {
        Snackbar snackbar = Snackbar.make(loadingView, R.string.hint_network_failure, Snackbar.LENGTH_SHORT);
        snackbar.show();
        stopLoading();
    }
}
