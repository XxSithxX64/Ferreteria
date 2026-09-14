package com.demo.ferreteria.repository;

import com.demo.ferreteria.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca,Long>,
        JpaSpecificationExecutor<Marca>,
        Serializable {
}
