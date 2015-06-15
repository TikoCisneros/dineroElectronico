/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     14/06/2015 20:52:53                          */
/*==============================================================*/
/*==============================================================*/
/* SECUENCIAS PARA ID                                           */
/*==============================================================*/

CREATE SEQUENCE public.sec_usuario
   INCREMENT 1
   START 1;

CREATE SEQUENCE public.sec_transaccion
   INCREMENT 1
   START 1;

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE (
   CEDULA               VARCHAR(10)          not null,
   NRO_CUENTA           TEXT                 null,
   ID_TIPO              INT4                 null,
   NOMBRE               TEXT                 null,
   APELLIDO             TEXT                 null,
   TELEFONO             VARCHAR(20)          null,
   CORREO               TEXT                 null,
   DIRECCION            TEXT                 null,
   ALIAS                TEXT                 null,
   PASS                 TEXT                 null,
   constraint PK_CLIENTE primary key (CEDULA)
);

/*==============================================================*/
/* Table: CUENTA                                                */
/*==============================================================*/
create table CUENTA (
   NRO_CUENTA           TEXT                 not null,
   SALDO                NUMERIC(15,2)        null,
   TOKEN                TEXT                 null,
   constraint PK_CUENTA primary key (NRO_CUENTA)
);

/*==============================================================*/
/* Table: TIPOTRANS                                             */
/*==============================================================*/
create table TIPOTRANS (
   ID_TIPOTRANS         INT4                 not null,
   TIPOTRANS            TEXT                 null,
   constraint PK_TIPOTRANS primary key (ID_TIPOTRANS)
);

/*==============================================================*/
/* Table: TIPOUSR                                               */
/*==============================================================*/
create table TIPOUSR (
   ID_TIPO              INT4                 not null,
   TIPO                 TEXT                 null,
   constraint PK_TIPOUSR primary key (ID_TIPO)
);

/*==============================================================*/
/* Table: TRANSACCION                                           */
/*==============================================================*/
create table TRANSACCION (
   ID_TRANS             NUMERIC              not null DEFAULT nextval('sec_transaccion'::regclass),
   ID_TIPOTRANS         INT4                 null,
   NRO_CUENTA           TEXT                 null,
   SALDO_ACTUAL         NUMERIC(15,2)        null,
   MONTO                NUMERIC(15,2)        null,
   NROC_DESTINO         TEXT                 null,
   SALDO_FINAL          NUMERIC(15,2)        null,
   FECHA                TIMESTAMP            null,
   constraint PK_TRANSACCION primary key (ID_TRANS)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   ID_USR               INT4                 not null DEFAULT nextval('sec_usuario'::regclass),
   ID_TIPO              INT4                 null,
   NOMBRE               TEXT                 null,
   APELLIDO             TEXT                 null,
   ALIAS                TEXT                 null,
   PASS                 TEXT                 null,
   constraint PK_USUARIO primary key (ID_USR)
);

alter table CLIENTE
   add constraint FK_CLIENTE_REFERENCE_CUENTA foreign key (NRO_CUENTA)
      references CUENTA (NRO_CUENTA)
      on delete restrict on update restrict;

alter table CLIENTE
   add constraint FK_CLIENTE_REFERENCE_TIPOUSR foreign key (ID_TIPO)
      references TIPOUSR (ID_TIPO)
      on delete restrict on update restrict;

alter table TRANSACCION
   add constraint FK_TRANSACC_REFERENCE_TIPOTRAN foreign key (ID_TIPOTRANS)
      references TIPOTRANS (ID_TIPOTRANS)
      on delete restrict on update restrict;

alter table USUARIO
   add constraint FK_USUARIO_REFERENCE_TIPOUSR foreign key (ID_TIPO)
      references TIPOUSR (ID_TIPO)
      on delete restrict on update restrict;


/*==============================================================*/
/* DATOS SQL		                                            */
/*==============================================================*/

insert into tipousr values (1,'Administrador');
insert into tipousr values (2,'Vendedor');
insert into tipousr values (3,'Usuario');

insert into usuario values (default, 1, 'root', 'root', 'root', 'admin');

insert into tipotrans values (1,'Recarga');
insert into tipotrans values (2,'Transferencia');