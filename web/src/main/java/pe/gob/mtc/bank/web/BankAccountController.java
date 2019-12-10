package pe.gob.mtc.bank.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import pe.gob.mtc.bank.api.bankaccount.CreateBankAccountCommand;
import pe.gob.mtc.bank.api.bankaccount.DepositMoneyCommand;
import pe.gob.mtc.bank.api.bankaccount.WithdrawMoneyCommand;
import pe.gob.mtc.bank.query.bankaccount.BankAccountEntry;
import pe.gob.mtc.bank.query.bankaccount.BankAccountRepository;
import pe.gob.mtc.bank.web.dto.BankAccountDto;
import pe.gob.mtc.bank.web.dto.DepositDto;
import pe.gob.mtc.bank.web.dto.WithdrawalDto;

import java.util.UUID;

@Controller
@MessageMapping("/bank-accounts")
public class BankAccountController {

    private final CommandGateway commandGateway;
    private final BankAccountRepository bankAccountRepository;

    public BankAccountController(CommandGateway commandGateway, BankAccountRepository bankAccountRepository) {
        this.commandGateway = commandGateway;
        this.bankAccountRepository = bankAccountRepository;
    }

    @SubscribeMapping
    public Iterable<BankAccountEntry> all() {
        return bankAccountRepository.findAllByOrderByIdAsc();
    }

    @SubscribeMapping("/{id}")
    public BankAccountEntry get(@DestinationVariable String id) {
        return bankAccountRepository.findOne(id);
    }

    @MessageMapping("/create")
    public void create(BankAccountDto bankAccountDto) {
        String id = UUID.randomUUID().toString();
        CreateBankAccountCommand command = new CreateBankAccountCommand(id, bankAccountDto.getOverdraftLimit());
        commandGateway.send(command);
    }

    @MessageMapping("/withdraw")
    public void withdraw(WithdrawalDto depositDto) {
        WithdrawMoneyCommand command = new WithdrawMoneyCommand(depositDto.getBankAccountId(), depositDto.getAmount());
        commandGateway.send(command);
    }

    @MessageMapping("/deposit")
    public void deposit(DepositDto depositDto) {
        DepositMoneyCommand command = new DepositMoneyCommand(depositDto.getBankAccountId(), depositDto.getAmount());
        commandGateway.send(command);
    }

}
