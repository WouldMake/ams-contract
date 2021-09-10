package br.com.mesttra.contract.amqp.message;

import br.com.mesttra.contract.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FinancialMessage {

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    private Double amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    private String correlationId;

}
