package thirdpower.mydms.filestore.filesystem.pathstrategies.subfolderdistribution;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

public class SubfolderDistributionConfiguration {

	private final int subFolderLength;
	private final HashFunction hashFunction;
	private final BaseEncoding hashEncoding;

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(SubfolderDistributionConfiguration data) {
		return builder().setHashEncoding(data.hashEncoding).setHashFunction(data.hashFunction)
				.setSubFolderLength(data.subFolderLength);
	}

	private SubfolderDistributionConfiguration(Builder builder) {
		this.subFolderLength = builder.subFolderLength;
		this.hashFunction = builder.hashFunction;
		this.hashEncoding = builder.hashEncoding;
	}

	public int getSubFolderLength() {
		return subFolderLength;
	}

	public HashFunction getHashFunction() {
		return hashFunction;
	}

	public BaseEncoding getHashEncoding() {
		return hashEncoding;
	}

	public static final class Builder {

		private int subFolderLength = 4;
		private HashFunction hashFunction = Hashing.murmur3_128();
		private BaseEncoding hashEncoding = BaseEncoding.base64Url().omitPadding();

		private Builder() {

		}

		public Builder setSubFolderLength(int subFolderLength) {
			this.subFolderLength = subFolderLength;
			return this;
		}

		public Builder setHashFunction(HashFunction hashFunction) {
			this.hashFunction = hashFunction;
			return this;
		}

		public Builder setHashEncoding(BaseEncoding hashEncoding) {
			this.hashEncoding = hashEncoding;
			return this;
		}

		public SubfolderDistributionConfiguration build() {
			return new SubfolderDistributionConfiguration(this);
		}
	}

}
