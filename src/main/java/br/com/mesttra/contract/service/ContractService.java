package br.com.mesttra.contract.service;

import br.com.mesttra.contract.amqp.RabbitConfig;
import br.com.mesttra.contract.dto.PlayerDTO;
import br.com.mesttra.contract.entity.Process;
import br.com.mesttra.contract.enums.Status;
import br.com.mesttra.contract.repository.ProcessRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContractService {

    ProcessRepository processRepository;
    RabbitTemplate rabbitTemplate;

    public ContractService(ProcessRepository processRepository, RabbitTemplate rabbitTemplate) {
        this.processRepository = processRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String startProcess(PlayerDTO playerDTO) {

        String correlationId = UUID.randomUUID().toString();
        playerDTO.setCorrelationId(correlationId);
        rabbitTemplate.convertAndSend(RabbitConfig.ROUTING_KEY_CONTRACT_TO_ROSTER, playerDTO);
        processRepository.save(new Process(Status.SENT_TO_ROSTER, playerDTO.toString(), correlationId));

        return correlationId;
    }
}
