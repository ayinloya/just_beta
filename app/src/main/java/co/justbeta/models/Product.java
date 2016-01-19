package co.justbeta.models;

import com.orm.SugarRecord;

/**
 * Created by barnabas on 10/12/15
 */
public class Product extends SugarRecord<Product> {
    String productId;
    String title;
    String video_url;
    Boolean beta;
    String userId;
    String email;

    public Product() {
    }
    public Product(String productId, String title, String video_url, Boolean beta, String userId, String email) {
        this.productId = productId;
        this.title = title;
        this.video_url = video_url;
        this.beta = beta;
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }
}
