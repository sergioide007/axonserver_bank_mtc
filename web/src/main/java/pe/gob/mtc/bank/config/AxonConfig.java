package pe.gob.mtc.bank.config;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pe.gob.mtc.bank.command.BankAccount;
import pe.gob.mtc.bank.command.BankAccountCommandHandler;
import pe.gob.mtc.bank.command.BankTransferManagementSaga;

@Configuration
public class AxonConfig {

    @Autowired
    private AxonConfiguration axonConfiguration;
    @Autowired
    private EventBus eventBus;

    @Bean
    public BankAccountCommandHandler bankAccountCommandHandler() {
        return new BankAccountCommandHandler(axonConfiguration.repository(BankAccount.class), eventBus);
    }

    @Bean
    public SagaConfiguration bankTransferManagementSagaConfiguration() {
        return SagaConfiguration.trackingSagaManager(BankTransferManagementSaga.class);
    }

    @Autowired
    public void configure(@Qualifier("localSegment") SimpleCommandBus simpleCommandBus) {
        simpleCommandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
    }
}
