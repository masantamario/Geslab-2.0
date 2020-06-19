
INSERT INTO usuarios (usuario, contrasena) VALUES ('admin', 'Y1ZAbRz18/ZxnLRt0CMMYA==');

INSERT INTO dpto (nombre) values ('Química');
INSERT INTO area (nombre, dpto) values ("Orgánica", 1);
INSERT INTO centro (nombre) values ("Facultad de Ciencias");

UPDATE usuarios SET nombre = 'Administrador', mail = 'admin@prueba.es', federada = 'false', activo = 'true', rol = 1, area=1, fecha_creacion = NOW() WHERE idusuario = 1;

INSERT INTO peligro values ('H200','Explosivo inestable'), ('H201','Explosivo; peligro de explosión en masa'), ('H202','Explosivo; grave peligro de proyección'), ('H203','Explosivo; peligro de incendio, de onda expansiva o de proyección.');		
INSERT INTO prudencia values ('P101','Si se necesita consejo médico, tener a mano el envase o la etiqueta.'), ('P102','Mantener fuera del alcance de los niños. '), ('P103','Leer la etiqueta antes del uso.');		
INSERT INTO pictograma values ('GHS01','Bomba explotando'), ('GHS02','Llama'), ('GHS03','LLama sobre un círculo'), ('GHS04','Bombona de gas'), ('GHS05','Corrosión'), ('GHS06','Calavera y tibias cruzadas'), ('GHS07','Signo de exclamación'), ('GHS08','Peligro para la salud'), ('GHS09','Medio ambiente');		

INSERT INTO prudencia_producto values ('7439-93-2', 'P101');
INSERT INTO peligro_producto values ('7439-93-2', 'H200');
INSERT INTO pictograma_producto values ('7439-93-2', 'GHS01');

INSERT INTO calidad (nombre) VALUES ('Excelente'), ('Buena'), ('Normal'), ('Regular'), ('Pésima');
INSERT INTO ubicacion (nombre, area, centro, oculta) VALUES ('Armario 1', 1, 1, 'False'), ('Armario 2', 1, 1, 'False'), ('Nevera', 1, 1, 'False'), ('Cajón', 1, 1, 'False'), ('Taquilla', 1, 1, 'True');

INSERT INTO proveedor (nombre) VALUES ('DPL Group'), ('Quindesur'), ('Addittex'), ('Mopasa');
INSERT INTO marca (nombre) VALUES ('Merck'), ('Fisher Sci.'), ('SAGU');
INSERT INTO prov_marca (proveedor, marca) VALUES (1, 1), (1, 2), (2, 1), (2, 2), (2, 3), (3, 2), (4, 1), (4, 3);

INSERT INTO producto (cas, formula) VALUES ('7440-59-7', 'He'), ('7439-93-2', 'Li'), ('7440-41-7', 'Be'), ('7440-42-8', 'B');
INSERT INTO nombre_producto (cas, nombre) VALUES ('7440-59-7', 'Helio'), ('7439-93-2', 'Litio'), ('7440-41-7', 'Berilio'), ('7440-42-8', 'Boro');
INSERT INTO nombre_producto (cas, nombre) VALUES ('7440-59-7', 'Helium'), ('7439-93-2', 'Lithium'), ('7440-41-7', 'Beryllium'), ('7440-42-8', 'Boron');

INSERT INTO ficha (producto, ubicacion, marca, proveedor, calidad) VALUES ('7440-59-7', 1, 2, 1, 1), ('7440-59-7', 3, 2, 1, 1), ('7440-59-7', 1, 3, 4, 3);
INSERT INTO ficha (producto, ubicacion, marca, proveedor, calidad) VALUES ('7440-41-7', 3, 2, 1, 2), ('7440-41-7', 5, 2, 1, 2);

INSERT INTO entrada (ficha, fecha, lote, unidades, capacidad, g_ml) VALUES (1, NOW(), '45127-DA', 15, 380.5, 'g');
INSERT INTO entrada (ficha, fecha, lote, unidades, capacidad, g_ml) VALUES (2, NOW(), '215-DB', 10, 120.5, 'ml');

update entrada set g_ml = 'g' where ficha=1;


select marca.nombre as marca, proveedor.nombre as proveedor from prov_marca
inner join proveedor on proveedor.codproveedor = prov_marca.proveedor
inner join marca on marca.codmarca = prov_marca.marca
where proveedor.codproveedor = 2;


select codarea from area where nombre = "Orgánica";

select * from usuarios where usuario = 'admin';

DELETE FROM usuarios WHERE idusuario>2;

DELETE FROM centro WHERE codcentro=4;

select * from prudencia_producto;

DELETE FROM prudencia_producto WHERE cas='7439-93-2' and frase = 'P101';

select * from peligro_producto;


select * from dpto;

