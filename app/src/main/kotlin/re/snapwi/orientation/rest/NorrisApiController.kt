package re.snapwi.orientation.rest

import de.greenrobot.event.EventBus
import re.snapwi.orientation.event.JokeEvent
import re.snapwi.orientation.io.Joke
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import javax.inject.Inject
import javax.inject.Singleton

Singleton public class NorrisApiController Inject constructor(var api: NorrisApi, var bus: EventBus) {
  init {
    bus.register(this)
  }

  public fun onEventAsync(event: GetRandomJokeEvent) {
    api.randomJoke(object : Callback<Joke> {
      override fun success(joke: Joke, response: Response) = bus.postSticky(JokeEvent(joke, null))
      override fun failure(error: RetrofitError) = bus.postSticky(JokeEvent(null, error))
    })
  }

  public class GetRandomJokeEvent
}