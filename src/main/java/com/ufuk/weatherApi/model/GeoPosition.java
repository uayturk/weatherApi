package com.ufuk.weatherApi.model;


import com.google.gson.annotations.SerializedName;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@Builder
public class GeoPosition {

  @SerializedName(value = "Latitude")
  private Double latitude;

  @SerializedName(value = "Longitude")
  private Double longitude;

  @SerializedName(value = "Elevation")
  private Elevation elevation;



}
