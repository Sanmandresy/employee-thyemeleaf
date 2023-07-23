-- create sequence
create sequence if not exists employee_seq start 1;

-- Function to generate custom registration number
create or replace function generate_registration_number()
    returns trigger as
$$
begin
    new.registration_number := 'EMP-' || lpad(nextval('employee_seq')::text, 3, '0');
    return new;
end;
$$
    language plpgsql;

-- Creating the trigger to generate the registration number on insert
create trigger insert_new_employee
    before insert
    on "employee"
    for each row
execute function generate_registration_number();