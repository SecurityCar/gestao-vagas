package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.gestao_vagas.exceptions.UserFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.controllers.CandidateEntity;

@Service
public class CreateCandidateUseCase {
    
    private final CandidateRepository candidateRepository;

    private final PasswordEncoder passwordEncoder;
    
    CreateCandidateUseCase(PasswordEncoder passwordEncoder, CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public CandidateEntity execute(CandidateEntity candidateEntity){
         this.candidateRepository
            .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new UserFoundException();
            });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);
        
        return candidateRepository.save(candidateEntity);
    }

}
