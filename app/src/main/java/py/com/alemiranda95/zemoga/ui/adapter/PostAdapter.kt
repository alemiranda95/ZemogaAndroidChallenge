package py.com.alemiranda95.zemoga.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import py.com.alemiranda95.zemoga.R
import py.com.alemiranda95.zemoga.domain.model.postinfo.PostInfo
import py.com.alemiranda95.zemoga.utils.extension.hide
import py.com.alemiranda95.zemoga.utils.extension.show

class PostAdapter(
    private val context: Context,
    private val listener: OnClickListener
) : BaseRecyclerAdapter<PostAdapter.ViewHolder, PostInfo>() {

    interface OnClickListener {
        fun onClick(postInfo: PostInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataList.get(position)
        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_anim)
        holder.bindData(post)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postCardView: CardView = itemView.findViewById(R.id.cardview_post)
        private val titleTextView: TextView = itemView.findViewById(R.id.textview_post_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textview_post_description)
        private val unreadImageView: ImageView = itemView.findViewById(R.id.imageview_unread_icon)

        fun bindData(postInfo: PostInfo) {
            titleTextView.text = postInfo.title
            descriptionTextView.text = postInfo.body

            if (postInfo.read) unreadImageView.hide() else unreadImageView.show()

            itemView.setOnClickListener { v: View? ->
                listener.onClick(postInfo)
            }

            descriptionTextView.setTextColor(
                ColorStateList.valueOf(
                    context.getColor(
                        if (postInfo.favorite) R.color.black else R.color.text_color
                    )
                )
            )

            if(postInfo.favorite) {
                titleTextView.setTextColor(context.getColor(R.color.black))
                titleTextView.typeface = Typeface.DEFAULT
            } else if (postInfo.read) {
                titleTextView.setTextColor(context.getColor(R.color.text_color))
                titleTextView.typeface = Typeface.DEFAULT
            } else {
                titleTextView.setTextColor(context.getColor(R.color.unread_text_color))
                titleTextView.typeface = Typeface.DEFAULT_BOLD
            }

            postCardView.backgroundTintList =
                ColorStateList.valueOf(
                    context.getColor(
                        if (postInfo.favorite)  R.color.favorite_cardview_color else R.color.cardview_color
                    )
                )
        }
    }
}