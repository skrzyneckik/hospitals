package com.skrzyneckik.repository;

import com.skrzyneckik.domain.Hospital;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
                .fromCallable(() -> httpClient.newCall(
                        new Request.Builder().url("https://data.gov.uk/data/resource/nhschoices/Hospital.csv").build())
                        .execute())
                .filter(response -> response.isSuccessful())
                .map(response -> {
                    List<Hospital> hospitals = new ArrayList<>();
                    try {
                        String line = "";
                        BufferedReader br = new BufferedReader(response.body().charStream());

                        //first line includes column names
                        br.readLine();
                        while ((line = br.readLine()) != null) {

                            String[] hospitalParams = line.split("\t");

                            if (hospitalParams.length != Hospital.PARAMS_NUMBER) {
                                hospitalParams = Arrays.copyOf(hospitalParams, Hospital.PARAMS_NUMBER);
                            }

                            hospitals.add(new Hospital(hospitalParams));
                        }
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return hospitals;
                });
    }

    public static Observable.Transformer<List<Hospital>, List<String>> odsCodes() {
        return listObservable -> listObservable.flatMap(hospitals -> Observable.from(hospitals))
                .map(hospital -> hospital.parentODSCode())
                .distinct()
                .toList();
    }
}
