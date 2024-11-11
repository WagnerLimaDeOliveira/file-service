package com.insurance.fileservice.entity;

import jakarta.persistence.*;

@Entity
public class ClaimFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "file_name", nullable = false)
    private String filename;
    private long size;
    private String type;
    private long claimId;

    public ClaimFile() {
    }

    public ClaimFile(String filename, long size, String type, long claimId) {
        this.filename = filename;
        this.size = size;
        this.type = type;
        this.claimId = claimId;
    }

    public long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getClaimId() {
        return claimId;
    }

    public void setClaimId(long claimId) {
        this.claimId = claimId;
    }
}
