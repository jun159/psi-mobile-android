package sg.edu.nus.baojun.psy;

import android.app.Application;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sg.edu.nus.baojun.psy.network.PsyService;

/**
 * Created by BAOJUN on 9/7/17.
 */

public class PsyApplication extends Application {

    private PsyService psyService;

    private Converter<ResponseBody, String> errorConverter;

    public PsyService getPsyService() {
        return psyService;
    }

    public Converter<ResponseBody, String> getErrorConverter() {
        return errorConverter;
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return simpleDateFormat.format(new Date());
    }

    public String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return simpleDateFormat.format(new Date());
    }

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        Date currentLocalTime = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy KK:mm a");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        return String.format(getString(R.string.last_updated), dateFormat.format(currentLocalTime));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header(getString(R.string.name_api_key), getString(R.string.api_key))
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(headerInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_server_address))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        psyService = retrofit.create(PsyService.class);
        errorConverter = retrofit.responseBodyConverter(String.class, new Annotation[0]);
    }
}
