package re.snapwi.orientation.event

import re.snapwi.orientation.io.Joke
import retrofit.RetrofitError

public class JokeEvent(val joke: Joke?, val error: RetrofitError?) {
  public fun hasError(): Boolean = error != null
}