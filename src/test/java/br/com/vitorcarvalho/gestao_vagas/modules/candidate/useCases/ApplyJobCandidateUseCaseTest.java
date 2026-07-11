package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vitorcarvalho.gestao_vagas.exceptions.JobNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.exceptions.UserNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.company.entities.CandidateEntity;
import br.com.vitorcarvalho.gestao_vagas.modules.company.entities.JobEntity;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should Throw Exception When Candidate Not Found")
    public void shouldThrowException_whenCandidateNotFound(){
        assertThatThrownBy(() -> applyJobCandidateUseCase.execute(null, null))
        .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Should Throw Exception When Job Not Found")
    public void shouldThrowException_whenJobNotFound(){
        var candidateId = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(candidateId);
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        assertThatThrownBy(() -> applyJobCandidateUseCase.execute(candidateId, null)).isInstanceOf(JobNotFoundException.class);
    }

    @Test
    @DisplayName("Should Apply to Job When Candidate and Job Exist")
    public void shouldApplyJob_whenCandidateAndJobExist(){
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder().id(UUID.randomUUID()).candidateId(candidateId).jobId(jobId).build();

        when(applyJobRepository.save(any(ApplyJobEntity.class))).thenReturn(applyJob);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        var result = applyJobCandidateUseCase.execute(candidateId, jobId);
        assertThat(result).hasFieldOrProperty("id");
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCandidateId()).isEqualTo(candidateId);
        assertThat(result.getJobId()).isEqualTo(jobId);
    }
}
