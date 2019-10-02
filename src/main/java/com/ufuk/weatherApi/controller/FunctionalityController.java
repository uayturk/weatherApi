package com.ufuk.weatherApi.controller;

import com.ufuk.weatherApi.handler.FunctionalityHandler;
import com.ufuk.weatherApi.model.BaseObject;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Check {@link FunctionalityHandler}.
 */
@Controller
public class FunctionalityController {

  @Autowired
  private final FunctionalityHandler functionalityHandler;

  public FunctionalityController(FunctionalityHandler functionalityHandler) {
    this.functionalityHandler = functionalityHandler;
  }

  /**
   * Check {@link FunctionalityHandler}.
   */
  @RequestMapping(value = "/jsonReadAndSaveDb", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for jsonReadAndSaveDb.\n",
      notes = "jsonReadAndSaveDb is read Json object values from url and then saves values to MongoDb.\n "
  )
  public void jsonReadAndSaveDb(@RequestParam String apiKey, @RequestParam(required = false) String language, @RequestParam(required = false) boolean details) throws IOException, JSONException {
    functionalityHandler.jsonReadAndSaveDb(apiKey, language, details);
  }

  /**
   * @param key key for get specific data from mondoDB.
   * @return returns data which is read from DB.
   */
  @RequestMapping(value = "/loadValuesFromMongoDb", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for loadValuesFromMongoDb.\n",
      notes = "loadValuesFromMongoDb is loads Json object values from MongoDb.\n "
  )
  public List<BaseObject> loadValuesFromMongoDb(@RequestParam(required = false) String key) {
    return functionalityHandler.loadValuesFromMongoDb(key);
  }

  @RequestMapping(value = "/fourBiggestCityForFirstLook", method = {RequestMethod.POST}, produces = "application/json")
  @ResponseBody
  @ApiOperation(value = "Necessary doc is the below for fourBiggestCityForFirstLook.\n",
      notes = "fourBiggestCityForFirstLook trying to load weather values for 4 biggest city.\n "
  )
  public List<BaseObject> fourBiggestCityForFirstLook(@RequestParam(required = false) String englishName) {
    return functionalityHandler.fourBiggestCityForFirstLook(englishName);
  }


}
