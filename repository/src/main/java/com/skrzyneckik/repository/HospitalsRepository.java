package com.skrzyneckik.repository;

import com.skrzyneckik.domain.Hospital;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Func1;

public class HospitalsRepository {

    private final OkHttpClient httpClient;

    public HospitalsRepository() {
        httpClient = new OkHttpClient.Builder().build();
    }

    public Observable<List<Hospital>> hospitals() {
        return Observable
                .fromCallable(new Callable<Response>() {
                    @Override
                    public Response call() throws Exception {
                        return httpClient.newCall(
                                new Request.Builder().url("https://data.gov.uk/data/resource/nhschoices/Hospital.csv").build())
                                .execute();
                    }
                })
                .filter(new Func1<Response, Boolean>() {
                    @Override
                    public Boolean call(Response response) {
                        return response.isSuccessful();
                    }
                })
                .map(new Func1<Response, Object>() {
                    @Override
                    public Object call(Response response) {
                        return null;
                    }
                })
                .map(new Func1<Object, List<Hospital>>() {
                    @Override
                    public List<Hospital> call(Object ignored) {
                        return Arrays.asList(
                                new Hospital("Newquay Hospital"),
                                new Hospital("Bridgewater Hospital")
                        );
                    }
                });
    }
}
