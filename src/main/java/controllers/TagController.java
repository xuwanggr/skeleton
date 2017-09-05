package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final TagDao tags;

    public TagController(TagDao tags) {
        this.tags = tags;
    }

    @PUT
    @Path("tags/{tag}")
    public String toggleTag(@PathParam("tag") String tagName, Integer receiptID) {
        //check whether receiptID exist?

        //how to untag?
        return tags.insert(tagName, receiptID);
    }

    @GET
    @Path("tags/{tag}")
    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> tagRecords = tags.getAllReceipts(tagName);
        return tagRecords.stream().map(ReceiptResponse::new).collect(toList());
    }

}
