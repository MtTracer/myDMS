package thirdpower.mydms.document.api;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

public final class DocumentFilter {

  private final String name;
  private final NameFilterMode nameFilterMode;

  private DocumentFilter(final Builder builder) {
    this.name = builder.name;
    this.nameFilterMode = builder.nameFilterMode;
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public NameFilterMode getNameFilterMode() {
    return nameFilterMode;
  }

  public static enum NameFilterMode {
    EQUALS, CONTAINS, REGEX
  }

  public static final class Builder {
    private String name = null;
    private NameFilterMode nameFilterMode = NameFilterMode.EQUALS;

    private Builder() {}

    public Builder withNameFilter(final String name, final NameFilterMode mode) {
      this.name = checkNotNull(name);
      this.nameFilterMode = checkNotNull(mode);
      return this;
    }

    public DocumentFilter build() {
      return new DocumentFilter(this);
    }
  }
}
