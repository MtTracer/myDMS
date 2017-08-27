package thirdpower.mydms.document.api;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.io.ByteSource;

public class Document {
  private Long id;
  private final String name;
  private final ByteSource contents;

  public Document(final long id, final String name, final ByteSource contents) {
    this(name, contents);
    this.id = id;
  }

  public Document(final String name, final ByteSource contents) {
    this.name = checkNotNull(name);
    this.contents = checkNotNull(contents);
  }

  @Nullable
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ByteSource getContents() {
    return contents;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Document other = (Document) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("id", id)
      .add("name", name)
      .toString();
  }
}
