create table resume
(
    uuid      char(36) not null
        primary key,
    full_name text
);

create table contact
(
    id          serial,
    resume_uuid char(36) not null
        references resume (uuid)
            on update restrict
            on delete cascade,
    type        text not null,
    value       text not null
);

create unique index contact_uuid_id_index
    on public.contact (id, resume_uuid, type);

