package thirdpower.mydms.persistence.api;

public class Paging {

  private int pageSize;
  private int pageOffset;

  public Paging(int pageSize, int pageOffset) {
    this.pageSize = pageSize;
    this.pageOffset = pageOffset;
  }

  public int getPageOffset() {
    return pageOffset;
  }

  public void setPageOffset(int pageOffset) {
    this.pageOffset = pageOffset;
  }

}
