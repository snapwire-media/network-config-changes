package re.snapwi.orientation.di;

import dagger.Component;
import de.greenrobot.event.EventBus;
import javax.inject.Singleton;
import re.snapwi.orientation.App;
import re.snapwi.orientation.JokePresenter;

@Singleton @Component(modules = { RestModule.class, CommunicationModule.class })
public interface AppComponent {

  void inject(App app);

  JokePresenter.JokeComponent plus(JokePresenter.JokeModule module);
}
