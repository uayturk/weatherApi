package com.ufuk.weatherApi.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Elevation {

  @SerializedName(value = "Metric")
  private Metric metric;

  @SerializedName(value = "Imperial")
  private Imperial imperial;

}
