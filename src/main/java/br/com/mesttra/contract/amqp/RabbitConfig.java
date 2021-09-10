package br.com.mesttra.contract.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static String EXCHANGE_NAME = "contract-process-exchange";
    public static final String ROUTING_KEY_CONTRACT_TO_ROSTER = "contract-to-roster";
    public static final String ROUTING_KEY_CONTRACT_TO_FINANCIAL = "contract-to-financial";
    public static final String ROUTING_KEY_CONTRACT_TO_MAIL = "contract-to-mail";
    public static final String ROUTING_KEY_ROSTER_TO_CONTRACT = "roster-to-contract";
    public static final String ROUTING_KEY_FINANCIAL_TO_CONTRACT = "financial-to-contract";
    public static final String QUEUE_TO_ROSTER = "contract-to-roster-queue";
    public static final String QUEUE_TO_FINANCIAL = "contract-to-financial-queue";
    public static final String QUEUE_FROM_ROSTER = "roster-to-contract-queue";
    public static final String QUEUE_FROM_FINANCIAL = "financial-to-contract-queue";
    public static final String QUEUE_MAIL = "contract-to-mail-queue";

    @Bean
    public Exchange declareExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Queue declareQueueMail() {
        return QueueBuilder.durable(QUEUE_MAIL)
                .build();
    }

    @Bean
    public Queue declareQueueFinancial() {
        return QueueBuilder.durable(QUEUE_TO_FINANCIAL)
                .build();
    }

    @Bean
    public Queue declareQueueRoster() {
        return QueueBuilder.durable(QUEUE_TO_ROSTER)
                .build();
    }

    @Bean
    public Queue declareQueueFromFinancial() {
        return QueueBuilder.durable(QUEUE_FROM_FINANCIAL)
                .build();
    }

    @Bean
    public Queue declareQueueFromRoster() {
        return QueueBuilder.durable(QUEUE_FROM_ROSTER)
                .build();
    }


    @Bean
    public Binding declareBindingContractToRoster(Exchange exchange) {
        return BindingBuilder.bind(declareQueueRoster())
                .to(exchange)
                .with(ROUTING_KEY_CONTRACT_TO_ROSTER)
                .noargs();
    }

    @Bean
    public Binding declareBindingContractToFinacial(Exchange exchange) {
        return BindingBuilder.bind(declareQueueFinancial())
                .to(exchange)
                .with(ROUTING_KEY_CONTRACT_TO_FINANCIAL)
                .noargs();
    }

    @Bean
    public Binding declareBindingContractToMail(Exchange exchange) {
        return BindingBuilder.bind(declareQueueMail())
                .to(exchange)
                .with(ROUTING_KEY_CONTRACT_TO_MAIL)
                .noargs();
    }

    @Bean
    public Binding declareBindingRosterToContract(Exchange exchange) {
        return BindingBuilder.bind(declareQueueFromRoster())
                .to(exchange)
                .with(ROUTING_KEY_ROSTER_TO_CONTRACT)
                .noargs();
    }

    @Bean
    public Binding declareBindingFinancialToContract(Exchange exchange) {
        return BindingBuilder.bind(declareQueueFromFinancial())
                .to(exchange)
                .with(ROUTING_KEY_FINANCIAL_TO_CONTRACT)
                .noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(RabbitConfig.EXCHANGE_NAME);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }


}
