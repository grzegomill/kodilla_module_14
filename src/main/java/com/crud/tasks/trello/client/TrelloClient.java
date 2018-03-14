package com.crud.tasks.trello.client;

import com.crud.tasks.trello.domain.CreatedTrelloCard;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;



    public List<TrelloBoardDto> getTrelloBoards() {

        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUser() + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();

        try {

            TrelloBoardDto[] boardsResponse = restTemplate
                    .getForObject(
                            url,
                            TrelloBoardDto[].class);

            return Arrays
                    .asList(
                            ofNullable(boardsResponse)
                                    .orElse(new TrelloBoardDto[0])
                    );

        } catch (RestClientException e) {

            LOGGER.error(e.getMessage(), e);

            return new ArrayList<>();
        }


    }


    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {

        return restTemplate
                .postForObject(
                        getTrelloCardsUrl(trelloCardDto),
                        null,
                        CreatedTrelloCard.class);
    }


    private URI getTrelloCardsUrl(TrelloCardDto trelloCardsDto) {

        return UriComponentsBuilder
                .fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardsDto.getName())
                .queryParam("desc", trelloCardsDto.getDescription())
                .queryParam("pos", trelloCardsDto.getPos())
                .queryParam("idList", trelloCardsDto.getListId())
                .build()
                .encode()
                .toUri();
    }
}