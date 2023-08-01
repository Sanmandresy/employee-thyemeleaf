-- we update the is_expired during select
create or replace function select_and_update_isExpired(p_id varchar)
    returns setof "session" AS $$
begin
    update "session" set is_expired = (validity < CURRENT_TIMESTAMP) where id = p_id;
    return query select * from "session" where id = p_id;
end;
$$
    language plpgsql;
