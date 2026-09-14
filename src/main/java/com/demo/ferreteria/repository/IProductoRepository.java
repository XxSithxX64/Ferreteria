package com.demo.ferreteria.repository;

import com.demo.ferreteria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;


@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long>,
        JpaSpecificationExecutor<Producto>,
        Serializable {

}