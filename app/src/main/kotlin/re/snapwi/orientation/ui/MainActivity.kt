package re.snapwi.orientation.ui

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.ViewFlipper
import butterknife.bindView
import re.snapwi.orientation.ui.JokePresenter
import re.snapwi.orientation.R
import re.snapwi.orientation.di.JokeModule
import re.snapwi.orientation.io.Joke
import re.snapwi.orientation.util.getAppComponent
import javax.inject.Inject
import kotlin.properties.Delegates

public class MainActivity : ActionBarActivity(), JokePresenter.JokeListener {
  val flipper: ViewFlipper by bindView(R.id.mainFlipper)
  val jokeTextView: TextView by bindView(R.id.jokeTextView)

  var presenter: JokePresenter by Delegates.notNull()
    [Inject] set

  override fun onCreate(state: Bundle?) {
    super<ActionBarActivity>.onCreate(state)
    setContentView(R.layout.activity_main)
    getAppComponent().plus(JokeModule(this)).inject(this)

    findViewById(R.id.tryAgainButton).setOnClickListener({ presenter.getRandomJoke() })

    flipper.setDisplayedChild(if (state == null) 0 else state.getInt(FLIPPER))
    presenter.restoreState(state ?: Bundle())
  }

  override fun onResume() {
    super<ActionBarActivity>.onResume()
    presenter.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super<ActionBarActivity>.onSaveInstanceState(outState)
    outState.putInt(FLIPPER, flipper.getDisplayedChild())
    presenter.saveState(outState)
  }

  override fun onPause() {
    presenter.onPause()
    super<ActionBarActivity>.onPause()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    getMenuInflater().inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.getItemId() == R.id.action_refresh) {
      presenter.getRandomJoke()
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