# language: pt
Funcionalidade: Buscar Reserva Específica

  Cenário: Buscar uma reserva específica por ID
    Dado que existe uma reserva com ID 1
    Quando uma requisição GET é feita para "/api/reservas/1"
    Então deve retornar os detalhes da reserva
    E deve retornar um status 200

  Cenário: Falha ao buscar uma reserva com ID inexistente
    Dado que a reserva com ID 999 não existe
    Quando uma requisição GET é feita para "/api/reservas/999"
    Então deve retornar uma mensagem de erro
    E deve retornar um status 404
