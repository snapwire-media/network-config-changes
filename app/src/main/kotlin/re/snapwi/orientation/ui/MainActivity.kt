package re.snapwi.orientation.ui

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.ViewFlipper
import butterknife.bindView
import re.snapwi.orientation.App
import re.snapwi.orientation.JokePresenter
import re.snapwi.orientation.R
import re.snapwi.orientation.io.Joke
import javax.inject.Inject

public class MainActivity : ActionBarActivity(), JokePresenter.JokeListener {

  val flipper: ViewFlipper by bindView(R.id.mainFlipper)
  val jokeTextView: TextView by bindView(R.id.jokeTextView)

  var presenter: JokePresenter? = null
    [Inject] set

  override fun onCreate(savedState: Bundle?) {
    super<ActionBarActivity>.onCreate(savedState)
    setContentView(R.layout.activity_main)
    (getApplication() as App).appComponent().plus(JokePresenter.JokeModule(this)).inject(this)

    findViewById(R.id.tryAgainButton).setOnClickListener({ presenter?.getRandomJoke() })

    if (savedState != null) {
      flipper.setDisplayedChild(savedState.getInt(FLIPPER, 0))
    }

    presenter?.restoreState(savedState)
  }

  override fun onResume() {
    super<ActionBarActivity>.onResume()
    presenter?.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super<ActionBarActivity>.onSaveInstanceState(outState)
    outState.putInt(FLIPPER, flipper.getDisplayedChild())
    presenter?.saveState(outState)
  }

  override fun onPause() {
    presenter?.onPause()
    super<ActionBarActivity>.onPause()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    getMenuInflater().inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.getItemId() == R.id.action_refresh) {
      presenter?.getRandomJoke()
      return true
    }

    return super<ActionBarActivity>.onOptionsItemSelected(item)
  }

  override fun onJokeLoaded(joke: Joke) {
    jokeTextView.setText(Html.fromHtml(joke.getJoke()))
    flipper.setDisplayedChild(2)
  }

  override fun onFailed() {
    flipper.setDisplayedChild(1)
  }

  override fun onStartedLoading() {
    flipper.setDisplayedChild(0)
  }

  companion object {
    private val FLIPPER = "FlipperStateKey"
  }
}