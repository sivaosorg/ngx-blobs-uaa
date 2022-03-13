package com.phuocnguyen.app.ngxblobsuaa.entities;

import com.sivaos.Entities.SessionsBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.sivaos.Variables.TablesVariable.SESSIONS;

@Entity
@Table(name = SESSIONS)
public class SessionsEntity extends SessionsBaseEntity {

    public SessionsEntity() {
        super();
    }
}
