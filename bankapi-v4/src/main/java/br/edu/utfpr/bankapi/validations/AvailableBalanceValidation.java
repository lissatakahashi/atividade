package br.edu.utfpr.bankapi.validations;

import org.springframework.stereotype.Component;

import br.edu.utfpr.bankapi.exception.WithoutBalanceException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.model.Transaction;

/**
 * Validar se existe saldo em conta disponível
 */
@Component
public class AvailableBalanceValidation {

    public void validate(Transaction transaction) {
        // Verifica se a conta de origem possui saldo
        if (transaction.getSourceAccount().getBalanceWithLimit() < transaction.getAmount()) {
            throw new WithoutBalanceException();
        }
    }
    
    // Métodos de teste

    public void testValidateWithEnoughBalance() {
        Account sourceAccount = new Account();
        sourceAccount.setBalance(1000.0);
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setAmount(500.0);

        validate(transaction); // Não deve lançar exceção
    }

    public void testValidateWithoutBalance() {
        Account sourceAccount = new Account();
        sourceAccount.setBalance(500.0);
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setAmount(1000.0);

        validate(transaction); // Deve lançar WithoutBalanceException
    }

    public void testValidateWithEnoughBalanceIncludingSpecialLimit() {
        Account sourceAccount = new Account();
        sourceAccount.setBalance(500.0);
        sourceAccount.setSpecialLimit(1000.0);
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setAmount(1000.0);

        validate(transaction); // Não deve lançar exceção
    }
}
