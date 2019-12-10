package pe.gob.mtc.bank.api.bankaccount;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class WithdrawMoneyCommand {

    @TargetAggregateIdentifier
    private String bankAccountId;
    private long amountOfMoney;
}
