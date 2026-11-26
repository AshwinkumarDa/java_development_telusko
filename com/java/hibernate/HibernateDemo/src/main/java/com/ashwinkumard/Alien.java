package com.ashwinkumard;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @Entity → Defines the entity name used in Hibernate/JPA queries.
 * @Table → Maps the entity to a physical database table.
 * @Column → Maps a Java field to a database column.
 * hibernate.hbm2ddl.auto=update → Attempts to synchronize schema changes, but isn't a database migration tool.
 * session.find() → Your preferred API for normal entity lookups.
 * session.getReference() → Used when a reference is sufficient and you want to defer loading.
 */

// Need to check difference between @Entity(name = "new_table_name") and @Table(name = "new_table_name")
// Entity - Alien class name
@Entity
//Below will change class name
// @Entity(name = "alien")
// below will create new table, class name it will keep as it is  `Alien`
// @Table(name = "alien")
public class Alien {
    @Id
    // @Column(name = "a_id")
    private int id;

    // @Column(name = "a_name")
    private String name;

    private String tech;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tech='" + tech + '\'' +
                '}';
    }
}
