/*
create table if not exists COMMENT(
    id bigint primary key not null,
    description varchar(255),
    createOn dateTime,
    updateOn dateTime
);

create table if not exists USER(
    id bigint identity,
    rank varchar(255)
);

alter table COMMENT
    add foreign key (userId) references USER(id);
*/
