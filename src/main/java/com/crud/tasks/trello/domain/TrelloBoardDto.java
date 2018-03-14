package com.crud.tasks.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonIgnoreProperties(ignoreUnknown = true)

public class TrelloBoardDto {

    @JsonProperty("id")
    String id;

    @JsonProperty("name")
    String name;

    @JsonProperty("lists")
    private List<TrelloListDto> lists;

}
