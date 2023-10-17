package id.inixindo.mysqlitedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import id.inixindo.mysqlitedb.models.Product;

public class DBDataSource {
    // inisialisasi SQLite Database
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    // mengambil semua nama kolom
    private String[] allColumns = {
            DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_PRICE, DBHelper.COLUMN_DESCRIPTION
    };

    // constructor
    public DBDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // membuka koneksi dengan database Sqlite
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // menutup koneksi database Sqlite
    public void close() {
        dbHelper.close();
    }

    // membuat operasional CRUD (Create Read Update Delete)

    // membuat method create product
    public Product createProduct(String name, String price, String description) {
        // Content Values digunakan untuk mendistribusikan parameter dengan nama kolom di tabel products
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_PRICE, price);
        values.put(DBHelper.COLUMN_DESCRIPTION, description);

        // mengeksekusi perintah INSERT INTO products
        long insertID = database.insert(DBHelper.TABLE_NAME, null, values);

        // Cursor untuk melihat data sudah masuk berdasarkan insertID
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, DBHelper.COLUMN_ID +
                " = " + insertID, null, null, null, null);
        cursor.moveToFirst();

        Product newProduct = cursorToProduct(cursor);
        cursor.close();
        return newProduct;
    }

    // membuat method get all products
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<Product>();  // diawal masih kosong
        // query SELECT ALL
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        // menampilkan seluruh data dari tabel products
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return productList;
    }

    // membuat method get single product by id
    public Product getProductById(long id) {
        Product product = new Product();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns,
                "id=" + id, null, null, null, null);
        cursor.moveToFirst();
        product = cursorToProduct(cursor);
        cursor.close();
        return product;
    }

    // membuat method update product
    public void updateProduct(Product product) {
        // ambil id product yang akan diubah
        String strId = "id=" + product.getId();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, product.getName());
        values.put(DBHelper.COLUMN_PRICE, product.getPrice());
        values.put(DBHelper.COLUMN_DESCRIPTION, product.getDescription());
        database.update(DBHelper.TABLE_NAME, values, strId, null);
    }

    // membuat method delete product
    public void deleteProduct(long id) {
        String strId = "id=" + id;
        database.delete(DBHelper.TABLE_NAME, strId, null);
    }

    private Product cursorToProduct(Cursor cursor) {
        Product product = new Product();
        Log.d("DEBUG PRODUCT", "GET PRODUCT ID: " + cursor.getLong(0));
        product.setId(cursor.getLong(0));
        product.setName(cursor.getString(1));
        product.setPrice(cursor.getString(2));
        product.setDescription(cursor.getString(3));
        return product;
    }
}
