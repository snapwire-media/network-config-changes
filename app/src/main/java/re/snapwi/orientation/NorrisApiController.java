package re.snapwi.orientation;

import de.greenrobot.event.EventBus;
import re.snapwi.orientation.event.JokeEvent;
import re.snapwi.orientation.io.Joke;
import re.snapwi.orientation.rest.NorrisApi;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NorrisApiController {
  private NorrisApi api;
  private EventBus bus;

  public NorrisApiController(NorrisApi api, EventBus bus) {
    this.api = api;
    this.bus = bus;
    bus.register(this);
  }

  public void onEventAsync(GetRandomJokeEvent event) {
    api.randomJoke(new Callback<Joke>() {
      @Override public void success(Joke joke, Response response) {
        bus.postSticky(new JokeEvent(joke));
      }

      @Override public void failure(RetrofitError error) {
        bus.postSticky(new JokeEvent(error));
      }
    });
  }

  public static class GetRandomJokeEvent {
  }
}
