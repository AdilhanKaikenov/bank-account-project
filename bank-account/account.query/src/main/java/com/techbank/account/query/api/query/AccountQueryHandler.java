package com.techbank.account.query.api.query;

import com.techbank.account.query.api.dto.EqualityType;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.domain.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryHandler implements QueryHandler {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public List<BaseEntity> handle(FindAllAccountsQuery query) {
    Iterable<BankAccount> bankAccounts = accountRepository.findAll();
    List<BaseEntity> bankAccountEntities = new ArrayList<>();
    bankAccounts.forEach(bankAccountEntities::add);
    return bankAccountEntities;
  }

  @Override
  public List<BaseEntity> handle(FindAccountByIdQuery query) {
    var bankAccount = accountRepository.findById(query.getId());
    if (bankAccount.isEmpty()) {
      return null;
    }
    List<BaseEntity> bankAccountEntities = new ArrayList<>();
    bankAccountEntities.add(bankAccount.get());

    return bankAccountEntities;
  }

  @Override
  public List<BaseEntity> handle(FindAccountByHolderQuery query) {
    var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
    if (bankAccount.isEmpty()) {
      return null;
    }
    List<BaseEntity> bankAccountEntities = new ArrayList<>();
    bankAccountEntities.add(bankAccount.get());

    return bankAccountEntities;
  }

  @Override
  public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
    return query.getEqualityType() == EqualityType.GREATER_THAN
        ? accountRepository.findByBalanceGreaterThan(query.getBalance())
        : accountRepository.findByBalanceLessThan(query.getBalance());
  }
}
