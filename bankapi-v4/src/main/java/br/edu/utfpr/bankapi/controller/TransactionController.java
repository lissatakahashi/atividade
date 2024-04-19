package br.edu.utfpr.bankapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.bankapi.dto.DepositDTO;
import br.edu.utfpr.bankapi.dto.TransferDTO;
import br.edu.utfpr.bankapi.dto.WithdrawDTO;
import br.edu.utfpr.bankapi.service.TransactionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody @Valid TransferDTO dto) {
        try {
            var res = transactionService.transfer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@RequestBody @Valid DepositDTO dto) {
        try {
            var res = transactionService.deposit(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody @Valid WithdrawDTO dto) {
        try {
            var res = transactionService.withdraw(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

        // Método de teste para transfer
        @PostMapping("/testTransfer")
        public ResponseEntity<Object> testTransfer(@RequestBody @Valid TransferDTO dto) {
            try {
                // Chamar o método de teste da classe de serviço correspondente
                var res = transactionService.testTransfer(dto);
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
        }
    
        // Método de teste para withdraw
        @PostMapping("/testWithdraw")
        public ResponseEntity<Object> testWithdraw(@RequestBody @Valid WithdrawDTO dto) {
            try {
                // Chamar o método de teste da classe de serviço correspondente
                var res = transactionService.testWithdraw(dto);
                return ResponseEntity.status(HttpStatus.OK).body(res);
            } catch (Exception exception) {
                return ResponseEntity.badRequest().body(exception.getMessage());
            }
        }
}
