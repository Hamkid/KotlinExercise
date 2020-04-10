package hu.renes.kotlin.view.base.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<T : IListTypeProvider> : RecyclerView.Adapter<BaseViewHolder<T>>,
    View.OnClickListener {
    val context: Context
    private var items: MutableList<T>
    private lateinit var onClickListener: BaseViewHolder.OnItemClickListener

    constructor(context: Context) {
        this.context = context
        items = ArrayList()
    }

    constructor(context: Context, items: MutableList<T>) {
        this.context = context
        this.items = items
    }

    constructor(
        context: Context,
        items: MutableList<T>,
        onClickListener: BaseViewHolder.OnItemClickListener
    ) {
        this.context = context
        this.items = items
        this.onClickListener = onClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getLayoutType()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun getItemPosition(item: T): Int {
        return if (items.contains(item)) {
            items.indexOf(item)
        } else {
            -1
        }
    }

    fun getItems(): List<T> {
        return items
    }

    fun setItems(items: MutableList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun addItems(items: List<T>?) {
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }

    fun removeItem(item: T) {
        if (items.contains(item)) {
            val position = items.indexOf(item)
            items.remove(item)
            notifyItemRemoved(position)
        }
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeAllItems() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun updateItem(oldItem: T, newItem: T) {
        if (items.contains(oldItem)) {
            val position = items.indexOf(oldItem)
            items[position] = newItem
            notifyItemChanged(position)
        }
    }

    fun notifyItemChanged(item: T) {
        if (items.contains(item)) {
            notifyItemChanged(items.indexOf(item))
        }
    }

    override fun onClick(v: View) {
        val vh: BaseViewHolder<*> = v.tag as BaseViewHolder<*>
        onClickListener.listItemClicked(v, vh.position)
    }

    fun getOnClickListener(): BaseViewHolder.OnItemClickListener? {
        return onClickListener
    }

    fun setOnClickListener(onClickListener: BaseViewHolder.OnItemClickListener) {
        this.onClickListener = onClickListener
    }
}