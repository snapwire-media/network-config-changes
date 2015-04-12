package re.snapwi.orientation;

import android.os.Bundle;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import de.greenrobot.event.EventBus;
import icepick.Icepick;
import icepick.Icicle;
import javax.inject.Inject;
import re.snapwi.orientation.di.PerActivity;
import re.snapwi.orientation.event.JokeEvent;
import re.snapwi.orientation.io.Joke;
import re.snapwi.orientation.ui.MainActivity;

public class JokePresenter {

  public static final String JOKE_KEY = "JokeKeyasdfasdf";

  EventBus bus;
  JokeListener listener;

  @Inject @PerActivity public JokePresenter(EventBus bus, JokeListener listener) {
    this.bus = bus;
    this.listener = listener;
  }

  @Icicle Joke joke;
  @Icicle boolean isLoadingJoke;

  public void onResume() {
    bus.registerSticky(this);
  }

  public void onPause() {
    bus.unregister(this);
  }

  public void restoreState(Bundle state) {
    Icepick.restoreInstanceState(this, state);

    if (state == null && !isLoadingJoke) {
      getRandomJoke();
    }

    if (joke != null) {
      listener.onJokeLoaded(joke);
    }
  }

  public void onEventMainThread(JokeEvent event) {
    if (event.hasError()) {
      listener.onFailed();
    } else {
      joke = event.getJoke();
      listener.onJokeLoaded(joke);
    }

    isLoadingJoke = false;
    bus.removeStickyEvent(event);
  }

  public void saveState(Bundle outstate) {
    Icepick.saveInstanceState(this, outstate);
  }

  public void getRandomJoke() {
    listener.onStartedLoading();
    isLoadingJoke = true;
    bus.post(new NorrisApiController.GetRandomJokeEvent());
  }

  public interface JokeListener {
    void onJokeLoaded(Joke joke);

    void onFailed();

    void onStartedLoading();
  }

  @PerActivity @Subcomponent(modules = JokeModule.class)
  public interface JokeComponent {

    void inject(MainActivity activity);
  }

  @Module public static class JokeModule {
    JokeListener listener;

    public JokeModule(JokeListener listener) {
      this.listener = listener;
    }

    @Provides @PerActivity public JokeListener provideListener() {
      return listener;
    }
  }
}
