package pe.gob.mtc.bank.api.bankaccount;

public class MoneyOfFailedBankTransferReturnedEvent extends MoneyAddedEvent {

    public MoneyOfFailedBankTransferReturnedEvent(String bankAccountId, long amountOfMoneyDeposited) {
        super(bankAccountId, amountOfMoneyDeposited);
    }
}
