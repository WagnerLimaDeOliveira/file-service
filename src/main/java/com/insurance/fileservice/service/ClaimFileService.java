package com.insurance.fileservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.fileservice.dto.ClaimFileDTO;
import com.insurance.fileservice.entity.ClaimFile;
import com.insurance.fileservice.kafka.producer.KafkaProducerService;
import com.insurance.fileservice.repository.ClaimFileRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ClaimFileService {
    @Inject
    ClaimFileRepository claimFileRepository;

    @Inject
    KafkaProducerService kafkaProducerService;

    @Inject
    ObjectMapper objectMapper;

    private static final Logger logger = Logger.getLogger(ClaimFileService.class.getName());

    public ClaimFile addClaimFile(ClaimFile claimFile) throws JsonProcessingException {
        try {
            claimFileRepository.save(claimFile);
            ClaimFileDTO claimFileDTO = new ClaimFileDTO(claimFile.getClaimId(), claimFile.getFilename());
            String claimFileDTOJson = objectMapper.writeValueAsString(claimFileDTO);
            kafkaProducerService.sendMessage("add-claim-file-events", String.valueOf(claimFile.getClaimId()), claimFileDTOJson);
            logger.info("New file added: " + claimFile.getFilename());
        } catch (Exception e) {
            logger.severe("Claim file could not be added caused by: " + e.getMessage());
        }

        return claimFile;
    }

    public List<ClaimFile> getClaimFilesByClaimId(long claimId) {
        return claimFileRepository.getClaimFilesByClaimId(claimId);
    }
}
