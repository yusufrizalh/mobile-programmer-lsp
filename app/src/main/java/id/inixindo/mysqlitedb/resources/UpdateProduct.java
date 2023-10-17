package id.inixindo.mysqlitedb.resources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import id.inixindo.mysqlitedb.R;
import id.inixindo.mysqlitedb.database.DBDataSource;
import id.inixindo.mysqlitedb.models.Product;

public class UpdateProduct extends AppCompatActivity {
    // inisialisasi seluruh komponen dari layout edit product
    private EditText editProductName, editProductPrice, editProductDescription;
    private Button buttonUpdate;
    private TextView txtProductId;

    // inisialisasi data source
    private DBDataSource dataSource;

    // properties yang akan diubah
    private long id;
    private String name, price, description;

    private Product product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_layout);

        // mengenali seluruh komponen
        txtProductId = findViewById(R.id.txtProductId);
        editProductName = findViewById(R.id.editProductName);
        editProductPrice = findViewById(R.id.editProductPrice);
        editProductDescription = findViewById(R.id.editProductDescription);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // membuka koneksi database
        dataSource = new DBDataSource(this);
        dataSource.open();

        // ambil data dari getExtras
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getLong("id");
        name = bundle.getString("name");
        price = bundle.getString("price");
        description = bundle.getString("description");

        // tulis data kedalam edit text
        txtProductId.append(String.valueOf(id));
        editProductName.setText(name);
        editProductPrice.setText(price);
        editProductDescription.setText(description);

        // event listener button update
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product = new Product();
                product.setName(editProductName.getText().toString());
                product.setPrice(editProductPrice.getText().toString());
                product.setDescription(editProductDescription.getText().toString());
                product.setId(id);

                dataSource.updateProduct(product);

                startActivity(new Intent(getApplicationContext(), ViewAllProducts.class));
                UpdateProduct.this.finish();
                dataSource.close();
            }
        });
    }
}
