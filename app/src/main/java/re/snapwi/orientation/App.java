package re.snapwi.orientation;

import android.app.Application;
import javax.inject.Inject;
import re.snapwi.orientation.di.AppComponent;
import re.snapwi.orientation.di.DaggerAppComponent;
import re.snapwi.orientation.rest.NorrisApiController;

public class App extends Application {

  @Inject NorrisApiController controller;
  private AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    appComponent = DaggerAppComponent.create();
    appComponent.inject(this);
  }

  public AppComponent appComponent() {
    return appComponent;
  }
}