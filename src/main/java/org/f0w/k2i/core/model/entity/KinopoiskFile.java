package org.f0w.k2i.core.model.entity;

import javax.persistence.*;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.GenericGenerator;
import java.util.Objects;
import static com.google.common.base.Preconditions.*;

@Entity
@Table(name = "KINOPOISK_FILE")
public class KinopoiskFile {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "HASH_CODE", unique = true, nullable = false)
    private String hashCode;

    public KinopoiskFile() {}

    public KinopoiskFile(String hashCode) {
        setHashCode(hashCode);
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String checksum) {
        this.hashCode = checkNotNull(checksum);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(getHashCode(), ((KinopoiskFile) obj).getHashCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHashCode());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("hashCode", hashCode)
                .toString();
    }
}
