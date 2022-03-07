package py.com.alemiranda95.zemoga.utils

import io.reactivex.rxjava3.subjects.PublishSubject

object RxBus {
    val postActionSubject: PublishSubject<PostAction> = PublishSubject.create()
}