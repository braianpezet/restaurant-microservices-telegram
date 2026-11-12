CREATE TABLE IF NOT EXISTS RESTAURANTE (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255),
    direccion VARCHAR(255),
    presentacion VARCHAR(255),
    telefono VARCHAR(255)
    );

INSERT INTO restaurante (id,nombre, direccion, presentacion, telefono) VALUES (1,'ACME', '123 Main Street', '¡Bienvenidos al restaurante ACME!', '555-1234');
