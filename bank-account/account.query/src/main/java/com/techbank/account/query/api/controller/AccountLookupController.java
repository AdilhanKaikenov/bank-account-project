package com.techbank.account.query.api.controller;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.api.query.FindAccountByHolderQuery;
import com.techbank.account.query.api.query.FindAccountByIdQuery;
import com.techbank.account.query.api.query.FindAccountsWithBalanceQuery;
import com.techbank.account.query.api.query.FindAllAccountsQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

  private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

  @Autowired
  private QueryDispatcher queryDispatcher;

  @GetMapping(path = "/")
  public ResponseEntity<AccountLookupResponse> getAllAccounts() {
    try {
      List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountsQuery());
      if (accounts == null || accounts.size() == 0) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }

      var response = AccountLookupResponse.builder()
          .accounts(accounts)
          .message(MessageFormat.format("Successfully returned {0} bank account(s)", accounts.size()))
          .build();
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to handle get all accounts request";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      AccountLookupResponse response = new AccountLookupResponse(safeErrorMessage);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/byId/{id}")
  public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value = "id") String id) {
    try {
      List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
      if (accounts == null || accounts.size() == 0) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }

      var response = AccountLookupResponse.builder()
          .accounts(accounts)
          .message("Successfully returned bank account!")
          .build();
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to handle get account by ID request";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      AccountLookupResponse response = new AccountLookupResponse(safeErrorMessage);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/byHolder/{accountHolder}")
  public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder) {
    try {
      List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
      if (accounts == null || accounts.size() == 0) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }

      var response = AccountLookupResponse.builder()
          .accounts(accounts)
          .message("Successfully returned bank account!")
          .build();
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to handle get account by holder request";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      AccountLookupResponse response = new AccountLookupResponse(safeErrorMessage);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(path = "/withBalance/{equalityType}/{balance}")
  public ResponseEntity<AccountLookupResponse> getAccountByHolder(
      @PathVariable(value = "equalityType") EqualityType equalityType,
      @PathVariable(value = "balance") double balance) {
    try {
      List<BankAccount> accounts = queryDispatcher.send(new FindAccountsWithBalanceQuery(equalityType, balance));
      if (accounts == null || accounts.size() == 0) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }

      String messageFormat = "Successfully returned {0} bank account(s)";
      var response = AccountLookupResponse.builder()
          .accounts(accounts)
          .message(MessageFormat.format(messageFormat, accounts.size()))
          .build();
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to handle get accounts with balance request";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      AccountLookupResponse response = new AccountLookupResponse(safeErrorMessage);
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
