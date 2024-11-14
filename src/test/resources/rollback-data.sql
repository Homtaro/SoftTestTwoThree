delete from public.animals_orders;

delete from public.animals_warehouse;

insert INTO public.animals_warehouse(
    id, animal, quantity
) VALUES (
             1, 'dog', 20
         );
insert INTO public.animals_warehouse(
    id, animal, quantity
) VALUES (
             2, 'cat', 40
         );

insert INTO public.animals_orders(
    id, animal_id, quantity, status
) VALUES (
             1, 1, 10, 'IN_PROGRESS'
         );