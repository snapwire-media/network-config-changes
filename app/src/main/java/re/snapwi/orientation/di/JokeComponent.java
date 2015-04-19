package re.snapwi.orientation.di;

import dagger.Subcomponent;
import re.snapwi.orientation.ui.MainActivity;

@PerActivity @Subcomponent(modules = JokeModule.class)
public interface JokeComponent {

  void inject(MainActivity activity);
}
