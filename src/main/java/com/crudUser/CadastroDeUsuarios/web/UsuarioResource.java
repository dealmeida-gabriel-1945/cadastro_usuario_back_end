package com.crudUser.CadastroDeUsuarios.web;

import com.crudUser.CadastroDeUsuarios.datashape.dto.UsuarioDTO;
import com.crudUser.CadastroDeUsuarios.service.impl.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
}
