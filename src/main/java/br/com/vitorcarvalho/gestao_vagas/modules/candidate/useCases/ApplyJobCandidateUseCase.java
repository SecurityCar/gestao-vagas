package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.gestao_vagas.exceptions.JobNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.exceptions.UserNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase { 
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    ApplyJobCandidateUseCase(CandidateRepository candidateRepository, JobRepository jobRepository){
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }

    public void execute(UUID idCandidate, UUID idJob){
        this.candidateRepository.findById(idCandidate).orElseThrow(
            () -> {new UserNotFoundException();}
        );  

        this.jobRepository.findById(idJob).orElseThrow(
            () -> new JobNotFoundException()
        );
    }
}
