# language: pt
Funcionalidade: Cancelar Reserva

  Cenário: Cancelar uma reserva existente
    Dado que existe uma reserva com ID 1
    Quando uma requisição DELETE é feita para "/api/reservas/1"
    Então a reserva deve ser cancelada com sucesso
    E deve retornar um status 200

  Cenário: Falha ao cancelar reserva inexistente
    Dado que a reserva com ID 999 não existe
    Quando uma requisição DELETE é feita para "/api/reservas/999"
    Então deve retornar uma mensagem de erro
    E deve retornar um status 404
