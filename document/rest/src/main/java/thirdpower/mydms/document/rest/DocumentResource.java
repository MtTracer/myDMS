package thirdpower.mydms.document.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("documents")
public class DocumentResource {

  // private final DocumentService service;

  // @Inject
  // DocumentResource(final DocumentService service) {
  // this.service = checkNotNull(service);
  // }

  @GET
  @Path("{id}")
  @Produces(MediaType.TEXT_HTML)
  public String findById(@PathParam("id") final long id) {
    // return service.find(id)
    // .orElseThrow(NotFoundException::new);
    return "hello " + id;
  }

  // public Document createDocument(final Document newDocument) {
  // final Document docToSave = new Document(null, newDocument.getName());
  // try {
  // return service.create(docToSave, ByteSource.wrap("test".getBytes()));
  // } catch (final DocumentServiceException e) {
  // throw new InternalServerErrorException("Could not create document:", e);
  // }
  // }
}
