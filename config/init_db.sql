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
    on contact (resume_uuid, type);

create table section
(
    id          serial primary key,
    resume_uuid char(36) not null
        references resume (uuid)
            on update restrict
            on delete cascade,
    type        text not null,
    content     text not null
);

create unique index section_idx
    on section (id, resume_uuid, type);

