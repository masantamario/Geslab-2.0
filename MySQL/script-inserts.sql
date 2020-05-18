INSERT INTO roles (rol) values ('Administrador'), ('Gestor'), ('Usuario');

INSERT INTO usuarios (usuario, contrasena) VALUES ('admin', 'admin');
UPDATE usuarios SET nombre = 'Administrador', mail = 'admin@prueba.es', federada = 'false', activo = 'true', rol = 1, area=1, fecha_creacion = NOW() WHERE idusuario = 1;

INSERT INTO usuarios (usuario, contrasena) VALUES ('mario', 'mario');
UPDATE usuarios SET nombre = 'Mario ', mail = 'mario@prueba.es', federada = 'true', activo = 'false', rol = 3, area=1, fecha_creacion = NOW() WHERE idusuario = 2;

UPDATE centro SET nombre = 'Económicas' WHERE nombre = "Economicas";


INSERT INTO dpto (nombre) values ('Química');
INSERT INTO area (nombre, dpto) values ("Orgánica", 1);
INSERT INTO centro (nombre) values ("Facultad de Ciencias");
INSERT INTO centro (nombre) values ("EPS");

INSERT INTO centro_area (centro, area) values (1, 1);
INSERT INTO centro_area (centro, area) values (2, 1);


DELETE FROM usuarios WHERE idusuario>1;

DELETE FROM centro WHERE codcentro=4;

select * from centro_area;

select * from dpto;

select * from centro;

select * from area;

select * from usuarios;

select * from roles;

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

