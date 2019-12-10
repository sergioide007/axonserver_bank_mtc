package pe.gob.mtc.bank.web.dto;

import lombok.Value;

@Value
public class BankAccountDto {

    private long overdraftLimit;
    
    public BankAccountDto() {
		this.overdraftLimit = 0;
	}
}
