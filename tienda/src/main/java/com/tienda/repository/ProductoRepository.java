package com.tienda.repository;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);

    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup")
    public List<Producto> consultaJPQL(@Param("precioInf") double precioInf,
            @Param("precioSup") double precioSup);

    @Query(value = "SELECT * FROM producto WHERE precio BETWEEN :precioInf AND :precioSup", nativeQuery = true)
    public List<Producto> consultaSQL(@Param("precioInf") double precioInf,
            @Param("precioSup") double precioSup);

    @Query("SELECT p FROM Producto p JOIN p.categoria c WHERE lower(p.descripcion) LIKE lower(concat('%', :texto, '%'))")
    public List<Producto> buscarPorDescripcion(@Param("texto") String texto);
}
