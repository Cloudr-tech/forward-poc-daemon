package cloudr.daemon;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.PATCH;
import retrofit.http.PUT;

public interface APIService {
    public static final String ENDPOINT = "https://cloudr-api.marmus.me";

    @PUT("/daemons")
    APIResponse put(@Body APIObject object);
    @PATCH("/search/repositories")
    APIResponse patch(@Body APIObject object);
}
