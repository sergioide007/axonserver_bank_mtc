package pe.gob.mtc.bank.web.dto;

import lombok.Value;

@Value
public class WithdrawalDto {

    private String bankAccountId;
    private long amount;
}
