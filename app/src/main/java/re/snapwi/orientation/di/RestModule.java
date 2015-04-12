package re.snapwi.orientation.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import re.snapwi.orientation.rest.NorrisApi;
import retrofit.RestAdapter;

@Module
public class RestModule {
  private static final String API_URL = "http://api.icndb.com/jokes";

  @Provides @Singleton public RestAdapter provideAdapter() {
    return new RestAdapter.Builder().setEndpoint(API_URL).build();
  }

  @Provides @Singleton public NorrisApi provideNorisService(RestAdapter adapter) {
    return adapter.create(NorrisApi.class);
  }
}
