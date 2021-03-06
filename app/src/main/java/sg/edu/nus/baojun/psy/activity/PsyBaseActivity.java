package sg.edu.nus.baojun.psy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.io.IOException;

import retrofit2.Response;
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

    public void handleAPICallFailure(Response<? extends Object> response) {
        if (response != null && response.errorBody() != null) {
            try {
                String error = getPsyApplication().getErrorConverter().convert(response.errorBody());
                Snackbar snackbar = Snackbar.make(loadingView, error, Snackbar.LENGTH_SHORT);
                snackbar.show();
            } catch (IOException e) {
                Snackbar snackbar = Snackbar.make(loadingView, R.string.hint_unknown_api_error, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(loadingView, R.string.hint_unknown_api_error, Snackbar.LENGTH_SHORT);
            snackbar.show();

        }
    }

    public void handleNetworkFailure() {
        Snackbar snackbar = Snackbar.make(loadingView, R.string.hint_network_failure, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
