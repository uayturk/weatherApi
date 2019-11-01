package com.ufuk.weatherApi.handler.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.google.gson.Gson;
import com.ufuk.weatherApi.beans.CollectionNames;
import com.ufuk.weatherApi.handler.FunctionalityHandler;
import com.ufuk.weatherApi.model.BaseObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Slf4j
class FunctionalityHandlerImpl implements FunctionalityHandler {

  private final MongoTemplate mongoTemplate;

  @Autowired
  FunctionalityHandlerImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  /*
   * EmZIJo7SS05EHTF2qiQa5aABmJqtG9gs 1.apikey(kullanıma acik)
   * url : http://dataservice.accuweather.com/currentconditions/v1/topcities/50?apikey="+apiKey+"&language=en&details=true&format=json
   **/
  //public void jsonReadAndSaveDb(String apiKey, String language, boolean details) throws IOException

  //@Override
  @Scheduled(fixedRate = 1000*60*30)
  public void jsonReadAndSaveDb() throws IOException {
    //String apiKey ="EmZIJo7SS05EHTF2qiQa5aABmJqtG9gs";

    //String url = "http://dataservice.accuweather.com/currentconditions/v1/topcities/150?apikey="+apiKey+"&language=en&details=true&ormat=json";
    String url = "http://dataservice.accuweather.com/currentconditions/v1/topcities/150?apikey=EmZIJo7SS05EHTF2qiQa5aABmJqtG9gs&language=en&details=true&ormat=json";
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));



