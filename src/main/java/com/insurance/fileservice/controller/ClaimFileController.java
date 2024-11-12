package com.insurance.fileservice.controller;

import com.insurance.fileservice.entity.ClaimFile;
import com.insurance.fileservice.service.ClaimFileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Logger;

@Path("claim")
public class ClaimFileController {
    @Inject
    ClaimFileService claimFileService;

    private static final Logger LOGGER = Logger.getLogger(ClaimFileController.class.getName());

    @GET
    @Path("{claimId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClaimFiles(@PathParam("claimId") long claimId) {
        List<ClaimFile> claimFiles = claimFileService.getClaimFilesByClaimId(claimId);

        if (claimFiles == null || claimFiles.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No files found for claim ID " + claimId)
                    .build();
        }

        return Response.ok(claimFiles).build();
    }

    @POST
    @Path("{claimId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClaimFile(@PathParam("claimId") long claimId, ClaimFile claimFile) {
        try {
            if (claimFile.getClaimId() != claimId) {
                throw new WebApplicationException("Claim ID does not match Claim ID in the body", Response.Status.BAD_REQUEST);
            }
            ClaimFile createdFile = claimFileService.addClaimFile(claimFile);
            return Response.status(Response.Status.CREATED).entity(createdFile).build();
        } catch (Exception e) {
            LOGGER.severe("Error creating claim file: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating claim file").build();
        }
    }
}
