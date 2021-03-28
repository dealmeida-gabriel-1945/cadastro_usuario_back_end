package com.crudUser.CadastroDeUsuarios.web;

import com.crudUser.CadastroDeUsuarios.datashape.dto.ImpressaoDTO;
import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.service.UsuarioService;
import com.crudUser.CadastroDeUsuarios.util.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.sql.SQLException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService service;

    /**
     * POST: /api/usuario Cadastra um novo usuario
     *
     * @params: UsuarioDTO usuario a ser salvo
     * @return: o novo usuario
     *
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioDTO toSave) throws IOException, SQLException {
        return ResponseEntity.ok(this.service.cadastrar(toSave));
    }

    /**
     * GET: /api/usuario Lista usuários paginados
     *
     * @params: Pageable pageable para realizar a paginação
     * @return: a página com os usuários
     *
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> listarPagina(Pageable pageable) throws IOException {
        return ResponseEntity.ok(this.service.listarPagina(pageable));
    }

    /**
     * GET: /api/usuario/{id}/foto Busca o base64 da foto do usuário especificado
     *
     * @params: id Identificador do usuário
     * @return: String contendo o base64 da foto
     *
     */
    @GetMapping("/{id}/foto")
    public ResponseEntity<String> buscaFoto(@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(this.service.buscaFoto(id));
    }

    /**
     * PUT: /api/usuario/{id} Edita um usuário
     *
     * @params: id Id do usuario a ser editado
     * @params: toEdit dados que serão persistidos
     * @return: o usuario persistido
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editaUsuario(@PathVariable("id") Long id, @RequestBody @Valid UsuarioDTO toEdit) throws IOException, SQLException {
        return ResponseEntity.ok(this.service.editaUsuario(id, toEdit));
    }

    /**
     * DELETE: /api/usuario/{id} Deleta o usuario com o id especificado
     *
     * @params: id Identificador do usuário a ser removido
     * @return: void
     *
     */
    @DeleteMapping("/{id}")
    public void listarPagina(@PathVariable("id") @NotNull(message = "O campo de id é obrigatório!") Long id){
        this.service.deleteUsuario(id);
    }

    /**
     * GET: /api/usuario/imprimir Gera um arquivo PDF contendo a foto, o nome e a data de nascimento de todos os usuários
     *
     * @return: O arquivo PDF com as informações dos usuários
     *
     */
    @GetMapping("/imprimir")
    public ResponseEntity<InputStreamResource> imprimirDocumento() {
        ImpressaoDTO documento = service.imprimirUsuarios();
        return FileUtils.output(documento.getBytesDocumnento(), documento.getNomeArquivo());
    }

}