/*    FileReader fr = new FileReader("/home/ufuk/Masaüstü/weatherApiJsonDatas3");
    BufferedReader in = new BufferedReader(fr);*/


    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    /**
     * In here JSON formant comes like:
     *  [
     *   {
     *      "Key": "28143",
     *      "LocalizedName": "Dhaka",
     *      .
     *      .
     *   },
     *   {
     *      "Key": "113487",
     *      "LocalizedName": "Kinshasa",
     *      .
     *      .
     *   }
     *  ]
     */

    /**
     * This is the professional way of setting fields and saving datas to MongoDb. Gson is the our hero here.
     * Our datas coming from AccuWeather API's as a JSON type. For now List do not support to using like List<BaseObject>.
     * Therefore we are using it with Object type.( List<Object> ) . We are taking the List in Object type.
     * Then we are converting our Object type to JSON type.
     * Lastly we are integrating JSON type to our BaseObject type.
     * As you see,it's clearly such a nice way to setting fields.
     */
    Gson gson = new Gson();
    List<Object> baseObjects = gson.fromJson(response.toString(), List.class);//Converting JSON to Java Object. From response.toString() (JSON) to List.class(JAVA OBJECT)
    for (Object o : baseObjects) {
      String jsonObject = gson.toJson(o); //Converting our Object type to JSON type.
      BaseObject baseObject = gson.fromJson(jsonObject, BaseObject.class);//Converting our JSON type to baseobject type.In here,our datas are filled in Baseobject fields.
                                                                         //From jsonObject(JSON) to BaseObject.class(BaseObject type)
      log.info("trying to save weather object: {}", baseObject);
      mongoTemplate.save(baseObject, CollectionNames.OBJECTS.toString());
      log.info("successfully saved weather object with key{}", baseObject.getKey());

      System.out.println(baseObject);
    }

    /**
     * The codes in below which is between comment lines can set fields and save to MongoDb too. But I am not
     * suggesting you to use this way. This is such a unprofessional way and mostly suitable for beginners or juniors.
     * As you see,it has not good look too.
     */

   /* BaseObject object = new BaseObject();
    JSONArray myResponse = new JSONArray(response.toString());
    for(int i=0 ; i< myResponse.length() ; i++){

      object.setKey(myResponse.getJSONObject(i).getString("Key"));

      object.setLocalizedName(myResponse.getJSONObject(i).getString("LocalizedName"));
      object.setEnglishName(myResponse.getJSONObject(i).getString("EnglishName"));
      object.setCountry(Set.of(CountryBased.builder()
          .ID(myResponse.getJSONObject(i).getJSONObject("Country").getString("ID"))
          .localizedName(myResponse.getJSONObject(i).getJSONObject("Country").getString("LocalizedName"))
          .englishName(myResponse.getJSONObject(i).getJSONObject("Country").getString("EnglishName")).build()));

      object.setTimeZone(Set.of(TimeZone.builder()
          .code(myResponse.getJSONObject(i).getJSONObject("TimeZone").getString("Code"))
          .name(myResponse.getJSONObject(i).getJSONObject("TimeZone").getString("Name"))
          .gmtOffset(myResponse.getJSONObject(i).getJSONObject("TimeZone").getInt("GmtOffset"))
          .isDaylightSaving(myResponse.getJSONObject(i).getJSONObject("TimeZone").getBoolean("IsDaylightSaving"))
          .nextOffsetChange(myResponse.getJSONObject(i).getJSONObject("TimeZone").get("NextOffsetChange")).build()));

      object.setGeoPosition(Set.of(GeoPosition.builder()
          .latitude(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getDouble("Latitude"))
          .longitude(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getDouble("Longitude"))
          .elevation(Set.of(Elevation.builder()
              .metric(Set.of(Metric.builder()
                  .value(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getJSONObject("Elevation").getJSONObject("Metric").getDouble("Value"))
                  .unit(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getJSONObject("Elevation").getJSONObject("Metric").getString("Unit"))
                  .unitType(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getJSONObject("Elevation").getJSONObject("Metric").getInt("UnitType")).build()))
              .imperial(Set.of(Imperial.builder()
                  .value(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getJSONObject("Elevation").getJSONObject("Imperial").getInt("Value"))
                  .unit(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getJSONObject("Elevation").getJSONObject("Imperial").getString("Unit"))
                  .unitType(myResponse.getJSONObject(i).getJSONObject("GeoPosition").getJSONObject("Elevation").getJSONObject("Imperial").getInt("UnitType")).build())).build())).build()));

      object.setLocalObservationDateTime(myResponse.getJSONObject(i).getString("LocalObservationDateTime"));
      object.setEpochTime(myResponse.getJSONObject(i).getInt("EpochTime"));
      object.setWeatherText(myResponse.getJSONObject(i).getString("WeatherText"));
      object.setWeatherIcon(myResponse.getJSONObject(i).getInt("WeatherIcon"));
      object.setDayTime(myResponse.getJSONObject(i).getBoolean("IsDayTime"));

      object.setTemperature(Set.of(Temperature.builder()
          .metric(Set.of(Metric.builder()
              .value(myResponse.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Metric").getDouble("Value"))
              .unit(myResponse.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Metric").getString("Unit"))
              .unitType(myResponse.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Metric").getInt("UnitType")).build()))
          .imperial(Set.of(Imperial.builder()
              .value(myResponse.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Imperial").getInt("Value"))
              .unit(myResponse.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Imperial").getString("Unit"))
              .unitType(myResponse.getJSONObject(i).getJSONObject("Temperature").getJSONObject("Imperial").getInt("UnitType")).build())).build()));

      object.setMobileLink(myResponse.getJSONObject(i).getString("MobileLink"));
      object.setLink(myResponse.getJSONObject(i).getString("Link"));

    }

    log.info("trying to save weather object: {}", object);
    mongoTemplate.save(object,CollectionNames.OBJECTS.toString());
    log.info("successfully saved weather object with key{}", object.getKey());*/

  }


  /**
   * loadValuesFromMongoDb loads weather values with using key.
   * @param key key to return weatherApi objects below it.
   * @return valid results.
   */
  public  List<BaseObject> loadValuesFromMongoDb(String key){
    log.info("trying to load weather values from key : {}",key);

    Query query = new Query(where("key").is(key));
    //Query query = new Query(); You can take all datas from MongoDb if you use query like that
    query.fields().include("key")
        .include("localizedName")
        .include("englishName")
        .include("country")
        .include("timeZone")
        .include("geoPosition")
        .include("localObservationDateTime")
        .include("epochTime")
        .include("weatherText")
        .include("weatherIcon")
        .include("isDayTime")
        .include("temperature")
        .include("mobileLink")
        .include("link");

    log.info("query to fetch weratherApi objects: {}", query);
    List<BaseObject> result = mongoTemplate.find(query,BaseObject.class,CollectionNames.OBJECTS.toString());
    log.info("successfully fetched result size: {}", result.size());
    return result;

  }

  /**
   * fourBiggestCityForFirstLook is loads weather values for 4 biggest city in front end's first opening view.
   * @param englishName valid city name.
   * @return valid results.
   */
  public List<BaseObject> fourBiggestCityForFirstLook(String englishName){
    log.info("trying to load weather values for 4 biggest city in front end's first opening view .");

    Query query = new Query(where("englishName").is(englishName));
    query.fields().include("englishName")
                  .include("country")
                  .include("temperature")
                  .include("weatherText");

    log.info("query to fetch country and city objects: {}", query);
    List<BaseObject> result = mongoTemplate.find(query,BaseObject.class,CollectionNames.OBJECTS.toString());
    log.info("successfully fetched result size: {}", result.size());
    log.info("FINALLY RESULT: {}", result);
    return result;

  }

  /**
   * getChosenCountryWeather returns weather values for chosen country.
   * @param country valid country name.
   * @return valid results.
   */
  public List<BaseObject> getChosenCountryWeather(String country){
    log.info("trying to get weather values for CURRENT country in front end's first opening view {}.",country);
    Query query = new Query(where("country.englishName").is(country));
    query.fields().include("englishName")
                  .include("country")
                  .include("temperature")
                  .include("weatherText");
    log.info("query to fetch CHOSEN country and city objects: {}", query);
    List<BaseObject> result = mongoTemplate.find(query,BaseObject.class,CollectionNames.OBJECTS.toString());
    log.info("successfully fetched result size: {}", result.size());
    log.info("FINALLY RESULT CHOSEN COUNTRY: {}", result);
    return result;
  }

  /**
   * getCurrentCountryWeather returns current country weather conditions.
   * @param country valid country name.
   * @param city valid city name.
   * @return valid results.
   */
  public List<BaseObject> getCurrentCountryWeather(String country,String city){
    log.info("trying to get weather values for CURRENT country in front end's first opening view .",country);

    List<BaseObject> result = null;

    Query query = new Query(where("country.englishName").is(country));
    query.fields().include("englishName")
        .include("country")
        .include("temperature")
        .include("weatherText");
    List<BaseObject> resultQuery = mongoTemplate.find(query,BaseObject.class,CollectionNames.OBJECTS.toString());

    Query queryWithCity = new Query(where("country.englishName").is(country).and("englishName").is(city));
    queryWithCity.fields().include("englishName")
        .include("country")
        .include("temperature")
        .include("weatherText");
    List<BaseObject> resultQueryWithCity = mongoTemplate.find(queryWithCity,BaseObject.class,CollectionNames.OBJECTS.toString());


    if(!resultQueryWithCity.isEmpty()){
      result = resultQueryWithCity;
    }else {
      result = resultQuery;
    }

    log.info("successfully fetched result size: {}", result.size());
    log.info("FINALLY RESULT CURRENT COUNTRY: {}", result);
    return result;
  }


}
