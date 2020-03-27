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





    