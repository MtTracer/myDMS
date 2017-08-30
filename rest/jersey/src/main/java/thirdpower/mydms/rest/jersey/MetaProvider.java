package thirdpower.mydms.rest.jersey;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import thirdpower.mydms.rest.api.Meta;
import thirdpower.mydms.rest.api.Meta.SortDirection;

public class MetaProvider implements Provider<Meta> {


  private UriInfo uriInfo;
  private HttpHeaders headers;


  @Inject
  MetaProvider(UriInfo uriInfo, HttpHeaders headers) {
    this.uriInfo = uriInfo;
    this.headers = headers;
  }


  @Override
  public Meta get() {
    Meta meta = new Meta();
    meta.setPageSize(parseMetaInt("pageSize"));
    meta.setPageOffset(parseMetaInt("pageOffset"));
    meta.setSort(parseMetaString("sort"));
    meta.setSortDirection(parseMetaEnum("sortDirection", SortDirection.class));

    return meta;
  }


  @Nullable
  private Integer parseMetaInt(String name) {
    String value = parseMetaString(name);
    if (null == value) {
      return null;
    }

    try {
      return Integer.parseUnsignedInt(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }


  private String parseMetaString(String name) {
    String value = uriInfo.getQueryParameters()
      .getFirst(name);
    if (null != value) {
      return value;
    }

    return headers.getHeaderString(name);
  }

  private <T extends Enum<T>> T parseMetaEnum(String name, Class<T> enumType) {
    String value = parseMetaString(name);
    if (null == value) {
      return null;
    }

    try {
      return Enum.valueOf(enumType, value);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

}
