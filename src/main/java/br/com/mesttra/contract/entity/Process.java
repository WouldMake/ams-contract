package br.com.mesttra.contract.entity;

import br.com.mesttra.contract.enums.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Status state;

    @Lob
    @NonNull
    private String payload;

    @NonNull
    private String correlationId;

}
