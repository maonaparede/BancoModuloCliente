
package com.tads.dac.cliente.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutocadastroDTO {
    
    private Long idConta;
    private Long idCliente;
    private String senha;
    private BigDecimal salario;
}
