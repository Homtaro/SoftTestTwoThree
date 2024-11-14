insert INTO public.animals_warehouse(
    animal, quantity
) VALUES (
    'dog', 20
);
insert INTO public.animals_warehouse(
    animal, quantity
) VALUES (
             'cat', 40
         );
insert INTO public.animals_orders(
    animal_id, quantity, status
) VALUES (
    1, 10, 'IN_PROGRESS'
);