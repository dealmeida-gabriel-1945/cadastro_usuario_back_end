package com.crudUser.CadastroDeUsuarios.repository;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT " +
            "new com.crudUser.CadastroDeUsuarios.datashape.domain.Usuario( " +
            "user.id, user.nome, user.dataNascimento )" +
            " FROM Usuario user")
    Page<Usuario> findAllSemFoto(Pageable pageable);
}

