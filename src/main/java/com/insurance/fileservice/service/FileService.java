package com.insurance.fileservice.service;

import com.insurance.fileservice.entity.ClaimFile;
import com.insurance.fileservice.repository.FileRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FileService {
    @Inject
    FileRepository fileRepository;


    public ClaimFile addClaimFile(ClaimFile claimFile) {
        fileRepository.save(claimFile);
        return claimFile;
    }

    public List<ClaimFile> getClaimFilesByClaimId(long claimId) {
        return fileRepository.getClaimFilesByClaimId(claimId);
    }
}
