package thirdpower.mydms.rest.api;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MetaWrapper {

  public static  MetaWrapper of(Object data, Meta meta) {
   return new MetaWrapper();
  }
  
  private Meta meta;
  
  private Object result;

  public MetaWrapper() {
    
  }
  
  public MetaWrapper(Object result, Meta meta) {
    checkNotNull(result);
    checkNotNull(meta);
    Class<? extends Object> resultClass = result.getClass();
    checkArgument(resultClass.isArray() || Iterable.class.isAssignableFrom(resultClass), "data must be of type array or iterable but was %s", resultClass);
    
    this.result = result;
    this.meta = meta;
  }

  public Meta getMeta() {
    return meta;
  }

  public Object getResult() {
    return result;
  }
  
}
