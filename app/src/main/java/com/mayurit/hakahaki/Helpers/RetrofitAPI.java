package com.mayurit.hakahaki.Helpers;

import com.mayurit.hakahaki.Model.CategoryModel;
import com.mayurit.hakahaki.Model.NewsListModel;
import com.mayurit.hakahaki.Model.VideoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


        @GET("./news-api/category/")
        Call<List<CategoryModel>> getCategoryList();

        @Headers("Cache-Control:no-cache")
        @GET("./news-api/category/")
        Call<List<NewsListModel>> getCategoryLimitNews(@Query("category") int category, @Query("offset") int offset, @Query("limit") int limit);

        @Headers("Cache-Control:no-cache")
        @GET("./news-api/detail/")
        Call<List<NewsListModel>> getPostDetail(@Query("id") String id);

        @Headers("Cache-Control:no-cache")
        @GET("./news-api/")
        Call<List<VideoModel>> getVideoList(@Query("ptype") String ptype, @Query("offset") int offset, @Query("limit") int limit);

        @Headers("Cache-Control:no-cache")
        @GET("./news-api/detail/")
        Call<VideoModel> getVideoDetail(@Query("ptype") String ptype,@Query("id") String id);



     /*   @Headers("Cache-Control:no-cache")
        @GET("./news-api/news_id/")
        Call<List<NewsListModel>> getPostDetail(@Query("news_id") String news_id);*/


    }
}
