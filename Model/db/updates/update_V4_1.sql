alter table request_action add column message varchar(1024);
alter table request_action alter column note type varchar(1024);

alter table request_action add column type varchar(255);
update request_action set type = 'DraftDeleteNotification' where label = 'DRAFT_DELETE_NOTIFICATION';
update request_action set type = 'CreationNotification' where label = 'REQUEST_CREATION_NOTIFICATION';
update request_action set type = 'OrangeAlertNotification' where label = 'REQUEST_ORANGE_ALERT_NOTIFICATION';
update request_action set type = 'RedAlertNotification' where label = 'REQUEST_RED_ALERT_NOTIFICATION';
update request_action set type = 'Creation' where label = 'CREATION_ACTION';
update request_action set type = 'StateChange' where label = 'STATE_CHANGE_ACTION';
update request_action set type = 'ContactCitizen' where label = 'REQUEST_CONTACT_CITIZEN';
alter table request_action drop column label;

alter table request add column request_season_id int8;

create table request_season (
  id int8 not null,
  request_type_id int8,
  effect_end timestamp,
  effect_start timestamp,
  label varchar(255),
  registration_end timestamp,
  registration_start timestamp,
  validation_authorization_start timestamp,
  primary key (id)
);

alter table request_season
  add constraint FK998F4693C5FD0068
  foreign key (request_type_id)
  references request_type;

create or replace function migrate_seasons() returns void as $$
  declare
    current_record record;
  begin
    for current_record in select uuid from seasons loop
      perform nextval('hibernate_sequence');
      update request set request_season_id = currval('hibernate_sequence') where season_uuid = current_record.uuid;
      insert into request_season values (currval('hibernate_sequence'));
      update request_season set
        request_type_id = s.request_type_id,
        registration_start = s.registration_start,
        registration_end = s.registration_end,
        effect_start = s.effect_start,
        effect_end = s.effect_end,
        validation_authorization_start = s.validation_authorization_start,
        label = s.label
      from (select request_type_id, registration_start, registration_end, effect_start, effect_end, validation_authorization_start, label from seasons where seasons.uuid = current_record.uuid) as s
      where request_season.id = currval('hibernate_sequence');
    end loop;
  end;
$$ LANGUAGE plpgsql;

select * from migrate_seasons();

alter table request
  add constraint FK414EF28F85577048
  foreign key (request_season_id)
  references request_season;

alter table request_season alter column request_type_id set not null;
alter table request_season alter column effect_end set not null;
alter table request_season alter column effect_start set not null;
alter table request_season alter column label set not null;
alter table request_season alter column registration_end set not null;
alter table request_season alter column registration_start set not null;

drop table seasons;

create or replace function migrate_request_states() returns void as $$
  declare
    current_record record;
  begin
    for current_record in select id from request where state = 'Active' loop
      delete from request_action where request_id = current_record.id and resulting_state = 'Active';
      update request set state = 'Notified' where id = current_record.id;
    end loop;
    for current_record in select id from request where state = 'Expired' loop
      delete from request_action where request_id = current_record.id and resulting_state in ('Active', 'Expired');
      update request set state = 'Archived' where id = current_record.id;
      insert into request_action values (
        nextval('hibernate_sequence'),
        -1,
        'Suppression des pseudo-états "actif" et "expiré"',
        now(),
        null,
        current_record.id,
        'Archived',
        null,
        'StateChange'
      );
    end loop;
  end;
$$ LANGUAGE plpgsql;

select * from migrate_request_states();

drop function migrate_request_states();
drop function migrate_seasons();

-- Study Grant Request RGAA refactoring
alter table study_grant_request rename current_studies to current_studies_diploma;

alter table request_action alter column request_id set not null;

update request_form set xsl_fo_filename = substring(xsl_fo_filename from 1 for position('.xsl' in xsl_fo_filename) - 1) where type = 'Request Certificate' and position('.xsl' in xsl_fo_filename) > 0;

alter table request_type drop column has_automatic_activation;

DELETE FROM agent_category_roles where profile = 'None';
