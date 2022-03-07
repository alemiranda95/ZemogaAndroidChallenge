package py.com.alemiranda95.zemoga.ui.view

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ExtendedFabScrollListener(
    private val fab: Lazy<ExtendedFloatingActionButton>
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == RecyclerView.SCROLL_STATE_IDLE
            && !fab.value.isExtended
            && recyclerView.computeVerticalScrollOffset() == 0
        ) {
            fab.value.extend()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy != 0 && fab.value.isExtended) {
            fab.value.shrink()
        }
    }
}