package id.inixindo.mysqlitedb.resources;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import id.inixindo.mysqlitedb.R;
import id.inixindo.mysqlitedb.database.DBDataSource;
import id.inixindo.mysqlitedb.models.Product;

public class ViewAllProducts extends ListActivity {
    // inisialisasi data source
    private DBDataSource dataSource;
    private ArrayList<Product> products;

    ListView listView;
    FloatingActionButton fabCreateProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_products_layout);

        dataSource = new DBDataSource(this);
        dataSource.open();  // membuka koneksi database

        // ambil semua data products
        products = dataSource.getAllProducts();
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(
                this, android.R.layout.simple_list_item_1, products
        );
        setListAdapter(adapter);

        listView = findViewById(android.R.id.list);
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // pilihan untuk edit atau delete menggunakan dialog
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // melihat detail product
            }
        });

        fabCreateProduct = findViewById(R.id.fabCreateProduct);
        fabCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuka form create new product
                Toast.makeText(ViewAllProducts.this, "Membuka Form Create Product", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), CreateNewProduct.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}
