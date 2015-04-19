package re.snapwi.orientation.di;

import dagger.Component;
import javax.inject.Singleton;
import re.snapwi.orientation.App;

@Singleton @Component(modules = { RestModule.class, CommunicationModule.class })
public interface AppComponent {

  void inject(App app);

  JokeComponent plus(JokeModule module);
}
