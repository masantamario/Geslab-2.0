CREATE TABLE dpto(
	nombre char(25),
    constraint PK_dpto primary key clustered (nombre)
);
    
CREATE TABLE area(
	codarea char(15),
    nombre varchar(40),
    dpto char(25),
    constraint PK_area primary key clustered (codarea),
    constraint FK_dpto_area foreign key (dpto) references dpto(nombre),
    constraint UNQ_area unique(nombre, dpto)
);

CREATE TABLE centro( 
	codcentro char(2),
    nombre varchar(40),
    constraint PK_centro primary key clustered (codcentro),
    constraint UNQ_centro unique (nombre)
);

CREATE TABLE centro_area(
	centro char(2),
    area char(15),
    constraint PK_centro_area primary key(centro, area),
    constraint FK_centro_ca foreign key (centro) references centro(codcentro),
    constraint FK_area_ca foreign key (area) references area(codarea)
);

CREATE TABLE ubicacion(
	codubicacion char(4),
    nombre varchar(30),
    area char(15),
    centro char(2),
    oculta char(1),
    constraint PK_ubicacion primary key(codubicacion),
    constraint FK_centro_area_ub foreign key (area, centro) references centro_area(area, centro),
    constraint CK_oculta check((oculta='s') or (oculta='n')),
    constraint UNQ_ubicacion unique (nombre, centro, area)    
);

CREATE TABLE calidad(
	nombre char(10),
    constraint PK_calidad primary key clustered(nombre)
);

CREATE TABLE producto(
	nombre char(40),
    formula char(30),
    precauciones varchar(40) null,
    constraint PK_producto primary key clustered (nombre)
);

CREATE TABLE proveedor(
	codproveedor char(4),
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
	nombre char(20),
    constraint PK_marca primary key clustered(nombre)
);

CREATE TABLE prov_marca(
	proveedor char(4),
    marca char(20),
    constraint PK_prov_marca primary key clustered(proveedor, marca),
    constraint FK_proveedor_pm foreign key (proveedor) references proveedor(codproveedor),
    constraint FK_marca_pm foreign key (marca) references marca(nombre)
);

CREATE TABLE ficha(
	codficha char(10),
    calidad char(10),
    ubicacion char(4),
    proveedor char(4),
    marca char(20),
    producto char(40),
    constraint PK_ficha primary key (codficha),
    constraint FK_calidad_ficha foreign key (calidad) references calidad(nombre),
    constraint FK_ubicacion_ficha foreign key (ubicacion) references ubicacion(codubicacion),
    constraint FK_proveedor foreign key (proveedor) references prov_marca(proveedor),
    constraint FK_marca_ficha foreign key (marca) references prov_marca(marca),
    constraint FK_producto_ficha foreign key (producto) references producto(nombre)
);

CREATE TABLE entrada (
	codentrada char(20),
    ficha char(10),
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
	codsalida char(20),
    ficha char(10),
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


CREATE TABLE maximo(
	centro char(2),
    ubicacion char(4),
    proveedor char(4),
    ficha char(10),
    entrada char(20),
    salida char(20)
);

INSERT INTO maximo VALUES('00', '0000', '0000', '0000000000', '00000000000000000000', '00000000000000000000');



    