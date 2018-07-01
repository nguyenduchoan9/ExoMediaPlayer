package solution.codebox.recorderapp.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_media_player_option.view.*
import solution.codebox.recorderapp.R
import solution.codebox.recorderapp.data.MediaPlayerOption

class MediaPlayerOptionAdapter(var options: List<MediaPlayerOption>, var listener: MediaPlayerOptionListener) : RecyclerView.Adapter<MediaPlayerOptionAdapter.MediaPlayerOptionVH>() {

    interface MediaPlayerOptionListener {
        fun onItemClick(type: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MediaPlayerOptionVH {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_media_player_option, parent, false)
        return MediaPlayerOptionVH(itemView)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: MediaPlayerOptionVH?, position: Int) {
        holder?.bindDataToView(getItemAt(position))
    }

    private fun getItemAt(pos: Int) = options[pos]

    inner class MediaPlayerOptionVH(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvMediaPlayerOption = itemView?.tvMediaPlayer

        fun bindDataToView(option: MediaPlayerOption) {
            tvMediaPlayerOption?.setTextColor(getColor(option.textColor))
            tvMediaPlayerOption?.text = option.option
            tvMediaPlayerOption?.setBackgroundColor(getColor(option.backgroundColor))
            itemView.setOnClickListener {
                listener.onItemClick(option.option)
            }
        }

        private fun getColor(color: Int): Int {
            return ContextCompat.getColor(itemView.context, color)
        }
    }
}