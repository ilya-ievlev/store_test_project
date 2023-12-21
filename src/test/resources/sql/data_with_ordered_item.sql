insert into orders (paid, modification_datetime, user_id)
values (false, current_timestamp, 1);
insert into available_items(name, quantity, price)
VALUES ('item1', 12, 12);
insert into ordered_items (price, quantity, order_id, available_item_id)
values (12, 12, 1, 1);