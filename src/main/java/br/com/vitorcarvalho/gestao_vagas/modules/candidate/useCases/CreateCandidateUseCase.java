package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.gestao_vagas.exceptions.UserFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers.CandidateEntity;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;
    
    public CandidateEntity execute(CandidateEntity candidateEntity){
         this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        return candidateRepository.save(candidateEntity);
    }

}
