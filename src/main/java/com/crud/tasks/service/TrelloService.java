package com.crud.tasks.service;


import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.domain.CreatedTrelloCard;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Task: new Trello card";

    public List<TrelloBoardDto> fetchTrelloBoards() {

        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createdTrelloCard(final TrelloCardDto trelloCardDto) {

        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(newCard).ifPresent(card ->

                emailService
                        .send(
                                new Mail(
                                        adminConfig.getMailFrom(),
                                        adminConfig.getAdminMail(),
                                        "",
                                        SUBJECT,
                                        "New card"
                                                + trelloCardDto.getName()
                                                + " has been created on your Trello account")
                        )
        );

        return newCard;
    }
}
