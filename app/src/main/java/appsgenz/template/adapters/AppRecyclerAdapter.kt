package appsgenz.template.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author HungHN on 09/19/2023.
 */

typealias ItemClick<T> = (position: Int, item: T) -> Unit

abstract class AppRecycleAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val mItems: ArrayList<T> = ArrayList()
    protected var onItemClick: ItemClick<T>? = null

    fun setItemClick(click: ItemClick<T>) {
        this.onItemClick = click
    }

    fun addAll(elements: Collection<T>) = this.mItems.addAll(elements)

    fun add(element: T) = this.mItems.add(element)

    fun filter(predicate: (T) -> Boolean): List<T> = this.mItems.filter { predicate.invoke(it) }

    val items: ArrayList<T>
        get() = this.mItems

    fun getItem(position: Int): T? {
        if (position >= mItems.size) {
            return null
        }
        return items[position]
    }

    fun clear() = mItems.clear()

    override fun getItemCount(): Int = items.size

    fun bindItemClickListener(view: View, position: Int, item: T) {

        view.setOnClickListener {
            onItemClick?.invoke(position, item)
        }
    }
}
