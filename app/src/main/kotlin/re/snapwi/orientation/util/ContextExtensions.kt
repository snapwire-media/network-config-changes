package re.snapwi.orientation.util

import android.content.Context
import re.snapwi.orientation.App
import re.snapwi.orientation.di.AppComponent


fun Context.getAppComponent(): AppComponent {
  return (getApplicationContext() as App).appComponent();
}