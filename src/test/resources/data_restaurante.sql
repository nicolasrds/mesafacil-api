INSERT INTO reserva.restaurante(
     res_nome, res_localizacao)
VALUES ( 'Restaurante 01', 'Teste 01'),( 'Restaurante 02', 'Teste 01');

INSERT INTO reserva.restaurante_culinarias(
    res_id, res_tipo_culinaria)
VALUES
    ((select r.res_id FROM reserva.restaurante r WHERE r.res_nome = 'Restaurante 01' ), 'ESPANHOLA'),
    ((select r.res_id FROM reserva.restaurante r WHERE r.res_nome = 'Restaurante 02' ), 'ITALIANA');