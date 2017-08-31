package thirdpower.mydms.util;

public class PagedQuery {

  public enum SortDirection {
    ascending, descending;
  }

  private Integer totalSize;
  private Integer pageSize;
  private Integer pageOffset=0;
  private String sort;
  private SortDirection sortDirection = SortDirection.ascending;

  public Integer getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(Integer totalSize) {
    this.totalSize = totalSize;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageOffset() {
    return pageOffset;
  }

  public void setPageOffset(Integer pageOffset) {
    this.pageOffset = pageOffset;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public SortDirection getSortDirection() {
    return sortDirection;
  }

  public void setSortDirection(SortDirection sortDirection) {
    this.sortDirection = sortDirection;
  }

}
