package br.com.mesttra.contract.amqp;

import br.com.mesttra.contract.amqp.message.FinancialMessage;
import br.com.mesttra.contract.dto.PlayerDTO;
import br.com.mesttra.contract.service.ContractService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class GenericConsumer {

    ContractService contractService;

    public GenericConsumer(ContractService contractService) {
        this.contractService = contractService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FROM_ROSTER)
    public void consumeRoster(@Payload PlayerDTO playerDTO) {
        this.contractService.fromContractToFinancial(playerDTO);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FROM_FINANCIAL)
    public void consumeFinancial(@Payload FinancialMessage financialMessage) {
        this.contractService.endProcess(financialMessage);
    }
}
