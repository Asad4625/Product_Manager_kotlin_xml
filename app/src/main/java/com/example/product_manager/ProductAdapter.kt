package com.example.product_manager

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val productList: MutableList<Product>,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteConfirmed: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val quantity: TextView = view.findViewById(R.id.tvQuantity)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val tax: TextView = view.findViewById(R.id.tvTax)
        val delete: TextView = view.findViewById(R.id.tvDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.name
        holder.quantity.text = product.quantity.toString()
        holder.price.text = "%.2f".format(product.price)
        holder.tax.text = "%.2f".format(product.tax)

        holder.itemView.setOnClickListener { onItemClick(position) }

        holder.delete.setOnClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete '${product.name}'?")
                .setPositiveButton("Yes") { _, _ -> onDeleteConfirmed(position) }
                .setNegativeButton("No", null)
                .show()
        }
    }
}
