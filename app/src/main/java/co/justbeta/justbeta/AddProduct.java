package co.justbeta.justbeta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.List;

import co.justbeta.models.Product;
import co.justbeta.models.User;
import co.justbeta.util.AppConstants;

public class AddProduct extends AppCompatActivity {

    private static final String TAG = "AddProduct";
    TextView productTitleTV;
    TextView productVideoUrlTV;
    Button btnCreateProduct;
    private List<User> mUser = User.listAll(User.class);
    View.OnClickListener createProductOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            JsonObject productDetails = new JsonObject();
            final String productTitle = productTitleTV.getText().toString();
            String productVideoUrl = productVideoUrlTV.getText().toString();
            if (TextUtils.isEmpty(productTitle)) {
                productTitleTV.setError("Title cannot be empty");
            }

            if (TextUtils.isEmpty(productVideoUrl)) {
                productVideoUrlTV.setError("Video url cannot be null");
            }
            btnCreateProduct.setEnabled(false);
            productDetails.addProperty("title", productTitle);
            productDetails.addProperty("video_url", productVideoUrl);
            productDetails.addProperty("user_id", mUser.get(0).getUserId());

            Ion.with(getApplicationContext())
                    .load(AppConstants.USER_END_POINT + mUser.get(0).getUserId() + "/products/")
                    .setHeader("Accept", "application/vnd.justbeta.v1")
                    .setHeader("Authorization", mUser.get(0).getAuth())
                    .setJsonObjectBody(productDetails)
                    .asJsonObject()
                    .withResponse()
                    .setCallback(new FutureCallback<Response<JsonObject>>() {
                        @Override
                        public void onCompleted(Exception e, Response<JsonObject> result) {
                            if (e == null) {
                                Log.e(TAG, " >>>>> " + result.getHeaders().code());
                                Log.e(TAG, " >>>>> " + result);
                                if (result.getHeaders().code() == 201) {
                                    JsonObject product = result.getResult().get("product").getAsJsonObject();
                                    Log.e(TAG, " >>>>> result" + product);
                                    Log.e(TAG, " >>>>> result id" + product.get("id").getAsString());

                                    Product newProduct = new Product(product.get("id").getAsString(),
                                            product.get("title").getAsString(),
                                            product.get("video_url").getAsString(),
                                            product.get("beta").getAsBoolean(),
                                            product.get("user").getAsJsonObject().get("id").getAsString(),
                                            product.get("user").getAsJsonObject().get("email").getAsString());
                                    newProduct.save();

                                    Toast.makeText(getApplicationContext(), product.get("title").getAsString() + " has been saved", Toast.LENGTH_LONG).show();
                                    productTitleTV.setText("");
                                    productVideoUrlTV.setText("");
                                    Log.e(TAG, " >>>>> " + product);
                                } else if (result.getHeaders().code() == 422) {
                                    Log.e(TAG, " >>>>> " + result.getResult().getAsJsonObject("errors").get("message"));
                                    Toast.makeText(getApplicationContext(), result.getResult().getAsJsonObject("errors").getAsJsonObject("message").toString(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Could not create product", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Could not create product", Toast.LENGTH_LONG).show();
                                Log.e(TAG, " >>>>> " + e.getMessage());
                            }

                            btnCreateProduct.setEnabled(true);
                        }
                    });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        productTitleTV = (TextView) findViewById(R.id.add_product_name);
        productVideoUrlTV = (TextView) findViewById(R.id.add_product_video_url);
        btnCreateProduct = (Button) findViewById(R.id.createProductBTN);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnCreateProduct.setOnClickListener(createProductOnClickListener);
    }


}
