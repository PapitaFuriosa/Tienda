

package com.tienda.domain;

@Data
@Entiry
@Table(name ="categoria")

public class Categoria implements Serializable {
    

  //Se recomienda añadir un serialVersionUID
    
    private static final long serialVersionUID = IL;
   
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_categoria")
private Integer idCategoria;

@Column(unique = true, nullable = false, length = 50)
@NotNull
@Size(max = 50)
private String descripcion;

@Column(length = 1024)
@Size(max = 1024)
private String rutaImagen;

@Column(name = "activo")
private Boolean activo;

}
