package thirdpower.mydms.category.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

import thirdpower.mydms.persistence.h2embedded.H2EmbeddedModule;

public class CategoryEntityTest {

  @Test
  public void testPersist() {
    final Injector injector = Guice.createInjector(new H2EmbeddedModule("CategoryNodeEntityTest"));
    final PersistService persistService = injector.getInstance(PersistService.class);
    persistService.start();

    final CategoryRepository catRepo = injector.getInstance(CategoryRepository.class);

    final CategoryEntity rootCat = new CategoryEntity();
    rootCat.setName("root");

    final CategoryEntity childCat1 = new CategoryEntity();
    childCat1.setName("cat1");
    childCat1.setParent(rootCat);

    final CategoryEntity childCat2 = new CategoryEntity();
    childCat2.setName("cat2");
    childCat2.setParent(rootCat);

    rootCat.getChildren()
      .addAll(ImmutableList.of(childCat1, childCat2));

    final CategoryEntity childCat21 = new CategoryEntity();
    childCat21.setName("cat21");
    childCat21.setParent(childCat2);
    final CategoryEntity childCat22 = new CategoryEntity();
    childCat22.setName("cat22");
    childCat22.setParent(childCat2);

    childCat2.getChildren()
      .addAll(ImmutableList.of(childCat22, childCat21));

    catRepo.persist(rootCat);

    final CategoryEntity cat = catRepo.findById(rootCat.getId());
    assertThat(cat).isNotNull();

    final List<CategoryEntity> roots = catRepo.findRoots();
    assertThat(roots).hasSize(1);

    persistService.stop();
  }
}
