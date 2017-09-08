package sg.edu.nus.baojun.psy.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BAOJUN on 9/7/17.
 */

public interface PsyService {
    @GET("environment/psi")
    Call<GetPSIResponse> getPsiByDateTime(@Query("date_time") String dateTime);

    @GET("environment/psi")
    Call<GetPSIResponse> getPsiByDate(@Query("date") String date);
}

