package pe.gob.mtc.bank.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.*;

import pe.gob.mtc.bank.api.banktransfer.BankTransferCompletedEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferCreatedEvent;
import pe.gob.mtc.bank.api.banktransfer.BankTransferFailedEvent;
import pe.gob.mtc.bank.api.banktransfer.CreateBankTransferCommand;
import pe.gob.mtc.bank.api.banktransfer.MarkBankTransferCompletedCommand;
import pe.gob.mtc.bank.api.banktransfer.MarkBankTransferFailedCommand;
import pe.gob.mtc.bank.command.BankTransfer;

public class BankTransferTest {

    private FixtureConfiguration<BankTransfer> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(BankTransfer.class);
    }

    @Test
    public void testCreateBankTransfer() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";

        fixture.givenNoPriorActivity()
               .when(new CreateBankTransferCommand(bankTransferId, sourceBankAccountId, destinationBankAccountId, 20))
               .expectEvents(new BankTransferCreatedEvent(bankTransferId,
                                                          sourceBankAccountId,
                                                          destinationBankAccountId,
                                                          20));
    }

    @Test
    public void testMarkBankTransferCompleted() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";

        fixture.given(new BankTransferCreatedEvent(bankTransferId, sourceBankAccountId, destinationBankAccountId, 20))
               .when(new MarkBankTransferCompletedCommand(bankTransferId))
               .expectEvents(new BankTransferCompletedEvent(bankTransferId));
    }

    @Test
    public void testMarkBankTransferFailed() throws Exception {
        String bankTransferId = "bankTransferId";
        String sourceBankAccountId = "sourceBankAccountId";
        String destinationBankAccountId = "destinationBankAccountId";

        fixture.given(new BankTransferCreatedEvent(bankTransferId, sourceBankAccountId, destinationBankAccountId, 20))
               .when(new MarkBankTransferFailedCommand(bankTransferId))
               .expectEvents(new BankTransferFailedEvent(bankTransferId));
    }
}