package com.ufuk.weatherApi.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimeZone {

  @SerializedName(value = "Code")
  private String code;

  @SerializedName(value = "Name")
  private String name;

  @SerializedName(value = "GmtOffset")
  private Double gmtOffset;

  @SerializedName(value = "IsDaylightSaving")
  private boolean isDaylightSaving;

  @SerializedName(value = "NextOffsetChange")
  private Object nextOffsetChange;

}
