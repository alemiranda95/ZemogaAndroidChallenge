package py.com.alemiranda95.zemoga.ui.interfaces

interface IBaseViewModel<T> {
    fun errorViewState(error: String) : T
}