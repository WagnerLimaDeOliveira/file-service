package com.insurance.fileservice.service;

import com.insurance.fileservice.entity.ClaimFile;
import com.insurance.fileservice.testhelper.TestContainerSetupHelper;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class FileServiceTest {

    @Inject
    private FileService fileService;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "com.insurance.fileservice")
                .addClasses(FileService.class)// Include service, repository, and entity packages
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml"); // Enable CDI
    }

    @BeforeClass
    public static void setUp() {
        // Start the PostgreSQL Testcontainer
        TestContainerSetupHelper.startContainer();

        // Set system properties for the persistence unit
        System.setProperty("jakarta.persistence.jdbc.url", TestContainerSetupHelper.getPostgresJdbcUrl());
        System.setProperty("jakarta.persistence.jdbc.user", TestContainerSetupHelper.getPostgresUsername());
        System.setProperty("jakarta.persistence.jdbc.password", TestContainerSetupHelper.getPostgresPassword());
    }

    @AfterClass
    public static void tearDown() {
        // Stop the PostgreSQL Testcontainer
        TestContainerSetupHelper.stopContainer();
    }

    @Test
    public void should_create_greeting() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void addClaimFile_shouldAddFileAndReturnAddedFile() {
        // Ensure fileService is injected
        assertNotNull("fileService should have been injected by CDI", fileService);

        ClaimFile claimFile = new ClaimFile("test-file.jpeg", 2028, "jpeg", 1);
        fileService.addClaimFile(claimFile);

        List<ClaimFile> claimFiles = fileService.getClaimFilesByClaimId(1);
    }
}
