package br.com.vitorcarvalho.gestao_vagas.modules.candidate;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{
    
}
