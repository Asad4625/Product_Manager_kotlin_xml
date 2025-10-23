package com.example.product_manager

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etQuantity: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnCalculate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotalProducts: TextView
    private lateinit var tvTotalItems: TextView
    private lateinit var tvTotalPrice: TextView

    private val productList = mutableListOf<Product>()
    private lateinit var adapter: ProductAdapter
    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etPrice = findViewById(R.id.etPrice)
        etQuantity = findViewById(R.id.etQuantity)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnCalculate = findViewById(R.id.btnCalculate)
        recyclerView = findViewById(R.id.recyclerView)
        tvTotalProducts = findViewById(R.id.tvTotalProducts)
        tvTotalItems = findViewById(R.id.tvTotalItems)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)

        adapter = ProductAdapter(productList,
            onItemClick = { index -> selectProduct(index) },
            onDeleteConfirmed = { index -> deleteProduct(index) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener { addProduct() }
        btnUpdate.setOnClickListener { updateProduct() }
        btnCalculate.setOnClickListener { calculateTotals() }
    }

    private fun addProduct() {
        val name = etName.text.toString()
        val price = etPrice.text.toString().toDoubleOrNull()
        val qty = etQuantity.text.toString().toIntOrNull()

        if (name.isBlank() || price == null || qty == null) {
            Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
            return
        }

        productList.add(Product(name, price, qty))
        adapter.notifyItemInserted(productList.size - 1)
        clearFields()
    }

    private fun updateProduct() {
        if (selectedIndex == -1) {
            Toast.makeText(this, "Select a product to update", Toast.LENGTH_SHORT).show()
            return
        }

        val name = etName.text.toString()
        val price = etPrice.text.toString().toDoubleOrNull()
        val qty = etQuantity.text.toString().toIntOrNull()

        if (name.isBlank() || price == null || qty == null) {
            Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
            return
        }

        productList[selectedIndex] = Product(name, price, qty)
        adapter.notifyItemChanged(selectedIndex)
        selectedIndex = -1
        clearFields()
    }

    private fun selectProduct(index: Int) {
        val p = productList[index]
        etName.setText(p.name)
        etPrice.setText(p.price.toString())
        etQuantity.setText(p.quantity.toString())
        selectedIndex = index
    }

    private fun deleteProduct(index: Int) {
        productList.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    private fun calculateTotals() {
        val totalProducts = productList.size
        val totalItems = productList.sumOf { it.quantity }
        val totalPrice = productList.sumOf { it.totalPriceWithTax }

        tvTotalProducts.text = "Total Products: $totalProducts"
        tvTotalItems.text = "Total Items: $totalItems"
        tvTotalPrice.text = "Total Price: %.2f".format(totalPrice)
    }

    private fun clearFields() {
        etName.text.clear()
        etPrice.text.clear()
        etQuantity.text.clear()
    }
}
