INSERT INTO roles (rol) values ('Administrador'), ('Gestor'), ('Usuario');

INSERT INTO usuarios (usuario, contrasena) VALUES ('admin', 'admin');

UPDATE usuarios SET nombre = 'Administrador', mail = 'admin@prueba.es', federada = 'n', rol = 1, fecha_creacion = NOW() WHERE idusuario = 1;


INSERT INTO dpto (nombre) values ('Química');
INSERT INTO area (nombre, dpto) values ("Orgánica", 1);
INSERT INTO centro (nombre) values ("Facultad de Ciencias");
INSERT INTO centro_area (centro, area) values (1, 1);


select * from centro_area;

select * from usuarios;

select * from roles;

INSERT INTO usuarios (usuario, contrasena) VALUES ('mario', 'mario');
