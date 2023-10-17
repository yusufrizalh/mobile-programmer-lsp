package id.inixindo.mysqlitedb.resources;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import id.inixindo.mysqlitedb.R;

public class ViewDetailProduct extends AppCompatActivity {
    // inisialisasi komponen dalam layout view single product
    TextView txtProductName, txtProductPrice, txtProductDescription;
    Button buttonOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_product);

        // mengenali komponen dalam layout
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        txtProductDescription = findViewById(R.id.txtProductDescription);
        buttonOk = findViewById(R.id.buttonOk);

        // event listener untuk button ok
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // menampilkan seluruh kolom
        txtProductName.setText("Product Name:\n" + getIntent().getExtras().getString("name"));
        txtProductPrice.setText("Product Price:\n" + getIntent().getExtras().getString("price"));
        txtProductDescription.setText("Product Description:\n" + getIntent().getExtras().getString("description"));
    }
}
