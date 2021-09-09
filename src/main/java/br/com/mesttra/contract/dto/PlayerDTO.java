package br.com.mesttra.contract.dto;

import br.com.mesttra.contract.enums.Position;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class PlayerDTO {

    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Double salary;

    private boolean available;

    private String correlationId;

}
