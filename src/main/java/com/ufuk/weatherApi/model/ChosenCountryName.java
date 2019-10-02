package com.ufuk.weatherApi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
@Getter
@Setter
@ToString
@Component //we should use that annotation because we call this model class with @Autowired in controller for dependency injection.So this class can autodetect through @Component annotation.
public class ChosenCountryName {

  private String chosenCountryName;

}
