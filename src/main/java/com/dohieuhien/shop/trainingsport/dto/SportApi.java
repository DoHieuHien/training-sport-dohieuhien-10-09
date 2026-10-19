package com.dohieuhien.shop.trainingsport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SportApi(Integer spordId, String sportName) {
}
