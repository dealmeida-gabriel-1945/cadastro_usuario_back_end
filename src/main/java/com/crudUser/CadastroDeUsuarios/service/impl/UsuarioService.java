package com.crudUser.CadastroDeUsuarios.service.impl;

import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface UsuarioService {
    /**
     * Cadastra um novo usuario
     *
     * @params: UsuarioDTO usuario a ser salvo
     * @return: o novo usuario
     *
     */
    UsuarioDTO cadastrar(UsuarioDTO toSave) throws IOException, SQLException;

    /**
     * Lista usuários paginados
     *
     * @params: Pageable pageable para realizar a paginação
     * @return: a página com os usuários
     *
     */
    Page<UsuarioDTO> listarPagina(Pageable pageable);
}
