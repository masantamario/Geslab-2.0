DROP TABLE IF EXISTS 
	usuarios, 
	salida, 
	entrada, 
	ficha, 
	prov_marca, 
	marca, 
	proveedor, 
	pictograma_producto, 
	pictograma, 
	prudencia_producto, 
	prudencia, 
	peligro_producto, 
	peligro, 
	nombre_producto, 
	producto, 
	calidad, 
	ubicacion, 
	centro, 
	area,  
	dpto;
   
   
CREATE TABLE dpto(
	coddpto int auto_increment,
	nombre char(40),
    constraint PK_dpto primary key clustered (coddpto)
);
    
CREATE TABLE area(
	codarea int auto_increment,
    nombre varchar(40),
    dpto int,
    constraint PK_area primary key clustered (codarea),
    constraint FK_dpto_area foreign key (dpto) references dpto(coddpto),
    constraint UNQ_area unique(nombre, dpto)
);

CREATE TABLE centro( 
	codcentro int auto_increment,
    nombre varchar(40),
    constraint PK_centro primary key clustered (codcentro),
    constraint UNQ_centro unique (nombre)
);

CREATE TABLE ubicacion(
	codubicacion int auto_increment,
    nombre varchar(40),
    area int,
    centro int,
    oculta char(5),
    constraint PK_ubicacion primary key(codubicacion),
    constraint FK_centro_ub foreign key (centro) references centro(codcentro),
    constraint FK_area_ub foreign key (area) references area(codarea),
    constraint CK_oculta check((oculta='true') or (oculta='false')),
    constraint UNQ_ubicacion unique (nombre, centro, area)    
);

CREATE TABLE calidad(
	codcalidad int auto_increment,
	nombre char(40),
    constraint PK_calidad primary key clustered(codcalidad)
);

CREATE TABLE producto(
	cas varchar(12),
    formula char(30),
    form_desarrollada text,
    peso_mol decimal(20, 9),
    pureza decimal(3, 3),
    numero_einecs varchar(9),
    numero_ec varchar(7),
    precauciones varchar(100) null,
    msds text,
    constraint PK_producto primary key clustered (cas)
);

CREATE TABLE nombre_producto(
	cas varchar(12),
    nombre char(40),
    constraint PK_nombre_producto primary key clustered(cas, nombre),
    constraint FK_cas_np foreign key (cas) references producto(cas)
);

CREATE TABLE peligro(
	frase varchar(9),
    indicacion varchar(255),
    constraint PK_peligro primary key clustered (frase)
);
CREATE TABLE peligro_producto(
	cas varchar(12),
    frase varchar(9),
    constraint PK_peligro_producto primary key clustered(cas, frase),
    constraint FK_cas_pelprod foreign key (cas) references producto(cas),
    constraint FK_frase_pelprod foreign key (frase) references peligro(frase)
);

CREATE TABLE prudencia(
	frase varchar(9),
    consejo varchar(255),
    constraint PK_pruencia primary key clustered (frase)
);
CREATE TABLE prudencia_producto(
	cas varchar(12),
    frase varchar(9),
    constraint PK_prudencia_producto primary key clustered(cas, frase),
    constraint FK_cas_prudprod foreign key (cas) references producto(cas),
    constraint FK_frase_prudprod foreign key (frase) references prudencia(frase)
);

CREATE TABLE pictograma(
	referencia varchar(5),
    descripcion varchar(40),
    constraint PK_pictograma primary key clustered (referencia)
    );
CREATE TABLE pictograma_producto(
	cas varchar(12),
    referencia varchar(5),
    constraint PK_pictograma_producto primary key clustered(cas, referencia),
    constraint FK_cas_picprod foreign key (cas) references producto(cas),
    constraint FK_referencia_picprod foreign key (referencia) references pictograma(referencia)
);


CREATE TABLE proveedor(
	codproveedor int auto_increment,
    nombre varchar(50),
    direccion varchar(40) null,
    tfno char(20) null,
    fax char(20) null,
    email varchar(40) null,
    constraint PK_codproveedor primary key clustered (codproveedor),
    constraint UNQ_proveedor unique(nombre)
);

CREATE TABLE marca(
	codmarca int auto_increment,
	nombre varchar(50),
    telefono varchar(20),
    direccion varchar(100),
    constraint PK_marca primary key clustered(codmarca)
);

CREATE TABLE prov_marca(
	proveedor int,
    marca int,
    constraint PK_prov_marca primary key clustered(proveedor, marca),
    constraint FK_proveedor_pm foreign key (proveedor) references proveedor(codproveedor),
    constraint FK_marca_pm foreign key (marca) references marca(codmarca)
);

CREATE TABLE usuarios(
	idusuario int auto_increment,
    usuario varchar(50),
    contrasena varchar(50),
    nombre varchar(50),
    mail varchar(50),
    rol int,
    area int,
    federada char(5),
    activo char(5),
    fecha_creacion datetime,
    constraint PK_usuarios primary key (idusuario),
    constraint UNQ_usuarios_usuario unique(usuario),
    constraint UNQ_usuarios_mail unique(mail),
    constraint CK_usuarios_fed check ((federada = 'true') OR (federada = 'false')),
    constraint CK_usuarios_act check ((activo = 'true') OR (activo = 'false')),
    constraint FK_usuarios_area foreign key (area) references area(codarea)
);

CREATE TABLE ficha(
	codficha int auto_increment,
    producto varchar(12),
    capacidad decimal(11,4),
    g_ml char(2),
    calidad int,
    ubicacion int,
    marca int,
    proveedor int,
    fechacaducidad datetime null,
    lote char(10) null,
    residuo char(5),
    stock int,
    constraint PK_ficha primary key (codficha),
    constraint FK_calidad_ficha foreign key (calidad) references calidad(codcalidad),
    constraint FK_ubicacion_ficha foreign key (ubicacion) references ubicacion(codubicacion),
    constraint FK_proveedor foreign key (proveedor) references proveedor(codproveedor),
    constraint FK_marca_ficha foreign key (marca) references marca(codmarca),
    constraint FK_producto_ficha foreign key (producto) references producto(cas),
    constraint CK_s_n_entrada check ((residuo = 'true') OR (residuo = 'false'))
);

CREATE TABLE entrada (
	codentrada int auto_increment,
    ficha int,
    unidades decimal(6),
    fecha datetime,
    nota varchar(100),
    usuario int,
    constraint PK_entrada primary key (codentrada),
    constraint FK_ficha_entrada foreign key (ficha) references ficha(codficha),
    constraint FK_usuario_entrada foreign key (usuario) references usuarios(idusuario)
);

CREATE TABLE salida(
	codsalida int auto_increment,
    ficha int,
	unidades decimal(6),
    fecha datetime,
    nota varchar(100),
    usuario int,
    constraint PK_salida primary key (codsalida),
    constraint FK_ficha_salida foreign key (ficha) references ficha(codficha),
    constraint FK_usuario_salida foreign key (usuario) references usuarios(idusuario)
);

INSERT INTO usuarios (usuario, contrasena) VALUES ('admin', 'Y1ZAbRz18/ZxnLRt0CMMYA==');
UPDATE usuarios SET nombre = 'Administrador', mail = 'admin@prueba.es', federada = 'false', activo = 'true', rol = 1, fecha_creacion = NOW() WHERE idusuario = 1;
