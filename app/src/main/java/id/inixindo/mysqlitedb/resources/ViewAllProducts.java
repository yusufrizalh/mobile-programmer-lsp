package id.inixindo.mysqlitedb.resources;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import id.inixindo.mysqlitedb.R;
import id.inixindo.mysqlitedb.database.DBDataSource;
import id.inixindo.mysqlitedb.models.Product;

public class ViewAllProducts extends ListActivity implements AdapterView.OnItemLongClickListener {
    // inisialisasi data source
    private DBDataSource dataSource;
    private ArrayList<Product> products;

    ListView listView;
    FloatingActionButton fabCreateProduct;
    Button buttonEdit, buttonDelete;

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
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // melihat detail product
                Product product = (Product) getListAdapter().getItem(position);
                switchToGetDetail(product.getId());
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

    public void switchToGetDetail(long id) {
        Product product = dataSource.getProductById(id);
        Intent intent = new Intent(this, ViewDetailProduct.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", product.getId());
        bundle.putString("name", product.getName());
        bundle.putString("price", product.getPrice());
        bundle.putString("description", product.getDescription());
        intent.putExtras(bundle);
        dataSource.close();
        startActivity(intent);
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

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.view_dialog);
        dialog.show();

        final Product product = (Product) getListAdapter().getItem(position);
        buttonEdit = dialog.findViewById(R.id.buttonEdit);
        buttonDelete = dialog.findViewById(R.id.buttonDelete);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuka form edit product
                switchToEditProduct(product.getId());
                dialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSource.deleteProduct(product.getId());
                dialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });

        return true;
    }

    public void switchToEditProduct(long id) {
        Product product = dataSource.getProductById(id);
        Intent intent = new Intent(this, UpdateProduct.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", product.getId());
        bundle.putString("name", product.getName());
        bundle.putString("price", product.getPrice());
        bundle.putString("description", product.getDescription());
        intent.putExtras(bundle);
        ViewAllProducts.this.finish();
        dataSource.close();
        startActivity(intent);
    }
}
