package co.justbeta.justbeta;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.justbeta.FeedRecyclerAdapter;
import co.justbeta.models.Product;
import co.justbeta.models.User;
import co.justbeta.util.EndlessRecyclerOnScrollListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    List<User> mUser = User.listAll(User.class);
    List<Product> mProducts;
    FeedRecyclerAdapter mFeedRecyclerAdapter;

    LinearLayoutManager linearLayoutLM;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutLM) {
        @Override
        public void onLoadMore(int current_page) {
            getAllProducts();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textViewWelcome = (TextView) findViewById(R.id.welcome);
        RecyclerView productRV = (RecyclerView) findViewById(R.id.content_product_feed);

        productRV.setHasFixedSize(true);

        linearLayoutLM = new LinearLayoutManager(getApplicationContext());
        productRV.setLayoutManager(linearLayoutLM);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mFeedRecyclerAdapter = new FeedRecyclerAdapter();
        productRV.setAdapter(mFeedRecyclerAdapter);

        textViewWelcome.setText("Welcome: " + mUser.get(0).getEmail());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView profileImageDawer = (ImageView) navigationView.findViewById(R.id.drawer_layout_profile_image);
        TextView profileEmailDawer = (TextView) navigationView.findViewById(R.id.drawer_layout_email);
//        profileEmailDawer.setText(mUser.get(0).getEmail());
        navigationView.setNavigationItemSelectedListener(this);
        try {
            mProducts = Product.listAll(Product.class);
        } catch (SQLiteException e) {

        }
        mFeedRecyclerAdapter.setProducts(getAllProducts());
        Log.e(TAG, " >>> the size of the products table " + getAllProducts().size());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private List<Product> getAllProducts() {
        Log.e(TAG, " >>> ");
        try {
            List<Product> products = Product.listAll(Product.class);
            Log.e(TAG, " >>> " + products.size());
            return products;
        } catch (Exception e) {
            return null;
        }
    }

    private List<Product> getUserProduct() {
        try {
            List<Product> products = Product.find(Product.class, "userId =?", mUser.get(0).getUserId());
            return (products != null) ? products : null;
        } catch (SQLiteException e) {
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.add_product) {
            startActivity(new Intent(getApplicationContext(), AddProduct.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_products) {
            mFeedRecyclerAdapter.setProducts(getUserProduct());
            // Handle the camera action
        } else if (id == R.id.nav_products_feed) {
            mFeedRecyclerAdapter.setProducts(getAllProducts());
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(getApplicationContext(), ProductScroll.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(getApplicationContext(), HollyViewScroll.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
