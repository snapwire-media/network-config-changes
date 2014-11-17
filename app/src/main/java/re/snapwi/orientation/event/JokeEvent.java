package re.snapwi.orientation.event;

import re.snapwi.orientation.io.Joke;
import retrofit.RetrofitError;

public class JokeEvent {
  private RetrofitError retrofitError;
  private Joke joke;

  public JokeEvent(Joke joke) {
    this.joke = joke;
  }

  public Joke getJoke() {
    return joke;
  }

  public JokeEvent(RetrofitError retrofitError) {
    this.retrofitError = retrofitError;
  }

  public boolean hasError() {
    return retrofitError != null;
  }
}
