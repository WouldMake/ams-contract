package br.com.mesttra.contract.service;

import br.com.mesttra.contract.amqp.RabbitConfig;
import br.com.mesttra.contract.amqp.message.FinancialMessage;
import br.com.mesttra.contract.amqp.message.MailMessage;
import br.com.mesttra.contract.dto.PlayerDTO;
import br.com.mesttra.contract.entity.Process;
import br.com.mesttra.contract.enums.ExpenseType;
import br.com.mesttra.contract.enums.Status;
import br.com.mesttra.contract.repository.ProcessRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void fromContractToFinancial(PlayerDTO playerDTO) {
        String correlationId = playerDTO.getCorrelationId();
        Process processDb = processRepository.findByCorrelationId(correlationId).get();

        MailMessage mailMessage = new MailMessage("joao@mesttra.com",
                "[Contratação de Jogador]",
                "A contratação do jogador X foi enviada para o financeiro");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_CONTRACT_TO_MAIL, mailMessage);

        FinancialMessage financialRequestMessage = new FinancialMessage(ExpenseType.SALARY, playerDTO.getSalary(), LocalDate.now().plusMonths(1L), correlationId);
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_CONTRACT_TO_FINANCIAL, financialRequestMessage);

        processDb.setPayload(financialRequestMessage.toString());
        processDb.setState(Status.SENT_TO_FINANCIAL);
        processRepository.save(processDb);
    }

    public void endProcess(FinancialMessage financialRequestMessage) {
        String correlationId = financialRequestMessage.getCorrelationId();
        Process processDb = processRepository.findByCorrelationId(correlationId).get();

        MailMessage mailMessage = new MailMessage("joao@mesttra.com",
                "[Contratação de Jogador Finalizada]",
                "A contratação do jogador X foi processada com sucesso");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_CONTRACT_TO_MAIL, mailMessage);

        processDb.setState(Status.DONE);
        processRepository.save(processDb);
    }
}
