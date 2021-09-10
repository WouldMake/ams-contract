package br.com.mesttra.contract.repository;

import br.com.mesttra.contract.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    Optional<Process> findByCorrelationId(String correlationId);

}
