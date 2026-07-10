package br.com.vitorcarvalho.gestao_vagas.modules.candidate;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vitorcarvalho.gestao_vagas.modules.company.entities.CandidateEntity;

import java.util.List;
import java.util.Optional;


public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
    Optional<CandidateEntity> findByUsername(String username);
}
