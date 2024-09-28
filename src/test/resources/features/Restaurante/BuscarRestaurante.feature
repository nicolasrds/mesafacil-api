# language: pt
Funcionalidade: Buscar Restaurante Específico

  Cenário: Buscar um restaurante específico por ID
    Dado que existe um restaurante com ID 1
    Quando uma requisição GET é feita para "/api/restaurantes/1"
    Então deve retornar os detalhes do restaurante
    E deve retornar um status 200

  Cenário: Falha ao buscar um restaurante com ID inexistente
    Dado que o restaurante com ID 999 não existe
    Quando uma requisição GET é feita para "/api/restaurantes/999"
    Então deve retornar uma mensagem de erro
    E deve retornar um status 404
