package thirdpower.mydms.document.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.common.io.ByteSource;

import thirdpower.mydms.document.api.Document;
import thirdpower.mydms.document.api.DocumentService;
import thirdpower.mydms.document.api.DocumentServiceException;

@Path("documents")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DocumentResource {

  private final DocumentService service;

  @Inject
  DocumentResource(final DocumentService service) {
    this.service = checkNotNull(service);
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public DocumentPojo findById(@PathParam("id") final long id) {
    Document document = service.find(id)
      .orElseThrow(NotFoundException::new);
    
    DocumentPojo documentPojo = toPojo(document);
    return documentPojo;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public DocumentPojo createDocument(final DocumentPojo pojo) {
    pojo.setId(null);
    final Document docToSave = fromPojo(pojo);
    try {
      Document createdDocument = service.create(docToSave, ByteSource.wrap("test".getBytes()));
      pojo.setId(createdDocument.getId());
      return pojo;
    } catch (final DocumentServiceException e) {
      throw new InternalServerErrorException("Could not create document:", e);
    }
  }
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<DocumentPojo> findAll() {
    return service.findAll(null).map(this::toPojo).collect(Collectors.toList());
  }
  
  private DocumentPojo toPojo(Document document) {
    DocumentPojo documentPojo = new DocumentPojo();
    documentPojo.setId(document.getId());
    documentPojo.setName(document.getName());
    return documentPojo;
  }
  
  private Document fromPojo(final DocumentPojo pojo) {
    return new Document(null, pojo.getName());
  }
  

}
