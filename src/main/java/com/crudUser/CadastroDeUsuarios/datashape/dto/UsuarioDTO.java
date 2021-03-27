package com.crudUser.CadastroDeUsuarios.datashape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private Long id;
    @Size(min = 5, max = 30, message = "O campo de nome deve ter de 5 até 100 caracteres.")
    @NotNull(message = "O campo de nome é obrigatório.")
    private String nome;
    @PastOrPresent(message = "O campo de ata de nascimento deve estar no passado.")
    @NotNull(message = "O campo de data de nascimento é obrigatório.")
    private LocalDate dataNascimento;
    private String foto;
}
