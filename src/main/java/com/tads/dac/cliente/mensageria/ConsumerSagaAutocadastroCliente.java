
package com.tads.dac.cliente.mensageria;

import com.tads.dac.cliente.DTO.ClienteEndDTO;
import com.tads.dac.cliente.DTO.MensagemDTO;
import com.tads.dac.cliente.exception.ClienteConstraintViolation;
import com.tads.dac.cliente.service.ClienteService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;

public class ConsumerSagaAutocadastroCliente {
    
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ClienteService serv;
    

    @RabbitListener(queues = "auto-cliente-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        
        ClienteEndDTO dto = mapper.map(msg.getSendObj(), ClienteEndDTO.class);
        try {
            dto = serv.save(dto);
            msg.setSendObj(dto);
        } catch (ClienteConstraintViolation ex) {
            msg.setMensagem(ex.getMessage());
        }
        
        template.convertAndSend("auto-cliente-saga-receive", msg);
    }

    @RabbitListener(queues = "auto-cliente-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        ClienteEndDTO dto = mapper.map(msg.getSendObj(), ClienteEndDTO.class);
        serv.rollbackAutocadastro(dto.getId());
    }    
}
