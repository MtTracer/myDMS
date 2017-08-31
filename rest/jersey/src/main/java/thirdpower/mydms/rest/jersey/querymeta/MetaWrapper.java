package thirdpower.mydms.rest.jersey.querymeta;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import thirdpower.mydms.util.PagedQuery;

public class MetaWrapper {

  public static  MetaWrapper of(Object data, PagedQuery meta) {
   return new MetaWrapper();
  }
  
  private PagedQuery meta;
  
  private Object result;

  public MetaWrapper() {
    
  }
  
  public MetaWrapper(Object result, PagedQuery meta) {
    checkNotNull(result);
    checkNotNull(meta);
    Class<? extends Object> resultClass = result.getClass();
    checkArgument(resultClass.isArray() || Iterable.class.isAssignableFrom(resultClass), "data must be of type array or iterable but was %s", resultClass);
    
    this.result = result;
    this.meta = meta;
  }

  public PagedQuery getMeta() {
    return meta;
  }

  public Object getResult() {
    return result;
  }
  
}
