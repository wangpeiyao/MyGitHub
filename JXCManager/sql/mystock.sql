/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 11                       */
/* Created on:     2018-01-03 10:04:40                          */
/*==============================================================*/


if exists(select 1 from sys.sysforeignkey where role='FK_T_REBATE_REFERENCE_T_GOOD') then
    alter table t_Rebate
       delete foreign key FK_T_REBATE_REFERENCE_T_GOOD
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_T_SELL_O_REFERENCE_T_CUSTOM') then
    alter table t_Sell_order
       delete foreign key FK_T_SELL_O_REFERENCE_T_CUSTOM
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_T_SELL_O_REFERENCE_T_GOOD') then
    alter table t_Sell_order
       delete foreign key FK_T_SELL_O_REFERENCE_T_GOOD
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_T_BUY_OR_REFERENCE_T_GOOD') then
    alter table t_buy_order
       delete foreign key FK_T_BUY_OR_REFERENCE_T_GOOD
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_T_ORDER__REFERENCE_T_BUY_OR') then
    alter table t_order_log
       delete foreign key FK_T_ORDER__REFERENCE_T_BUY_OR
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_T_ORDER__REFERENCE_T_SELL_O') then
    alter table t_order_log
       delete foreign key FK_T_ORDER__REFERENCE_T_SELL_O
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_T_STOCK_REFERENCE_T_GOOD') then
    alter table t_stock
       delete foreign key FK_T_STOCK_REFERENCE_T_GOOD
end if;

if exists(
   select 1 from sys.systable 
   where table_name='T_custom'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table T_custom
end if;

if exists(
   select 1 from sys.systable 
   where table_name='t_Rebate'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table t_Rebate
end if;

if exists(
   select 1 from sys.systable 
   where table_name='t_Sell_order'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table t_Sell_order
end if;

if exists(
   select 1 from sys.systable 
   where table_name='t_buy_order'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table t_buy_order
end if;

if exists(
   select 1 from sys.systable 
   where table_name='t_good'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table t_good
end if;

if exists(
   select 1 from sys.systable 
   where table_name='t_order_log'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table t_order_log
end if;

if exists(
   select 1 from sys.systable 
   where table_name='t_stock'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table t_stock
end if;


create table t_user 
(
   uid                  integer NOT NULL IDENTITY(1, 1) ,
   uname                varchar(56)                       not  null,
   userName  varchar(56)                         null,
   pass              varchar(56)     not null,
   state    integer not null default 1,
   createtime           datetime                       null,
   lastupdatetime       datetime                       null,
   constraint PK_t_user primary key clustered (uid)
);

/*==============================================================*/
/* Table: T_custom                                              */
/*==============================================================*/
create table T_custom 
(
   cid                  integer NOT NULL IDENTITY(1, 1) ,
   cname                VARCHAR(60)                  null,
   createtime           datetime                       null,
   lastupdatetime       datetime                       null,
   treeno              varchar(56)           null,
   PARENTID            integer      null,
    PARENTID            integer      null,
   constraint PK_T_CUSTOM primary key clustered (cid)
);

/*==============================================================*/
/* Table: t_Rebate                                              */
/*==============================================================*/
create table t_Rebate 
(
   rid                 integer NOT NULL IDENTITY(1, 1) ,
   gid                  integer                       not  null,
   expression           VARCHAR(128)                 null,
   sdate                DATETIME                           null,
   edate                DATETIME                           null,
   constraint PK_T_REBATE primary key clustered (rid)
);

/*==============================================================*/
/* Table: t_Sell_order                                          */
/*==============================================================*/
create table t_Sell_order 
(
   soid                 integer NOT NULL IDENTITY(1, 1) ,
   cid                  integer                       not null,
   gid                  integer                        NOT NULL,
   orderdate           datetime                        NOT null,
   "count"               integer                        NOT NULL,
   sumamount            NUMERIC                         null,
   amount               NUMERIC                         null,
   memo                 VARCHAR(255)                 null,
   createtime           datetime                       null,
   lastupdatetime       datetime                       null,
     SELLNUM        VARCHAR(13)                     not   null,
   constraint PK_T_SELL_ORDER primary key clustered (soid)
);

/*==============================================================*/
/* Table: t_buy_order                                           */
/*==============================================================*/
create table t_buy_order 
(
   boid                 integer NOT NULL IDENTITY(1, 1) ,
   gid                  integer                        not null,
   orderdate           datetime                           not null,
   count                integer                        not null,
    sumamount            NUMERIC                         null,
   amount               NUMERIC                         null,
   buyNUM        VARCHAR(13)                     not   null,
   memo                 VARCHAR(255)                 null,
   createtime           datetime                       null,
   lastupdatetime       datetime                       null,
   constraint PK_T_BUY_ORDER primary key clustered (boid)
);

/*==============================================================*/
/* Table: t_good                                                */
/*==============================================================*/
create table t_goods 
(
   gid                  integer NOT NULL IDENTITY(1, 1) ,
   gname                VARCHAR(30)                not  null,
      spec                VARCHAR(30)                  null,
  place                  VARCHAR(30)                  null,
   isRebate                 integer                     not   null,
   memo           VARCHAR(256)                   null,
   createtime           datetime                       null,
   lastupdatetime       datetime                       null,
   constraint PK_T_GOOD primary key clustered (gid)
);

/*==============================================================*/
/* Table: t_order_log                                           */
/*==============================================================*/
create table t_order_log 
(
   olid                  integer NOT NULL IDENTITY(1, 1) ,
   soid                 integer                        null,
   optype               integer                        null,
   remark               VARCHAR(256)                 null,
   createtime           datetime                       null,
   boid                 integer                        null,
   constraint PK_T_ORDER_LOG primary key clustered (olid)
);

/*==============================================================*/
/* Table: t_stock                                               */
/*==============================================================*/
create table t_stock 
(
   sid                  integer NOT NULL IDENTITY(1, 1) ,
   gid                  integer                       not  null,
   lavenum              integer                        null,
   sdate                datetime                        not   null,
   constraint PK_T_STOCK primary key clustered (sid)
);

alter table t_Rebate
   add constraint FK_T_REBATE_REFERENCE_T_GOOD foreign key (gid)
      references t_good (gid)
      on update restrict
      on delete restrict;

alter table t_Sell_order
   add constraint FK_T_SELL_O_REFERENCE_T_CUSTOM foreign key (cid)
      references T_custom (cid)
      on update restrict
      on delete restrict;

alter table t_Sell_order
   add constraint FK_T_SELL_O_REFERENCE_T_GOOD foreign key (gid)
      references t_good (gid)
      on update restrict
      on delete restrict;

alter table t_buy_order
   add constraint FK_T_BUY_OR_REFERENCE_T_GOOD foreign key (gid)
      references t_good (gid)
      on update restrict
      on delete restrict;

alter table t_order_log
   add constraint FK_T_ORDER__REFERENCE_T_BUY_OR foreign key (boid)
      references t_buy_order (boid)
      on update restrict
      on delete restrict;

alter table t_order_log
   add constraint FK_T_ORDER__REFERENCE_T_SELL_O foreign key (soid)
      references t_Sell_order (soid)
      on update restrict
      on delete restrict;

alter table t_stock
   add constraint FK_T_STOCK_REFERENCE_T_GOOD foreign key (gid)
      references t_good (gid)
      on update restrict
      on delete restrict;

