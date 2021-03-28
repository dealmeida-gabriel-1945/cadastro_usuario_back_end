package com.crudUser.CadastroDeUsuarios.repository;

import com.crudUser.CadastroDeUsuarios.datashape.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
}

