package com.ufuk.weatherApi.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountryBased {

  private String ID;

  @SerializedName(value = "LocalizedName")
  private String localizedName;

  @SerializedName(value = "EnglishName")
  private String englishName;

}
