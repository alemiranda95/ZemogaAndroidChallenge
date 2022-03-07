package py.com.alemiranda95.zemoga.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.domain.model.CommentDto

class CommentAdapter(
    private val context: Context,
) : BaseRecyclerAdapter<CommentAdapter.ViewHolder, CommentDto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataList.get(position)
        holder.bindData(post)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val commentTextVoew: TextView = itemView.findViewById(R.id.textview_comment)

        fun bindData(commentDto: CommentDto) {
            commentTextVoew.text = commentDto.body
        }
    }
}