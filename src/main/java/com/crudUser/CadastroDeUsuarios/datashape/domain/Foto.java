package com.crudUser.CadastroDeUsuarios.datashape.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="foto")
@Getter
@Setter
@NoArgsConstructor
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "arquivo", columnDefinition = "LONGBLOB")
    private byte[] arquivo;

    public Foto(byte[] arquivo) {
        this.arquivo = arquivo;
    }
}
