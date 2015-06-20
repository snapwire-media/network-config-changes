package re.snapwi.orientation.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.bindView
import re.snapwi.orientation.R
import re.snapwi.orientation.di.JokeModule
import re.snapwi.orientation.io.Joke
import re.snapwi.orientation.util.getAppComponent
import javax.inject.Inject
import kotlin.properties.Delegates

public class MainActivity : AppCompatActivity(), JokePresenter.JokeListener, SwipeRefreshLayout.OnRefreshListener {
  val refreshLayout: SwipeRefreshLayout by bindView(R.id.swipeRefreshLayout)
  val retryLayout: View by bindView(R.id.retryLayout)
  val jokeTextView: TextView by bindView(R.id.jokeTextView)

  var presenter: JokePresenter by Delegates.notNull()
    @Inject set

  override fun onCreate(state: Bundle?) {
    super<AppCompatActivity>.onCreate(state)
    setContentView(R.layout.activity_main)
    getAppComponent().plus(JokeModule(this)).inject(this)

    findViewById(R.id.tryAgainButton).setOnClickListener({ presenter.getRandomJoke() })

    refreshLayout.setOnRefreshListener(this)
    refreshLayout.setRefreshing(if (state == null) true else state.getBoolean(LOADING_CONTENT))
    presenter.restoreState(state ?: Bundle())
  }

  override fun onRefresh() {
    presenter.getRandomJoke()
  }

  override fun onResume() {
    super<AppCompatActivity>.onResume()
    presenter.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super<AppCompatActivity>.onSaveInstanceState(outState)
    outState.putBoolean(LOADING_CONTENT, refreshLayout.isRefreshing())
    presenter.saveState(outState)
  }

  override fun onPause() {
    presenter.onPause()
    super<AppCompatActivity>.onPause()
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

    return super<AppCompatActivity>.onOptionsItemSelected(item)
  }

  override fun onJokeLoaded(joke: Joke) {
    jokeTextView.setText(Html.fromHtml(joke.getJoke()))
    refreshLayout.setRefreshing(false)
    retryLayout.setVisibility(View.INVISIBLE)
  }

  override fun onFailed() {
    refreshLayout.setRefreshing(false)
    retryLayout.setVisibility(View.VISIBLE)
  }

  override fun onStartedLoading() {
    refreshLayout.setRefreshing(true)
    retryLayout.setVisibility(View.INVISIBLE)
  }

  companion object {
    private val LOADING_CONTENT = "FlipperStateKey"
  }
}