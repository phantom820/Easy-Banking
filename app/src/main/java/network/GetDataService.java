package network;

import java.util.HashMap;
import java.util.List;

import models.Account;
import models.Client;
import models.Transaction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("/clients")
    Call<Client> addClient(@Body Client client);

    @GET("/clients")
    Call<List<Client>> getUsers(@Query("identity_number") String identityNumber,@Query("password") String password);

    @GET("/accounts")
    Call<List<Account>> getAccounts(@Query("identity_number") String identityNumber);

    @PATCH("/accounts/pay")
    Call<String>makePayment(@Body Transaction transaction);

}
