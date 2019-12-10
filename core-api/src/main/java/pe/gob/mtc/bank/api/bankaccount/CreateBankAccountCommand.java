package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.Min;

@Value
public class CreateBankAccountCommand {

    @TargetAggregateIdentifier
    private String bankAccountId;
    @Min(value = 0, message = "Overdraft limit must not be less than zero")
    private long overdraftLimit;
}
