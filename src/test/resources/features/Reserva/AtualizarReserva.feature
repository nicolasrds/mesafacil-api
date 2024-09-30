# language: pt
Funcionalidade: Atualizar Reserva

  Cenário: Atualizar uma reserva existente
    Dado que o usuário fornece novos dados para a reserva com ID 1
    Quando uma requisição PUT é feita para "/api/reservas/1"
    Então a reserva deve ser atualizada com sucesso
    E o status da mesa deve ser atualizado para indisponível
    E deve retornar um status 200

  Cenário: Falha ao atualizar reserva inexistente
    Dado que a reserva com ID 999 não existe
    Quando uma requisição PUT é feita para "/api/reservas/999" com novos dados
    Então deve retornar uma mensagem de erro
    E deve retornar um status 404
