package re.snapwi.orientation.ui

import android.os.Bundle
import de.greenrobot.event.EventBus
import re.snapwi.orientation.di.PerActivity
import re.snapwi.orientation.event.JokeEvent
import re.snapwi.orientation.io.Joke
import re.snapwi.orientation.rest.NorrisApiController
import javax.inject.Inject

public class JokePresenter
@Inject @PerActivity constructor(var bus: EventBus, var listener: JokePresenter.JokeListener) {

  val JOKE_KEY = "JOKE_KEY"
  val LOADING_JOKE_KEY = "LoadingJokeKey"
  var joke: Joke? = null
  var isLoadingJoke: Boolean = false

  public fun onResume(): Unit = bus.registerSticky(this)

  public fun onPause(): Unit = bus.unregister(this)

  fun saveState(outState: Bundle) {
    outState.putParcelable(JOKE_KEY, joke)
    outState.putBoolean(LOADING_JOKE_KEY, isLoadingJoke)
  }

  public fun restoreState(state: Bundle) {
    joke = state.getParcelable(JOKE_KEY)
    isLoadingJoke = state.getBoolean(LOADING_JOKE_KEY, false)

    if (state.isEmpty() && !isLoadingJoke) {
      getRandomJoke()
    }

    if (joke != null) {
      listener.onJokeLoaded(joke as Joke)
    }
  }

  public fun onEventMainThread(event: JokeEvent) {
    if (event.hasError()) {
      listener.onFailed()
    } else {
      joke = event.joke
      listener.onJokeLoaded(joke as Joke)
    }

    isLoadingJoke = false
    bus.removeStickyEvent(event)
  }

  public fun getRandomJoke() {
    listener.onStartedLoading()
    isLoadingJoke = true
    bus.post(NorrisApiController.GetRandomJokeEvent())
  }

  public interface JokeListener {
    fun onJokeLoaded(joke: Joke)

    fun onFailed()

    fun onStartedLoading()
  }
}