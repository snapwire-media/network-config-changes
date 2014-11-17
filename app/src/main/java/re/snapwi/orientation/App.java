package re.snapwi.orientation;

import android.app.Application;
import de.greenrobot.event.EventBus;
import re.snapwi.orientation.rest.NorrisApi;
import retrofit.RestAdapter;

public class App extends Application {
  private static final String API_URL = "http://api.icndb.com/jokes";
  private NorrisApiController controller;

  @Override public void onCreate() {
    super.onCreate();

    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
    NorrisApi api = restAdapter.create(NorrisApi.class);
    controller = new NorrisApiController(api, EventBus.getDefault());
  }
}