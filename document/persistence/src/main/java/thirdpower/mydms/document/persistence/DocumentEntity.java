package thirdpower.mydms.document.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DocumentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private long fileReferenceId;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public long getFileReferenceId() {
    return fileReferenceId;
  }

  public void setFileReferenceId(final long fileReferenceId) {
    this.fileReferenceId = fileReferenceId;
  }


}
