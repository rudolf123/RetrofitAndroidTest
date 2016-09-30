package test.resttest.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Владимир on 30.09.2016.
 */
public class UserListActivity extends AppCompatActivity implements Callback<List<User>> {

    Retrofit retrofit;
    APIService testAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://shopquest-rudolf.rhcloud.com/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        testAPI = retrofit.create(APIService.class);

        Call<List<User>> call = testAPI.getUserList();
        //asynchronous call
        TextView testText = (TextView) findViewById(R.id.TestText);
        testText.setText(call.request().toString());

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<User>> user, Response<List<User>> response) {
        TextView responseText = (TextView) findViewById(R.id.ResponseText);
        try{
            responseText.setText(new Gson().toJson(response));
        }
        catch (Exception e)
        {
            responseText.setText(e.toString());
        }
    }

    @Override
    public void onFailure(Call<List<User>> user, Throwable t) {
        Toast.makeText(UserListActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
