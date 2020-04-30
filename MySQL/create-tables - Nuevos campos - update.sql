DROP TABLE IF EXISTS 
	roles, 
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
	centro_area,
	centro, 
	area,  
	dpto;


CREATE TABLE dpto(
	nombre char(25),
    constraint PK_dpto primary key clustered (nombre)
);
    
CREATE TABLE area(
	codarea int auto_increment,
    nombre varchar(40),
    dpto char(25),
    constraint PK_area primary key clustered (codarea),
    constraint FK_dpto_area foreign key (dpto) references dpto(nombre),
    constraint UNQ_area unique(nombre, dpto)
);

CREATE TABLE centro( 
	codcentro int auto_increment,
    nombre varchar(40),
    constraint PK_centro primary key clustered (codcentro),
    constraint UNQ_centro unique (nombre)
);

CREATE TABLE centro_area(
	centro int,
    area int,
    constraint PK_centro_area primary key(centro, area),
    constraint FK_centro_ca foreign key (centro) references centro(codcentro),
    constraint FK_area_ca foreign key (area) references area(codarea)
);

CREATE TABLE ubicacion(
	codubicacion int auto_increment,
    nombre varchar(30),
    area int,
    centro int,
    oculta char(1),
    constraint PK_ubicacion primary key(codubicacion),
    constraint FK_centro_area_ub foreign key (area, centro) references centro_area(area, centro),
    constraint CK_oculta check((oculta='s') or (oculta='n')),
    constraint UNQ_ubicacion unique (nombre, centro, area)    
);

CREATE TABLE calidad(
	codcalidad int auto_increment,
	nombre char(10),
    constraint PK_calidad primary key clustered(codcalidad)
);

CREATE TABLE producto(
	cas varchar(12),
    formula char(30),
    form_desarrollada text,
    numero_einecs varchar(9),
    numero_ec varchar(7),
    precauciones varchar(40) null,
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
    indicacion varchar(200),
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
    consejo varchar(200),
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
    nombre varchar(40),
    direccion varchar(40) null,
    tfno char(9) null,
    fax char(9) null,
    email varchar(40) null,
    contacto varchar(40) null,
    constraint PK_codproveedor primary key clustered (codproveedor),
    constraint UNQ_proveedor unique(nombre)
);

CREATE TABLE marca(
	codmarca int auto_increment,
	nombre char(20),
    descripcion varchar(100),
    telefono varchar(10),
    direccion varchar(100),
    constraint PK_marca primary key clustered(codmarca)
);

CREATE TABLE prov_marca(
	proveedor int,
    marca int,
    constraint PK_prov_marca primary key clustered(proveedor, marca),
    constraint FK_proveedor_pm foreign key (proveedor) references proveedor(codproveedor),
    constraint FK_marca_pm foreign key (marca) references marca(nombre)
);

CREATE TABLE ficha(
	codficha int auto_increment,
    calidad int,
    ubicacion int,
    proveedor int,
    marca int,
    producto varchar(12),
    constraint PK_ficha primary key (codficha),
    constraint FK_calidad_ficha foreign key (calidad) references calidad(codcalidad),
    constraint FK_ubicacion_ficha foreign key (ubicacion) references ubicacion(codubicacion),
    constraint FK_proveedor foreign key (proveedor) references prov_marca(proveedor),
    constraint FK_marca_ficha foreign key (marca) references prov_marca(marca),
    constraint FK_producto_ficha foreign key (producto) references producto(cas)
);

CREATE TABLE entrada (
	codentrada int auto_increment,
    ficha int,
    fecha datetime,
    fechacaducidad datetime null,
    lote char(10) null,
    unidades decimal(6),
    capacidad decimal(11,4),
    g_ml char(2),
    residuo char(1),
    constraint PK_entrada primary key (codentrada),
    constraint FK_ficha_entrada foreign key (ficha) references ficha(codficha),
    constraint CK_s_n_entrada check ((residuo = 's') OR (residuo = 'n'))
);

CREATE TABLE salida(
	codsalida int auto_increment,
    ficha int,
    fecha datetime,
    fechacaducidad datetime null,
    lote char(10) null,
    unidades decimal(6),
    capacidad decimal(11, 4),
    g_ml char(2),
    residuo char(1),
    constraint PK_salida primary key (codsalida),
    constraint FK_ficha_salida foreign key (ficha) references ficha(codficha),
    constraint CK_s_n_salida check ((residuo = 's') OR (residuo = 'n'))
);

CREATE TABLE usuarios(
	idusuario int auto_increment,
    usuario varchar(50),
    contrasena varchar(50),
    nombre varchar(50),
    mail varchar(50),
    federada char(1),
    rol int,
    fecha_creacion datetime,
    constraint PK_usuarios primary key (idusuario),
    constraint UNQ_usuarios_usuario unique(usuario),
    constraint UNQ_usuarios_mail unique(mail),
    constraint CK_usuarios_fed check ((federada = 's') OR (federada = 'n')),
    constraint FK_usuarios foreign key (rol) references roles(idrol)
);

CREATE TABLE roles(
	idrol int auto_increment,
    rol varchar(20)
);



    