package id.inixindo.mysqlitedb.resources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import id.inixindo.mysqlitedb.R;
import id.inixindo.mysqlitedb.database.DBDataSource;
import id.inixindo.mysqlitedb.models.Product;

public class CreateNewProduct extends AppCompatActivity implements View.OnClickListener {
    // menginisialisasi seluruh komponen pada layout create product
    private EditText editName, editPrice, editDescription;
    private Button buttonSubmit, buttonViewProducts;

    // menginisialisasi data source
    private DBDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product_layout);

        // mengenali seluruh komponen yang ada di layout create product
        editName = findViewById(R.id.editProductName);
        editPrice = findViewById(R.id.editProductPrice);
        editDescription = findViewById(R.id.editProductDescription);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonViewProducts = findViewById(R.id.buttonViewProducts);

        // membuat event handling pada button
        buttonSubmit.setOnClickListener(this);
        buttonViewProducts.setOnClickListener(this);

        // memanggil data source
        dataSource = new DBDataSource(this);
        dataSource.open();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonViewProducts) {
            // diarahkan ke ViewAllProducts
            startActivity(new Intent(getApplicationContext(), ViewAllProducts.class));
        } else if (view.getId() == R.id.buttonSubmit) {
            // proses menyimpan data
            String name = null, price = null, description = null;
            Product product = null;
            // pengecekan edit text tidak boleh kosong
            if (editName.getText().toString().trim() == "") {
                Toast.makeText(this, "Product name cannot be empty!", Toast.LENGTH_SHORT).show();
            } else if (editPrice.getText().toString().trim() == "") {
                Toast.makeText(this, "Product price cannot be empty!", Toast.LENGTH_SHORT).show();
            } else if (editDescription.getText().toString().trim() == "") {
                Toast.makeText(this, "Product description cannot be empty!", Toast.LENGTH_SHORT).show();
            } else {
                name = editName.getText().toString().trim();
                price = editPrice.getText().toString().trim();
                description = editDescription.getText().toString().trim();
                product = dataSource.createProduct(name, price, description);
                Toast.makeText(this, "Success create new product:\n" +
                        "Name: " + product.getName() + " Price: " + product.getPrice(), Toast.LENGTH_SHORT).show();
                // arahkan kembali ke ViewAllProducts
                startActivity(new Intent(getApplicationContext(), ViewAllProducts.class));
            }
        }
    }
}
