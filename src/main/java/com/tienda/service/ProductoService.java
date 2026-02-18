package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) 
    {
        if (activo) 
        {
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) 
    {
        return categoriaRepository.findById(idProducto);
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional
    public void save(Producto categoria, MultipartFile imagenFile) 
    {
        categoriaRepository.save(categoria);

        if (!imagenFile.isEmpty()) 
        { // Si no está vacío... pasaron una imagen...
            try 
            {
                String rutaImagen = firebaseStorageService.uploadImage(
                        imagenFile,
                        "categoria",
                        categoria.getIdProducto()
                );

                categoria.setRutaImagen(rutaImagen);
                categoriaRepository.save(categoria);

            } catch (IOException e) 
            {
                
            }
        }
    }

    @Transactional
    public void delete(Integer idProducto) {

        // Verifica si la categoría existe antes de intentar eliminarlo
        if (!categoriaRepository.existsById(idProducto)) 
        {
            // Lanza una excepción para indicar que el usuario no fue encontrado
            throw new IllegalArgumentException(
                    "La categoria con ID " + idProducto + " no existe."
            );
        }

        try 
        {
            categoriaRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) 
        {
            // Lanza una nueva excepción para encapsular el problema de integridad de datos
            throw new IllegalStateException(
                    "No se puede eliminar la categoria. Tiene datos asociados.", e
            );
        }
    }
}