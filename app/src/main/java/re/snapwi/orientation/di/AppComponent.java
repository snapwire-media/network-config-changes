package re.snapwi.orientation.di;

import dagger.Component;
import javax.inject.Singleton;
import re.snapwi.orientation.App;
import re.snapwi.orientation.MainActivity;

@Singleton @Component(modules = { RestModule.class, CommunicationModule.class })
public interface AppComponent {

  void inject(App app);

  void inject(MainActivity activity);
}
