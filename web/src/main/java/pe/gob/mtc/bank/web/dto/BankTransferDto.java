package pe.gob.mtc.bank.web.dto;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class BankTransferDto {

    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amount;
}
