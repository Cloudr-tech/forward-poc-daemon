package cloudr.daemon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static APIService service;

    public API() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cloudr-api.marmus.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
    }

    public static void put(APIObject object) {
        service.put(object).call(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    public static void patch(APIObject object) {
        service.patch(object).call(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
}