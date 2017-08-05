package thirdpower.mydms.filestore.api;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSource;

public class FileReference {
  private final Long id;
  private final String name;
  private final ByteSource contents;
  private final Map<String, String> meta;

  public static final Builder builder() {
    return new Builder();
  }

  public static final Builder builderFrom(final FileReference data) {
    return builder().setId(data.id)
      .setName(data.name)
      .setContents(data.contents)
      .setMeta(data.meta);
  }

  public FileReference(final Builder builder) {
    id = builder.id;
    name = builder.name;
    contents = builder.contents;
    meta = builder.meta;
  }

  public static final class Builder {
    private Long id;
    private String name;
    private ByteSource contents;
    private Map<String, String> meta = ImmutableMap.of();

    public Builder setId(final long id) {
      this.id = id;
      return this;
    }

    public Builder setName(final String name) {
      checkNotNull(name);
      checkArgument(!name.isEmpty(), "name must not be empty");
      this.name = name;
      return this;
    }

    public Builder setContents(final ByteSource contents) {
      this.contents = checkNotNull(contents);
      return this;
    }

    public Builder setMeta(final Map<String, String> meta) {
      this.meta = ImmutableMap.copyOf(checkNotNull(meta));
      return this;
    }

    public FileReference build() {
      checkState(!Strings.isNullOrEmpty(name), "name must be set before calling build()");
      checkState(null != contents);
      return new FileReference(this);
    }

  }
}
