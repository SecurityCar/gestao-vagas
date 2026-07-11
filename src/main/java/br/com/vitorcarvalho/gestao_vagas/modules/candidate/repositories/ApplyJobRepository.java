package br.com.vitorcarvalho.gestao_vagas.modules.candidate.repositories;

import java.util.UUID;

import br.com.vitorcarvalho.gestao_vagas.modules.candidate.entities.ApplyJobEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID>{
    
}
