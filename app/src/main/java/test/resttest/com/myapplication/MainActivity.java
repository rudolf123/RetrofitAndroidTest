package test.resttest.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.Console;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback<User> {

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

    }

    public void sendUserData(View view) throws IOException {
        User user = new User();
        user.phone_number = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
        user.device_ident = "123";
        user.first_name = ((EditText)findViewById(R.id.editTextUserName)).getText().toString();
        user.password = ((EditText)findViewById(R.id.editTextPass)).getText().toString();
        user.password2 = ((EditText)findViewById(R.id.editTextPass2)).getText().toString();
        Call<User> call = testAPI.createUser(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> user, Response<User> response) {
        TextView responseText = (TextView) findViewById(R.id.ResponseText);
        try{
            responseText.setText(new Gson().toJson(response.body()));
        }
        catch (Exception e)
        {
            responseText.setText(e.toString());
        }
    }

    @Override
    public void onFailure(Call<User> user, Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
