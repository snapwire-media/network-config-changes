package re.snapwi.orientation.rest;

import re.snapwi.orientation.io.Joke;
import retrofit.Callback;
import retrofit.http.GET;

public interface NorrisApi {
  @GET("/random") void randomJoke(Callback<Joke> callback);
}