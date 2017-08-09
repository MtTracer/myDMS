package thirdpower.mydms.filestore.filesystem.pathstrategies;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

public class SubfolderDistributionConfiguration {

  private final int subFolderLength = 4;
  private final HashFunction hashFunction = Hashing.murmur3_128();
  private final BaseEncoding hashEncoding = BaseEncoding.base64Url()
    .omitPadding();

  public int getSubFolderLength() {
    return subFolderLength;
  }

  public HashFunction getIdHashFunction() {
    return hashFunction;
  }

  public BaseEncoding getHashEncoding() {
    return hashEncoding;
  }

}
