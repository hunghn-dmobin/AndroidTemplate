package appsgenz.template.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @author HungHN on 09/19/2023.
 */

abstract class AppListAdapter<T, VH : RecyclerView.ViewHolder>(
    private val diffItem: ItemCallback<T>
) : ListAdapter<T, VH>(diffItem) {

    protected var onItemClick: ItemClick<T>? = null

    fun setItemClick(click: ItemClick<T>) {
        this.onItemClick = click
    }

    fun bindItemClickListener(view: View, position: Int, item: T) {
        view.setOnClickListener {
            onItemClick?.invoke(position, item)
        }
    }
}
