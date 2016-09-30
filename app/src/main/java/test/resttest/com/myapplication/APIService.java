package test.resttest.com.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

/**
 * Created by Владимир on 30.09.2016.
 */
public interface APIService {

    @POST("create-user/")
    Call<User> createUser(@Body User user);

    @GET("user-list/")
    Call<List<User>> getUserList();

}