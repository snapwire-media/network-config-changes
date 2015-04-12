package re.snapwi.orientation.event

import re.snapwi.orientation.io.Joke
import retrofit.RetrofitError

public class JokeEvent {
  var retrofitError: RetrofitError? = null
  var joke: Joke? = null

  public constructor(joke: Joke) {
    this.joke = joke
  }

  public constructor(retrofitError: RetrofitError) {
    this.retrofitError = retrofitError
  }

  public fun hasError(): Boolean = retrofitError != null
}