package py.com.alemiranda95.zemoga.ui.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T : RecyclerView.ViewHolder, R> :
    RecyclerView.Adapter<T>() {

    var dataList : List<R> = emptyList()
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return dataList.size
    }
}