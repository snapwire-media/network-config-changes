package re.snapwi.orientation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;
import de.greenrobot.event.EventBus;
import javax.inject.Inject;
import re.snapwi.orientation.event.JokeEvent;
import re.snapwi.orientation.io.Joke;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
  private static final String FLIPPER = "FLipperStateKey";
  private static final String JOKE = "JokeKey";

  private ViewFlipper flipper;
  private TextView jokeTextView;

  private Joke joke;
  private boolean isLoadingJoke;

  @Inject EventBus bus;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);
    setContentView(R.layout.activity_main);

    ((App) getApplication()).getAppComponent().inject(this);

    jokeTextView = (TextView) findViewById(R.id.jokeTextView);
    flipper = (ViewFlipper) findViewById(R.id.mainFlipper);
    findViewById(R.id.tryAgainButton).setOnClickListener(this);

    if (savedState != null) {
      flipper.setDisplayedChild(savedState.getInt(FLIPPER, 0));
      joke = savedState.getParcelable(JOKE);
    }

    if (joke != null) {
      bindJoke();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    /** Register first since sticky events are delivered right away if available. */
    bus.registerSticky(this);

    if (joke == null && !isLoadingJoke) {
      getRandomJoke();
    }
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(JOKE, joke);
    outState.putInt(FLIPPER, flipper.getDisplayedChild());
  }

  public void onEventMainThread(JokeEvent event) {
    if (event.hasError()) {
      flipper.setDisplayedChild(1);
    } else {
      joke = event.getJoke();
      bindJoke();
      flipper.setDisplayedChild(2);
    }

    isLoadingJoke = false;
    bus.removeStickyEvent(event);
  }

  private void bindJoke() {
    jokeTextView.setText(Html.fromHtml(joke.getJoke()));
  }

  private void getRandomJoke() {
    flipper.setDisplayedChild(0);
    isLoadingJoke = true;
    bus.post(new NorrisApiController.GetRandomJokeEvent());
  }

  @Override protected void onPause() {
    bus.unregister(this);
    super.onPause();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_refresh) {
      joke = null;
      getRandomJoke();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onClick(View v) {
    getRandomJoke();
  }
}
