package br.com.mesttra.contract.entity;

import br.com.mesttra.contract.enums.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status state;

    @Lob
    @NonNull
    private String payload;

    @NonNull
    @Column(unique = true)
    private String correlationId;

}
