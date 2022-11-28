package com.moneybin.mortgage.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class BasicIdentity implements Serializable {

    public static final String ATTR_NAME_CREATED = "createdOn";
    public static final String ATTR_UPDATED = "updatedOn";
    public static final String ATTR_ID = "id";

    private static final long serialVersionUID = 8178208943735727212L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdOn;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedOn;

    public BasicIdentity() {super();}

    public BasicIdentity(Long id) {
        super();
        this.id = id;
    }
}
