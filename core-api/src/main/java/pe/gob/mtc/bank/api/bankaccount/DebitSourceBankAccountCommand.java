package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class DebitSourceBankAccountCommand {

    @TargetAggregateIdentifier
    private String bankAccountId;
    private String bankTransferId;
    private long amount;
}
