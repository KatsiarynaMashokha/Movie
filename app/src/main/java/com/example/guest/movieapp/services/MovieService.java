package com.example.guest.movieapp.services;

import com.example.guest.movieapp.Constants;
import com.example.guest.movieapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 10/18/17.
 */

public class MovieService {
    public static void findMovies( Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.MOVIE_POPULARITY_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.MOVIE_LANGUAGE_QUERY_PARAMETER, Constants.MOVIE_LANGUAGE_PARAMETER)
                .addQueryParameter(Constants.MOVIE_PAGE_QUERY_PARAMETER, Constants.MOVIE_PAGE_PARAMETER)
                .addQueryParameter(Constants.MOVIE_KEY_QUERY_PARAMETER, Constants.MOVIE_KEY);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Movie> processResults(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            for(int i=0; i < resultsArray.length(); i++){
                String title = resultsArray.getJSONObject(i).getString("title");
                int rating = resultsArray.getJSONObject(i).getInt("vote_average");
                String description = resultsArray.getJSONObject(i).getString("overview");
                String image = resultsArray.getJSONObject(i).getString("poster_path");
                String release = resultsArray.getJSONObject(i).getString("release_date");

                Movie movie = new Movie(title, rating, description, release, image);
                movies.add(movie);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
