package thirdpower.mydms.persistence.api;

public class Paging {

  private int pageSize;
  private int pageOffset;

  public Paging(final int pageSize, final int pageOffset) {
    this.pageSize = pageSize;
    this.pageOffset = pageOffset;
  }

  public int getPageOffset() {
    return pageOffset;
  }

  public void setPageOffset(final int pageOffset) {
    this.pageOffset = pageOffset;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

}
