
package com.tads.dac.cliente.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejeitaClienteDTO {
    private Long idCliente;
    private Long idConta;
    private String email;
}
