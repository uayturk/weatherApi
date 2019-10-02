package com.ufuk.weatherApi.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@Builder
public class Imperial {

  @SerializedName(value = "Value")
  private int value;

  @SerializedName(value = "Unit")
  private String unit;

  @SerializedName(value = "UnitType")
  private int unitType;


}
