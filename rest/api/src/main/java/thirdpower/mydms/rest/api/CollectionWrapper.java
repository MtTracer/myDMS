package thirdpower.mydms.rest.api;

import java.util.Collection;

public class CollectionWrapper<T> {

  public static <T> CollectionWrapper<T> of(Collection<T> data, Meta meta) {
    CollectionWrapper<T> cw = new CollectionWrapper<>();
    cw.setMeta(meta);
    cw.setData(data);
    return cw;
  }
  
  private Meta meta;
  
  private Collection<T> data;

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  public Collection<T> getData() {
    return data;
  }

  public void setData(Collection<T> data) {
    this.data = data;
  }
  
  
}
