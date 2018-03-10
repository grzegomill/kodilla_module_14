package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUser;


    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate
                .getForObject(
                        getTrelloMembersBoardsUrl(),
                        TrelloBoardDto[].class);

        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }


    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {

        return restTemplate
                .postForObject(
                        getTrelloCardsUrl(trelloCardDto),
                        null,
                        CreatedTrelloCard.class);
    }


    private URI getTrelloMembersBoardsUrl() {

        return UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUser + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }

    private URI getTrelloCardsUrl(TrelloCardDto trelloCardsDto) {

        return UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardsDto.getName())
                .queryParam("desc", trelloCardsDto.getDescription())
                .queryParam("pos", trelloCardsDto.getPos())
                .queryParam("idList", trelloCardsDto.getListId())
                .build()
                .encode()
                .toUri();
    }
}