select marca.codmarca as Codmarca, marca.nombre as Marca, proveedor.codproveedor as Codprov, proveedor.nombre as Prov from prov_marca 
inner join marca on marca.codmarca = prov_marca.marca
inner join proveedor on proveedor.codproveedor= prov_marca.proveedor;

DELETE FROM prov_marca WHERE proveedor = (select codproveedor from proveedor where nombre = 'Addittex') and marca = 1;

select * from ficha;

select * from usuarios;

select ubicacion.nombre as Ubicacion, area.nombre as Area, ubicacion.oculta as Oculta from ubicacion
inner join area on ubicacion.area = area.codarea
where (area.nombre != 'Orgánica' and ubicacion.oculta = 'true') or (area.nombre = 'Orgánica');

select codficha, capacidad, g_ml, fechacaducidad, lote, residuo, stock, calidad.nombre AS calidad, ubicacion, proveedor.nombre AS proveedor, marca.nombre AS marca, producto 
from ficha inner join calidad on ficha.calidad = calidad.codcalidad
inner join proveedor on ficha.proveedor = proveedor.codproveedor
inner join marca on ficha.marca = marca.codmarca
inner join ubicacion on ficha.ubicacion = ubicacion.codubicacion
where (ubicacion.area != (select codarea from area where nombre = 'Orgánica') and ubicacion.oculta = 'false') or (ubicacion.area = (select codarea from area where nombre = 'Orgánica'));


SELECT codarea FROM area WHERE nombre="Orgánica";

select calidad.nombre AS calidad, ubicacion.nombre AS ubicacion, proveedor.nombre AS proveedor, marca.nombre AS marca, nombre_producto.nombre AS producto from ficha
inner join calidad on "Excelente" = calidad.nombre
inner join ubicacion on "Armario 1" = ubicacion.nombre
inner join proveedor on "DPL Group" = proveedor.nombre
inner join marca on "Fisher Sci." = marca.nombre
inner join nombre_producto on "Helio" = nombre_producto.nombre
;


select calidad.nombre AS calidad, ubicacion.nombre AS ubicacion, proveedor.nombre AS proveedor, marca.nombre AS marca, nombre_producto.nombre AS producto 
from ficha
inner join calidad on ficha.calidad = calidad.codcalidad
inner join ubicacion on ficha.ubicacion = ubicacion.codubicacion
inner join proveedor on ficha.proveedor = proveedor.codproveedor
inner join marca on ficha.marca = marca.codmarca
inner join nombre_producto on ficha.producto = nombre_producto.cas
;

SELECT producto.*, nombre_producto.nombre from producto 
inner join nombre_producto on producto.cas = nombre_producto.cas
where producto.cas='7440-59-7';

SELECT ubicacion.nombre, area.nombre as area, centro.nombre as centro, oculta
from ubicacion 
inner join area on ubicacion.area = area.codarea
inner join centro on ubicacion.centro = centro.codcentro
where codubicacion = 2;


-- Todas las areas con su respectivo nombre departamento y el centro al que pertenecen
SELECT area.codarea, area.nombre AS Area, area.dpto AS coddpto, dpto.nombre AS Departamento, centro.codcentro, centro.nombre AS Centro
FROM area 
INNER JOIN dpto ON area.dpto = dpto.coddpto
INNER JOIN centro_area ON area.codarea = centro_area.area
INNER JOIN centro ON centro_area.centro = centro.codcentro;

-- Todas las areas con su respectivo nombre departamento
SELECT area.codarea, area.nombre AS Area, area.dpto AS coddpto, dpto.nombre AS Departamento
FROM area 
INNER JOIN dpto ON area.dpto = dpto.coddpto;

-- Todas los usuarios con sus respectivos nombre area
SELECT usuarios.idusuario, usuarios.usuario, usuarios.nombre, usuarios.mail, usuarios.rol, area.nombre AS area, usuarios.federada, usuarios.fecha_creacion
FROM usuarios 
INNER JOIN area ON usuarios.area = area.codarea
WHERE usuario = 'admin';

-- Centros que tiene el area X
SELECT centro_area.area AS codarea, centro_area.centro AS codcentro, centro.nombre AS centro 
from centro_area 
INNER JOIN centro ON centro_area.centro = centro.codcentro
where area = 1;


-- Todos los centros con todas las areas que posee
SELECT centro.codcentro, centro.nombre AS Centro, area.codarea, area.nombre AS Area
FROM centro 
INNER JOIN centro_area ON centro.codcentro = centro_area.centro
INNER JOIN area ON centro_area.area = area.codarea;

SELECT centro_area.centro AS codcentro, centro_area.area AS codarea, area.nombre AS area, area.dpto AS coddpto, dpto.nombre AS dpto
from centro_area 
INNER JOIN area ON centro_area.area = area.codarea
INNER JOIN dpto ON area.dpto = dpto.coddpto
where centro = 1;

select codarea from area where nombre='Inorgánica';

