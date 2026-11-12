CREATE TABLE IF NOT EXISTS PLATO (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nombre VARCHAR(255),
        precio INT,
        descripcion VARCHAR(255),
        categoria VARCHAR(255),
        url_Foto VARCHAR(255)
);


INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (1,'Hamburguesa', 10, 'Deliciosa hamburguesa con carne y queso', 'Comida rápida','https://img.icons8.com/fluency/100/hamburger.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (2,'Pizza', 12, 'Sabrosa pizza con variedad de ingredientes', 'Comida italiana','https://img.icons8.com/emoji/100/pizza-emoji.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (3,'Ensalada César', 8, 'Ensalada fresca con pollo y aderezo César', 'Ensaladas','https://img.icons8.com/plasticine/100/salad.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (4,'Sushi', 15, 'Rolls de sushi con pescado fresco y arroz', 'Comida japonesa','https://img.icons8.com/plasticine/100/sushi.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (5,'Pasta Alfredo', 10, 'Pasta con salsa Alfredo y queso parmesano', 'Comida italiana','https://img.icons8.com/plasticine/100/spaghetti.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (6,'Ceviche', 14, 'Plato de ceviche con pescado fresco y limón', 'Comida peruana','https://img.icons8.com/external-icongeek26-flat-icongeek26/100/external-ceviche-fine-dining-icongeek26-flat-icongeek26.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (7,'Tacos', 9, 'Deliciosos tacos mexicanos con carne y salsa', 'Comida mexicana','https://img.icons8.com/external-flaticons-flat-flat-icons/64/external-tacos-world-cuisine-flaticons-flat-flat-icons.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (8,'Sopa de Tomate', 6, 'Sopa caliente de tomate con croutones', 'Sopas','https://img.icons8.com/color/100/soup-plate.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (9,'Pollo a la Parrilla', 12, 'Pechuga de pollo a la parrilla con papas fritas', 'Platos principales','https://img.icons8.com/external-chloe-kerismaker/100/external-Chicken-new-year-chloe-kerismaker.png');
INSERT INTO PLATO (id,nombre, precio, descripcion, categoria,url_Foto) VALUES (10,'Helado de Vainilla', 5, 'Delicioso helado de vainilla con topping de chocolate', 'Postres','https://img.icons8.com/officel/100/ice-cream-bowl.png');