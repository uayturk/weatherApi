package com.ufuk.weatherApi.beans;

import lombok.Getter;

/**
 * Keeps collection names, don't use {@link Enum#name()} use {@link CollectionNames#toString()} instead.
 */
@Getter
public enum CollectionNames {

  OBJECTS("weatherApiCollection");

  private String collectionName;

  CollectionNames(String collectionName) {
    this.collectionName = collectionName;
  }

  @Override
  public String toString() {
    return collectionName;
  }
}
