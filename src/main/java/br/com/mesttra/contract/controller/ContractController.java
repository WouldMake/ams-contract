package br.com.mesttra.contract.controller;

import br.com.mesttra.contract.dto.PlayerDTO;
import br.com.mesttra.contract.service.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contracts")
public class ContractController {

    ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public String startContract(@RequestBody PlayerDTO playerDTO) {
        return contractService.startProcess(playerDTO);
    }

}

