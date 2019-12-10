package pe.gob.mtc.bank.web;

import lombok.extern.slf4j.Slf4j;
import pe.gob.mtc.bank.api.banktransfer.CreateBankTransferCommand;
import pe.gob.mtc.bank.query.banktransfer.BankTransferEntry;
import pe.gob.mtc.bank.query.banktransfer.BankTransferRepository;
import pe.gob.mtc.bank.web.dto.BankTransferDto;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@Slf4j
public class BankTransferController {

    private final CommandGateway commandGateway;
    private final BankTransferRepository bankTransferRepository;

    public BankTransferController(CommandGateway commandGateway, BankTransferRepository bankTransferRepository) {
        this.commandGateway = commandGateway;
        this.bankTransferRepository = bankTransferRepository;
    }

    @SubscribeMapping("/bank-accounts/{bankAccountId}/bank-transfers")
    public Iterable<BankTransferEntry> bankTransfers(@DestinationVariable String bankAccountId) {
        log.info("Retrieve bank transfers for bank account with id {}", bankAccountId);
        return bankTransferRepository.findBySourceBankAccountIdOrDestinationBankAccountId(bankAccountId, bankAccountId);
    }

    @MessageMapping("/bank-transfers/{id}")
    public BankTransferEntry get(@DestinationVariable String id) {
        log.info("Retrieve bank transfer with id {}", id);
        return bankTransferRepository.findOne(id);
    }

    @MessageMapping("/bank-transfers/create")
    public void create(BankTransferDto bankTransferDto) {
        log.info("Create bank transfer with payload {}", bankTransferDto);

        String bankTransferId = UUID.randomUUID().toString();
        CreateBankTransferCommand command = new CreateBankTransferCommand(bankTransferId,
                                                                          bankTransferDto.getSourceBankAccountId(),
                                                                          bankTransferDto.getDestinationBankAccountId(),
                                                                          bankTransferDto.getAmount());

        commandGateway.send(command);
    }
}
