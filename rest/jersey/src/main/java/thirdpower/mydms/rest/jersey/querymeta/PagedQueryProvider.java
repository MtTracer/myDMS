package thirdpower.mydms.rest.jersey.querymeta;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import thirdpower.mydms.util.PagedQuery;
import thirdpower.mydms.util.PagedQuery.SortDirection;

public class PagedQueryProvider implements Provider<PagedQuery> {

  private UriInfo uriInfo;
  private HttpHeaders headers;

  @Inject
  PagedQueryProvider(UriInfo uriInfo, HttpHeaders headers) {
    this.uriInfo = uriInfo;
    this.headers = headers;
  }

  @Override
  public PagedQuery get() {
    PagedQuery pagedQuery = new PagedQuery();
    
    Integer pageSize = parseMetaInt("pageSize");
    if(null!=pageSize) {
    pagedQuery.setPageSize(pageSize);
    }
    
    Integer pageOffset = parseMetaInt("pageOffset");
    if(null!=pageOffset) {
      pagedQuery.setPageOffset(pageOffset);
    }
    
    String sort = parseMetaString("sort");
    if(null!=sort) {
      pagedQuery.setSort(sort);
    }
    
    SortDirection sortDirection = parseMetaEnum("sortDirection", SortDirection.class);
    if(null!=sortDirection) {
      pagedQuery.setSortDirection(sortDirection);
    }

    return pagedQuery;
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
