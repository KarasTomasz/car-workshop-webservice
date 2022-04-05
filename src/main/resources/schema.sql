create table if not exists COMMENT(
    id bigint primary key not null,
    description varchar(255),
    createAt dateTime,
    updateA dateTime,
    userId bigint
);

create table if not exists USER(
    id bigint identity,
    rank varchar(255)

);

create table if not exists USER_COMMENT(
    userId bigint not null
);

alter table COMMENT
    add foreign key (userId) references USER(id);