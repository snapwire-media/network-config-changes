package re.snapwi.orientation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;
import javax.inject.Inject;
import re.snapwi.orientation.io.Joke;

import static android.view.View.OnClickListener;
import static re.snapwi.orientation.JokePresenter.JokeListener;
import static re.snapwi.orientation.JokePresenter.JokeModule;

public class MainActivity extends ActionBarActivity implements OnClickListener, JokeListener {
  private static final String FLIPPER = "FLipperStateKey";

  private ViewFlipper flipper;
  private TextView jokeTextView;

  @Inject JokePresenter presenter;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);
    setContentView(R.layout.activity_main);

    ((App) getApplication()).appComponent().plus(new JokeModule(this)).inject(this);

    jokeTextView = (TextView) findViewById(R.id.jokeTextView);
    flipper = (ViewFlipper) findViewById(R.id.mainFlipper);
    findViewById(R.id.tryAgainButton).setOnClickListener(this);

    if (savedState != null) {
      flipper.setDisplayedChild(savedState.getInt(FLIPPER, 0));
    }

    presenter.restoreState(savedState);
  }

  @Override protected void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(FLIPPER, flipper.getDisplayedChild());
    presenter.saveState(outState);
  }

  @Override protected void onPause() {
    presenter.onPause();
    super.onPause();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_refresh) {
      presenter.getRandomJoke();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onClick(View v) {
    presenter.getRandomJoke();
  }

  @Override public void onJokeLoaded(Joke joke) {
    jokeTextView.setText(Html.fromHtml(joke.getJoke()));
    flipper.setDisplayedChild(2);
  }

  @Override public void onFailed() {
    flipper.setDisplayedChild(1);
  }

  @Override public void onStartedLoading() {
    flipper.setDisplayedChild(0);
  }
}
