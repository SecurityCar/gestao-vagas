package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

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
import br.com.vitorcarvalho.gestao_vagas.modules.company.entities.CandidateEntity;
import br.com.vitorcarvalho.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Should Throw Exception When Candidate Not Found")
    public void shouldThrowException_whenCandidateNotFound(){
        assertThatThrownBy(() -> applyJobCandidateUseCase.execute(null, null))
        .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Should Throw Exception When Job Not Found")
    public void shouldThrowException_whenJobNotFound(){
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        assertThatThrownBy(() -> applyJobCandidateUseCase.execute(idCandidate, null)).isInstanceOf(JobNotFoundException.class);
    }
}
