package br.com.mesttra.contract.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GenericConsumer {


    @RabbitListener(queues = RabbitConfig.QUEUE_FROM_ROSTER)
    public void consumeRoster() {
        // passos depois que consumir o roster
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FROM_FINANCIAL)
    public void consumeFinancial() {
        // passos depois que consumir o roster
    }
}
