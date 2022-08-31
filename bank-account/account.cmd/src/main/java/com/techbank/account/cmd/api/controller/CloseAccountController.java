package com.techbank.account.cmd.api.controller;

import com.techbank.account.cmd.api.command.CloseAccountCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.exception.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/closeBankAccount")
public class CloseAccountController {

  private final Logger logger = Logger.getLogger(CloseAccountController.class.getName());

  @Autowired
  private CommandDispatcher commandDispatcher;

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id) {

    try {
      commandDispatcher.send(new CloseAccountCommand(id));
      String message = "Bank account closure request successfully completed!";
      return new ResponseEntity<>(new BaseResponse(message), HttpStatus.OK);
    } catch (IllegalStateException | AggregateNotFoundException e) {
      logger.log(Level.WARNING,
          MessageFormat.format("Client made a bad request - {0}", e.toString()));
      return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      var safeErrorMessage =
          MessageFormat.format("Error while processing request to close bank account with id - {0}", id);
      logger.log(Level.SEVERE, safeErrorMessage, e);
      return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
