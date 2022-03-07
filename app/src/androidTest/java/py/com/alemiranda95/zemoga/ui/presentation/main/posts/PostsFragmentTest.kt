package py.com.alemiranda95.zemoga.ui.presentation.main.posts

import android.support.test.rule.ActivityTestRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import py.com.alemiranda95.zemoga.HiltTestActivity
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.launchFragmentInHiltContainer

//DISCLAIMER: For some reason my Android Studio couldn't connect to my device to run the tests
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class PostsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var activityTestRyle = ActivityTestRule(HiltTestActivity::class.java)
    private lateinit var scenario: FragmentScenario<PostsFragment>

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLoadPosts() {
        launchFragmentInHiltContainer<PostsFragment> { }

        val postsRecyclerView = activityTestRyle.activity.findViewById<RecyclerView>(R.id.recyclerview_posts)
        runBlocking {
            launch { onView(withId(R.id.refresh_layout_posts)).perform(swipeDown()) }
            delay(3000)
            assertTrue(postsRecyclerView.adapter!!.itemCount > 0)
        }
    }

}