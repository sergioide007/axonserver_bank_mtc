package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class ReturnMoneyOfFailedBankTransferCommand {

    @TargetAggregateIdentifier
    private String bankAccountId;
    private long amount;
}
