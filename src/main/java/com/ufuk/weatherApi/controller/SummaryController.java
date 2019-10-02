package com.ufuk.weatherApi.controller;

import com.ufuk.weatherApi.handler.FunctionalityHandler;
import com.ufuk.weatherApi.model.BaseObject;
import com.ufuk.weatherApi.model.ChosenCountryName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jsoup.Jsoup;

/**
 * SummaryController is read datas from MongoDb and display these on html side.
 */
@Controller
public class SummaryController {

  @Autowired
  private FunctionalityHandler functionalityHandler;
  @Autowired
  private ChosenCountryName chosenCountryName;


  @RequestMapping(value = "/getCountryNameFromJquery", method = RequestMethod.POST)
  /*@ResponseBody*/  //if you not use this annotation,you get " javax.servlet.ServletException: Circular view path [...] "
  private String getWeatherValues(String selectedCountryName) {
    List<List<BaseObject>> weatherValues = new ArrayList<>();
    chosenCountryName.setChosenCountryName(selectedCountryName.replaceAll("\"", "").trim());

    weatherValues.add(functionalityHandler.getChosenCountryWeather(selectedCountryName.replaceAll("\"", "").trim()));

    return "redirect:/weatherApi";
  }


  @RequestMapping(value = "/weatherApi", method = RequestMethod.GET)
  /*@Scope(value = "session")*/
  public String summary(ModelMap modelMap) throws IOException {
    //This code takes current country from url
    String json = Jsoup.connect("https://api.ipdata.co/?api-key=0083485ee882335a413ef89a99cb902453f142de3c96e21dd4b1e3b1").ignoreContentType(true).execute().body();
    //Json converting Jsonobject,so we can get specific JSON data.
    JSONObject obj = new JSONObject(json);
    //Converting string because getCurrentCountryWeather takes string.
    String currentCountryName = obj.getString("country_name").replaceAll("\"", "").trim().toString();
    String currentCityName = obj.getString("city").replaceAll("\"", "").trim().toString();

    modelMap.addAttribute("summaryChosen", functionalityHandler.getChosenCountryWeather(chosenCountryName.getChosenCountryName())); // getCurrentCountryWeather method can use for chosen country too.

    modelMap.addAttribute("summaryCurrent", functionalityHandler.getCurrentCountryWeather(currentCountryName,currentCityName));

    modelMap.addAttribute("summaryLon", functionalityHandler.fourBiggestCityForFirstLook("London"));

    modelMap.addAttribute("summaryIst", functionalityHandler.fourBiggestCityForFirstLook("Istanbul"));

    modelMap.addAttribute("summaryMscw", functionalityHandler.fourBiggestCityForFirstLook("Moscow"));

    modelMap.addAttribute("summaryNY", functionalityHandler.fourBiggestCityForFirstLook("New York"));


    return "summary";
  }



}
