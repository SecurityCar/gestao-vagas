package br.com.vitorcarvalho.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.vitorcarvalho.gestao_vagas.exceptions.UserNotFoundException;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.vitorcarvalho.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
    private final CandidateRepository candidateRepository;

    ProfileCandidateUseCase(CandidateRepository candidateRepository){
        this.candidateRepository = candidateRepository;
    }

    public ProfileCandidateResponseDTO execute(UUID idCandidate){
        var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(()-> {
            throw new UserNotFoundException();
        });

        var candidateDTO = ProfileCandidateResponseDTO.builder()
        .description(candidate.getDescription())
        .name(candidate.getName())
        .email(candidate.getEmail())
        .username(candidate.getUsername())
        .id(candidate.getId())
        .build();

        return candidateDTO;
    }
}
