package re.snapwi.orientation.di;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import javax.inject.Singleton;

@Module
public class CommunicationModule {

  @Provides @Singleton public EventBus provideBus() {
    return EventBus.getDefault();
  }
}
