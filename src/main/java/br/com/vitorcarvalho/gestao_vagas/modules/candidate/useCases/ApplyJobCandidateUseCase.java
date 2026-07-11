package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.gestao_vagas.exceptions.JobNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.exceptions.UserNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase { 
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    private final ApplyJobRepository applyJobRepository;

    ApplyJobCandidateUseCase(CandidateRepository candidateRepository, JobRepository jobRepository, ApplyJobRepository applyJobRepository){
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.applyJobRepository = applyJobRepository;
    }

    public ApplyJobEntity execute(UUID candidateId, UUID jobId){
        this.candidateRepository.findById(candidateId).orElseThrow(
            () -> new UserNotFoundException()
        );  

        this.jobRepository.findById(candidateId).orElseThrow(
            () -> new JobNotFoundException()
        );

        var applyJob = ApplyJobEntity.builder().candidateId(candidateId).jobId(jobId).build();
        applyJob = applyJobRepository.save(applyJob);
        return applyJob;
    }
}
