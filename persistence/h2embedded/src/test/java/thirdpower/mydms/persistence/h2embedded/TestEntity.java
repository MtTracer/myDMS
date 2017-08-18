package thirdpower.mydms.persistence.h2embedded;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.common.base.MoreObjects;

@Entity
public class TestEntity {

  @Id
  @GeneratedValue
  private Long id;

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("id", id)
      .toString();
  }
}
