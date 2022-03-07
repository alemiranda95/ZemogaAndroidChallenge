package py.com.alemiranda95.zemoga.ui.interfaces

import io.reactivex.rxjava3.subjects.PublishSubject
import py.com.alemiranda95.zemoga.utils.PostAction
import py.com.alemiranda95.zemoga.utils.RxBus

interface IBasePostsFragment {
    val postActionSubject: PublishSubject<PostAction>
        get() = RxBus.postActionSubject
    fun deleteAll()
}