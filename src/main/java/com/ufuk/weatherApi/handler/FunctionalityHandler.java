package com.ufuk.weatherApi.handler;

import com.ufuk.weatherApi.model.BaseObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

import net.bytebuddy.agent.builder.AgentBuilder.LambdaInstrumentationStrategy;
import org.springframework.boot.configurationprocessor.json.JSONException;

/**
 * Reading Json values from developer.accuweather.com(AccuWeather APIs) and save MongoDb.
 */
public interface FunctionalityHandler {

    /**
     * Reads json values from url and saves MongoDb.
     */
    void jsonReadAndSaveDb() throws IOException, JSONException;
    //void jsonReadAndSaveDb(String apiKey, String language, boolean details) throws IOException, JSONException;
    /**
     * @param key key to return weatherApi objects below it.
     * @return returns a list of {@link BaseObject}
     */
    List<BaseObject> loadValuesFromMongoDb(String key);

    List<BaseObject> fourBiggestCityForFirstLook(String englishName);

    List<BaseObject> getChosenCountryWeather(String country);

    List<BaseObject> getCurrentCountryWeather(String country ,String city);

}
