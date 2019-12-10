package pe.gob.mtc.bank.query.banktransfer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepository extends JpaRepository<BankTransferEntry, String> {

    BankTransferEntry findOneByAxonBankTransferId(String axonBankTransferId);

    Iterable<BankTransferEntry> findBySourceBankAccountIdOrDestinationBankAccountId(String sourceBankAccountId,
                                                                                    String destinationBankAccountId);
}
