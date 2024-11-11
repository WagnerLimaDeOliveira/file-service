package com.insurance.fileservice.repository;

import com.insurance.fileservice.entity.ClaimFile;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.util.List;

@ApplicationScoped
public class FileRepository {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    public ClaimFile save(ClaimFile claimFile) {
        try {
            transaction.begin();
            entityManager.persist(claimFile);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return claimFile;
    }

    public List<ClaimFile> getClaimFilesByClaimId(long claimId) {
        System.out.println("Executing query for claimId: " + claimId);
        TypedQuery<ClaimFile> query = entityManager.createQuery(
                "SELECT cf FROM ClaimFile cf WHERE cf.claimId = :claimId", ClaimFile.class
        );
        query.setParameter("claimId", claimId);
        List<ClaimFile> result = query.getResultList();
        System.out.println("Query result size: " + (result != null ? result.size() : "null"));
        return result;
    }

    public ClaimFile findById(Long id) {
        return entityManager.find(ClaimFile.class, id);
    }

    @PreDestroy
    public void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
