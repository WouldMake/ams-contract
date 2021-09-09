package br.com.mesttra.contract.amqp.message;

import br.com.mesttra.contract.enums.Position;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RosterRequestMessage implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("position")
    private Position position;

    @JsonProperty("salary")
    private Double salary;

    @JsonProperty("available")
    private boolean available;

}
