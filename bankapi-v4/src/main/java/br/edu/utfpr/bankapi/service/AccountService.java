package br.edu.utfpr.bankapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.bankapi.dto.AccountDTO;
import br.edu.utfpr.bankapi.exception.NotFoundException;
import br.edu.utfpr.bankapi.model.Account;
import br.edu.utfpr.bankapi.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getByNumber(long number) {
        return accountRepository.getByNumber(number);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account save(AccountDTO dto) {
        var account = new Account();
        BeanUtils.copyProperties(dto, account);

        account.setBalance(0); // Inicializar a conta com saldo 0.

        System.out.println(account);

        // Salva a conta
        return accountRepository.save(account);
    }

    /**
     * 
     * @param id
     * @param dto
     * @return
     * @throws NotFoundException
     */
    public Account update(long id, AccountDTO dto) throws NotFoundException {
        var res = accountRepository.findById(id);

        if (res.isEmpty())
            throw new NotFoundException();

        var account = res.get();
        account.setName(dto.name());
        account.setNumber(dto.number());
        account.setSpecialLimit(dto.specialLimit());

        System.out.println(account);

        // Salva a conta
        return accountRepository.save(account);
    }

    // Métodos de teste

    @Test
    public void testGetByNumber() {
        // Mock do AccountRepository
        AccountRepository accountRepository = mock(AccountRepository.class);

        // Mock do comportamento do accountRepository.getByNumber()
        long accountNumber = 123456;
        Account mockAccount = new Account();
        mockAccount.setNumber(accountNumber);
        when(accountRepository.getByNumber(accountNumber)).thenReturn(Optional.of(mockAccount));

        // Criar uma instância de AccountService
        AccountService accountService = new AccountService();
        accountService.setAccountRepository(accountRepository);

        // Chamar o método getByNumber
        Optional<Account> retrievedAccount = accountService.getByNumber(accountNumber);

        // Verificar se o comportamento esperado ocorreu
        assertTrue(retrievedAccount.isPresent());
        assertEquals(accountNumber, retrievedAccount.get().getNumber());
    }

    @Test
    public void testGetAll() {
        // Mock do AccountRepository
        AccountRepository accountRepository = mock(AccountRepository.class);

        // Criar uma lista de contas para simular o retorno do accountRepository.findAll()
        List<Account> mockAccounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(mockAccounts);

        // Criar uma instância de AccountService
        AccountService accountService = new AccountService();
        accountService.setAccountRepository(accountRepository);

        // Chamar o método getAll
        List<Account> retrievedAccounts = accountService.getAll();

        // Verificar se o comportamento esperado ocorreu
        assertEquals(mockAccounts.size(), retrievedAccounts.size());
    }

    // Métodos setters para injetar o AccountRepository no teste
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }    
}
