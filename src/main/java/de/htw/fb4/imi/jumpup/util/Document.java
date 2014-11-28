package de.htw.fb4.imi.jumpup.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import de.htw.fb4.imi.jumpup.entities.AbstractEntity;



/**
 * This class models document entities based on database BLOBs. Note that these documents do not
 * track their inverse relationships (see {@LinkPlain Person#avatar}), as these should
 * be of little general interest, and might become referenced by many kinds of counterparts as the
 * application grows. Also note that some special considerations must be taken into account when
 * dealing with any kind of LOB column in an object-relational model:
 * <ul>
 * <li>JPA mapping of BOB fields to {@linkplain java.sql.Blob} is not supported by most persistence
 * managers, Hibernate being an exception. The reason is that {@code Blob} instances must remain
 * connected to a database in order to access their content, which prevents returning database
 * connections to connection pools much longer than usual, and creates the general issue of how to
 * disconnect {@code Blob} resources automatically. {@code Blob} content becomes inaccessible once a
 * {@code Blob} is disconnected, which creates issues related to the entity manager's merge
 * operation. And on top of this, there is no second level caching when {@code Blob} mapping is
 * involved. Therefore, even with Hibernate {@code Blob} mapping support is shaky at best, and never
 * recommended.</li>
 * <li>The primary alternative is mapping the database BLOBs to byte arrays, which is ok if the
 * content is guaranteed to be small enough to fit into memory without generating excessive memory
 * demands. With modern servers this should always be the case with content up to around 64KB
 * length, as this is about the size usually used for stream I/O buffers and IP packets anyways.
 * Larger content is also bearable if it isn't constantly accessed, maybe up to a few MB in size; a
 * 16MB BLOB column would still fit around 255 times into 4GB of memory. Mapping to byte arrays also
 * has the advantage that second level caching of these BLOBs can be highly effective, which
 * counters the general disadvantage that all BLOB content is transferred through JDBC connections.</li>
 * <li>If byte arrays cannot be used because the content size is too large and/or access occurs too
 * often, the second alternative is to not map the BLOB field at all in JPA, but to use separate SQL
 * queries to query or update the BLOB fields, passing binary streams as parameters. This comes with
 * the general disadvantage that second level caching is of no use regarding {@Code Blob}
 * objects, so the general disadvantage that all content is transferred through JDBC connections
 * remains unmitigated.</li>
 * <li>In case of MySQL the client side {@linkplain java.sql.Blob} implementation is "brain-dead"
 * (original JavaDoc by creator Mark Matthews), because the database does not implement the required
 * server side streaming I/O feature, in turn forcing the JDBC driver to transport the full BLOB
 * content before any read access can be granted. Therefore, with MySQL the primary alternative
 * "byte array mapping" is always the better choice, and realizing video libraries based on MySQL
 * BLOBs is never a good idea.</li>
 * </ul>
 */
@Entity
@XmlRootElement
public class Document extends AbstractEntity {
	static private final byte[] DEFAULT_CONTENT = new byte[0];
	static private final byte[] DEFAULT_CONTENT_HASH = { -29, -80, -60, 66, -104, -4, 28, 20, -102, -5, -12, -56, -103, 111, -71, 36, 39, -82, 65, -28, 100, -101, -109, 76, -92, -107, -103, 27, 120, 82, -72, 85 };
	static private final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

	@XmlAttribute
	@Column(nullable = false, updatable = true, length = 64)
	private volatile String contentType;

	@XmlAttribute
	@Column(nullable = false, updatable = true, length = 32, unique = true)
	private volatile byte[] contentHash;

	@XmlTransient
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(nullable = false, updatable = true, length = 16777215)
	private volatile byte[] content;


	/**
	 * Creates a new instance.
	 */
	public Document () {
		super();

		this.contentType = DEFAULT_CONTENT_TYPE;
		this.contentHash = DEFAULT_CONTENT_HASH;
		this.content = DEFAULT_CONTENT;
	}


	/**
	 * Returns the content type.
	 * @return the content type
	 */
	public String getContentType () {
		return this.contentType;
	}


	/**
	 * Sets the content type.
	 * @param contentType the content type
	 * @throws NullPointerException if the given content type is {@code null}
	 */
	public void setContentType (final String contentType) {
		if (contentType == null) throw new NullPointerException();

		this.contentType = contentType;
	}


	/**
	 * Returns the content hash.
	 * @return the content' SHA-256 hash
	 */
	public byte[] getContentHash () {
		return this.contentHash;
	}


	/**
	 * Returns the content.
	 * @return the content
	 */
	public byte[] getContent () {
		return this.content;
	}


	/**
	 * Sets the content, and recalculates the associated content hash.
	 * @param content the content
	 * @throws NullPointerException if the given content is {@code null}
	 */
	public void setContent (final byte[] content) {
		try {
			this.contentHash = MessageDigest.getInstance("SHA-256").digest(content);
			this.content = content;
		} catch (final NoSuchAlgorithmException exception) {
			throw new AssertionError();
		}
	}
}