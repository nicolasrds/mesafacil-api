# language: pt
Funcionalidade: Atualizar Restaurante

  Cenário: Atualizar um restaurante existente
    Dado que o usuário fornece novos dados para o restaurante com ID 1
    Quando uma requisição PUT é feita para "/api/restaurantes/1"
    Então o restaurante deve ser atualizado com sucesso
    E deve retornar um status 200

  Cenário: Falha ao atualizar restaurante inexistente
    Dado que o restaurante com ID 999 não existe
    Quando uma requisição PUT é feita para "/api/restaurantes/999" com novos dados
    Então deve retornar uma mensagem de erro
    E deve retornar um status 404
