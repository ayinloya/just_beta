package co.justbeta.util;

import com.orm.SugarApp;

/**
 * Created by barnabas on 10/11/15
 */
public class AppConstants extends SugarApp {

    public static final String BASE_URL = "http://10.0.2.2:3000/";
//    public static final String BASE_URL = "http://justbeta.dev:3000/";
//    public static final String BASE_URL = "http://tranquil-springs-2211.herokuapp.com/";
    public static final String USER_END_POINT = BASE_URL+"users/";
    public static final String PRODUCT_END_POINT = BASE_URL+"products/";
}
