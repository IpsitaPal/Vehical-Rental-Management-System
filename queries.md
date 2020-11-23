create table BOOKING (
       BOOKING_ID number(10,0) not null,
        BOOKED_TILL_DATE date,
        BOOKING_DATE date,
        BOOKING_DESCRIPTION varchar2(255 char),
        DISTANCE double precision,
        TOTAL_COST double precision,
        CUSTOMER_ID number(10,0),
        VEHICLE_ID number(10,0),
        primary key (BOOKING_ID)
    );
create table CUSTOMER (
       CUSTOMER_ID number(10,0) not null,
        ADDRESS varchar2(255 char),
        EMAIL varchar2(255 char),
        FIRST_NAME varchar2(255 char),
        LAST_NAME varchar2(255 char),
        MOBILE_NUMBER varchar2(255 char),
        primary key (CUSTOMER_ID)
    );
    create table DRIVER (
       DRIVER_ID number(10,0) not null,
        ADDRESS varchar2(255 char),
        CHARGES_PER_DAY double precision,
        MOBILE_NUMBER varchar2(255 char),
        EMAIL_ID varchar2(255 char),
        FIRST_NAME varchar2(255 char),
        LAST_NAME varchar2(255 char),
        LICENSE_NO varchar2(255 char),
        primary key (DRIVER_ID)
    );
    
    create table PAYMENT (
       PAYMENT_ID number(10,0) not null,
        PAYMENT_DATE date,
        PAYMENT_MODE varchar2(255 char),
        PAYMENT_STATUS varchar2(255 char),
        BOOKING_ID number(10,0),
        primary key (PAYMENT_ID)
    );
    
    create table USER_DETAILS (
       USER_ID varchar2(255 char) not null,
        PASSWORD varchar2(255 char),
        ROLE varchar2(255 char),
        primary key (USER_ID)
    );
    
    create table VEHICLE (
       VEHICLE_ID number(10,0) not null,
        CAPACITY varchar2(255 char),
        CATEGORY varchar2(255 char),
        CHARGE_PER_KM double precision,
        DESCRIPTION varchar2(255 char),
        FIXED_CHARGES double precision,
        LOCATION varchar2(255 char),
        TYPE varchar2(255 char),
        VEHICLE_NUMBER varchar2(255 char),
        DRIVER_ID number(10,0),
        primary key (VEHICLE_ID)
    );
    
    alter table BOOKING 
       add constraint FKhcoq6smikpa3vesuajuan5yse 
       foreign key (CUSTOMER_ID) 
       references CUSTOMER
    
    alter table BOOKING 
       add constraint FKhfjfufx8y13nnngxhgpv9i9ih 
       foreign key (VEHICLE_ID) 
       references VEHICLE

    alter table PAYMENT 
       add constraint FKiw6m28o68ns2m0p7ww74ehn64 
       foreign key (BOOKING_ID) 
       references BOOKING

    alter table VEHICLE 
       add constraint FKeq0v35t6lcq8549u0q408af1b 
       foreign key (DRIVER_ID) 
       references DRIVER

