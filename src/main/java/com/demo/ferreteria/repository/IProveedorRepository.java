package com.demo.ferreteria.repository;

import com.demo.ferreteria.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long>,
        JpaSpecificationExecutor<Proveedor>,
        Serializable {
}
