package re.snapwi.orientation.di;

import dagger.Module;
import dagger.Provides;
import re.snapwi.orientation.ui.JokePresenter;

@Module public class JokeModule {
  JokePresenter.JokeListener listener;

  public JokeModule(JokePresenter.JokeListener listener) {
    this.listener = listener;
  }

  @Provides @PerActivity public JokePresenter.JokeListener provideListener() {
    return listener;
  }
}
