package com.ufuk.weatherApi.model;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseObject {

  /**
   * @SerializedName is the mapping annotation. Ex. if your key value coming from your source like "Key" :432342 , but
   * you used "private String key;" Key and key are different. if you put Key inside @SerializedName and put your head of
   * private String key; ,your code will understand Key is key value. Shortly it will mapping.
   */
  @SerializedName(value = "Key")
  private String key;

  @SerializedName(value = "LocalizedName")
  private String localizedName;

  @SerializedName(value = "EnglishName")
  private String englishName;

  @SerializedName(value = "Country")
  private CountryBased country;

  @SerializedName(value = "TimeZone")
  private TimeZone timeZone;

  @SerializedName(value = "GeoPosition")
  private GeoPosition geoPosition;

  @SerializedName(value = "LocalObservationDateTime")
  private String localObservationDateTime;

  @SerializedName(value = "EpochTime")
  private int epochTime;

  @SerializedName(value = "WeatherText")
  private String weatherText;

  @SerializedName(value = "WeatherIcon")
  private int weatherIcon;

  @SerializedName(value = "IsDayTime")
  private boolean isDayTime;

  @SerializedName(value = "Temperature")
  private Temperature temperature;

  @SerializedName(value = "MobileLink")
  private String mobileLink;

  @SerializedName(value = "Link")
  private String link;

}
