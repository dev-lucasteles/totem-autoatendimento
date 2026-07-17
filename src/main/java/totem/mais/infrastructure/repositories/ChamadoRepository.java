package totem.mais.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import totem.mais.infrastructure.entities.Chamado;


@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    // O Spring Boot cria todos os comandos de banco de dados automaticamente aqui
}