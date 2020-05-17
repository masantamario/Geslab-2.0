INSERT INTO roles (rol) values ('Administrador'), ('Gestor'), ('Usuario');

INSERT INTO usuarios (usuario, contrasena) VALUES ('admin', 'admin');
UPDATE usuarios SET nombre = 'Administrador', mail = 'admin@prueba.es', federada = 'false', activo = 'true', rol = 1, fecha_creacion = NOW() WHERE idusuario = 1;

INSERT INTO usuarios (usuario, contrasena) VALUES ('mario', 'mario');
UPDATE usuarios SET nombre = 'Mario ', mail = 'mario@prueba.es', federada = 'true', activo = 'false', rol = 3, fecha_creacion = NOW() WHERE idusuario = 2;

INSERT INTO dpto (nombre) values ('Química');
INSERT INTO area (nombre, dpto) values ("Orgánica", 1);
INSERT INTO centro (nombre) values ("Facultad de Ciencias");
INSERT INTO centro (nombre) values ("EPS");

INSERT INTO centro_area (centro, area) values (1, 1);
INSERT INTO centro_area (centro, area) values (2, 1);


DELETE FROM usuarios WHERE idusuario>1;


select * from centro_area;

select * from centro;

select * from usuarios;

select * from roles;

SELECT area.codarea, area.nombre AS Area, area.dpto AS coddpto, dpto.nombre AS Departamento, centro.codcentro, centro.nombre AS Centro
FROM area 
INNER JOIN dpto ON area.dpto = dpto.coddpto
INNER JOIN centro_area ON area.codarea = centro_area.area
INNER JOIN centro ON centro_area.centro = centro.codcentro;


