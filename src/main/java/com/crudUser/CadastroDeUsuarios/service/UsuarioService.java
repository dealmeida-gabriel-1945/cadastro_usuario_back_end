package com.crudUser.CadastroDeUsuarios.service;

import com.crudUser.CadastroDeUsuarios.datashape.dto.ImpressaoDTO;
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

    /**
     * Deleta o usuario com o id especificado
     *
     * @params: id Identificador do usuário a ser removido
     * @return: void
     *
     */
    void deleteUsuario(Long id);

    /**
     * PUT: /api/usuario/{id} Edita um usuário
     *
     * @params: id Id do usuario a ser editado
     * @params: toEdit dados que serão persistidos
     * @return: o usuario persistido
     *
     */
    UsuarioDTO editaUsuario(Long id, UsuarioDTO toEdit) throws SQLException;

    /**
     * GET: /api/usuario/{id}/foto Busca o base64 da foto do usuário especificado
     *
     * @params: id Identificador do usuário
     * @return: String contendo o base64 da foto
     *
     */
    String buscaFoto(Long id);

    /**
     * Gera um arquivo PDF contendo a foto, o nome e a data de nascimento de todos os usuários
     *
     * @return: O arquivo PDF com as informações dos usuários
     *
     */
    ImpressaoDTO imprimirUsuarios();
}
