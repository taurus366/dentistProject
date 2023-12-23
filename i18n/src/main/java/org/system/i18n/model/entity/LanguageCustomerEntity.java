package org.system.i18n.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "language_customer")
public class LanguageCustomerEntity extends BaseEntity{

    private String session;

    private String locale;

    private Instant created;

    private Instant modified;

}
