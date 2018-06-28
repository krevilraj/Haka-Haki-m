package com.mayurit.hakahaki.Helpers;

import com.mayurit.hakahaki.Model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Krevilraj on 4/8/2018.
 */

public class RetrofitAPI {
    public static final String url = "http://hakahakionline.com/np/";
    public static final String auth_key = "Auth-Key:simplerestapi";
    public static final String content_type = "Content-Type:application/x-www-form-urlencoded";
    public static final String client_service = "Client-Service:frontend-client";
    public static final String cache_control = "Cache-Control:no-cache";

    public static PostService postService = null;

    public static PostService getService() {

        if (postService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService = retrofit.create(PostService.class);

        }
        return postService;


    }

    public interface PostService {

        @Headers("Cache-Control:no-cache")
        @GET("./news-api/category/")
        Call<List<CategoryModel>> getCategoryList();

        @Headers("Cache-Control:no-cache")
        @GET("./news-api/category/?category={category_id}&offset={offset}&limit={limit}")
        Call<List<CategoryModel>> getCategoryLimitNews(@Path("category_id") String category, @Path("offset") int offset, @Path("limit") String limit);



    }
}
