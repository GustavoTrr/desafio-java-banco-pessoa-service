package com.gustavotorres.cadastropessoa.messages;

import com.gustavotorres.cadastropessoa.dtos.PessoaContaMessageDTO;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CriaContaPessoaMessage{

    @Value("${constants.rabbitmq.exchange}")
    String exchange;
    
    @Value("${constants.rabbitmq.routingkey}")
    String routingkey;
    
    public final RabbitTemplate rabbitTemplate;
    
    @Autowired
    public CriaContaPessoaMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendMessage(PessoaContaMessageDTO pessoaContaMessageDTO) {
        rabbitTemplate.convertAndSend(exchange, routingkey, pessoaContaMessageDTO);
    }
}