package sg.edu.nus.baojun.psy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.edu.nus.baojun.psy.activity.MainActivity;
import sg.edu.nus.baojun.psy.network.GetPSIResponse;

/**
 * Created by BAOJUN on 9/8/17.
 */

@RunWith(AndroidJUnit4.class)
public class GetPSIFromRemoteTest {

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class);

    private String date;
    private String dateTime;

    @Before
    public void prepareDates() {
        date = rule.getActivity().getPsyApplication().getDate();
        dateTime = rule.getActivity().getPsyApplication().getDateTime();
    }

    @Test
    public void testGetPSIDataByDate() {

        Call<GetPSIResponse> call = rule
                .getActivity()
                .getPsyApplication()
                .getPsyService()
                .getPsiByDate(date);

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {

                if (response.isSuccessful()) {
                    GetPSIResponse psiResponse = response.body();
                    Assert.assertNotNull(psiResponse);
                    Assert.assertNotEquals(psiResponse.getRegionMetadataList().size(), 0);
                    Assert.assertNotEquals(psiResponse.getPsiItemList(), 0);
                    Assert.assertNotNull(psiResponse.getApiInfo());
                } else {
                    // API call failure
                    Assert.fail();
                }
            }

            @Override
            public void onFailure(Call<GetPSIResponse> call, Throwable t) {
                // No network connection
                Assert.fail();
            }
        };

        call.enqueue(callback);
    }

    @Test
    public void testGetPSIDataByDateTime() {

        Call<GetPSIResponse> call = rule
                .getActivity()
                .getPsyApplication()
                .getPsyService()
                .getPsiByDateTime(dateTime);

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {

                if (response.isSuccessful()) {
                    GetPSIResponse psiResponse = response.body();
                    Assert.assertNotNull(psiResponse);
                    Assert.assertNotEquals(psiResponse.getRegionMetadataList().size(), 0);
                    Assert.assertNotEquals(psiResponse.getPsiItemList(), 0);
                    Assert.assertNotNull(psiResponse.getApiInfo());
                } else {
                    // API call failure
                    Assert.fail();
                }
            }

            @Override
            public void onFailure(Call<GetPSIResponse> call, Throwable t) {
                // No network connection
                Assert.fail();
            }
        };

        call.enqueue(callback);
    }

    @Test
    public void testGetPSIDataByInvalidDate() {

        Call<GetPSIResponse> call = rule
                .getActivity()
                .getPsyApplication()
                .getPsyService()
                .getPsiByDate(dateTime);

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {


                if (response.isSuccessful()) {
                    Assert.fail();
                } else {
                    // API call failure
                    Assert.assertNotNull(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GetPSIResponse> call, Throwable t) {
                // No network connection
                Assert.fail();
            }
        };

        call.enqueue(callback);
    }

    @Test
    public void testGetPSIDataByInvalidDateTime() {

        Call<GetPSIResponse> call = rule
                .getActivity()
                .getPsyApplication()
                .getPsyService()
                .getPsiByDateTime(date);

        Callback<GetPSIResponse> callback = new Callback<GetPSIResponse>() {
            @Override
            public void onResponse(Call<GetPSIResponse> call, Response<GetPSIResponse> response) {

                if (response.isSuccessful()) {
                    Assert.fail();
                } else {
                    // API call failure
                    Assert.assertNotNull(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GetPSIResponse> call, Throwable t) {
                // No network connection
                Assert.fail();
            }
        };

        call.enqueue(callback);
    }
}
