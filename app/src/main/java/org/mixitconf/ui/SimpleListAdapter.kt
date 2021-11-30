package org.mixitconf.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple [RecyclerView.Adapter] with some util methods to
 */
abstract class SimpleListAdapter<T, VH : SimpleItemViewHolder<T>> : RecyclerView.Adapter<VH>() {
    /**
     * List containing all the items displayed by the adapter.
     */
    private var _items: MutableList<T> = mutableListOf()

    val items: List<T>
        get() = _items

    /**
     * Optional listener to receive callbacks when user clicks on a ViewHolder.
     */
    var onItemClickListener: ((T, Int) -> Unit)? = null

    /**
     * @return The layout resource is used for the ViewHolders.
     */
    abstract val itemViewLayoutId: Int

    /**
     * @return a new Instance of the ViewHolder to display.
     */
    abstract fun onCreateViewHolder(itemView: View, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: T = _items[position]
        onItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.invoke(item, position) }
        }
        onBindItemViewHolder(holder, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(itemViewLayoutId, parent, false)
        return onCreateViewHolder(itemView, viewType)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.let { holder.itemView.setOnClickListener(null) }
    }

    override fun getItemCount(): Int = _items.size

    open fun onBindItemViewHolder(holder: VH, item: T) {
        holder.resetViews()
        holder.bindItem(item)
    }

    fun addItem(item: T) {
        _items.add(item)
        notifyItemInserted(_items.size - 1)
    }

    /**
     * Update the items displayed in the adapter
     */
    fun setItems(newItems: List<T>) {
        _items = newItems.toMutableList()
        notifyDataSetChanged()
    }
}

/**
 * Custom abstract [RecyclerView.ViewHolder] containing 2 methods to be overridden:
 * [bindItem] and [resetViews] used to bind the given item [T] and reset the views.
 */
abstract class SimpleItemViewHolder<T>(open val containerView: View) : RecyclerView.ViewHolder(containerView) {
    /**
     * Reset the views in the ViewHolder(clear texts, images, etc..).
     */
    abstract fun resetViews()

    /**
     * Binds the given [item] to the ViewHolder.
     */
    abstract fun bindItem(item: T)

    val context: Context by lazy { containerView.context }
}


