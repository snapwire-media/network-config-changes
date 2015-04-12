package re.snapwi.orientation.rest

import re.snapwi.orientation.io.Joke
import retrofit.Callback
import retrofit.http.GET

public trait NorrisApi {
  GET("/random") public fun randomJoke(callback: Callback<Joke>)
}
