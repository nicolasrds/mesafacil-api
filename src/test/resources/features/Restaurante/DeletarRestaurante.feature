# language: pt
Funcionalidade: Excluir Restaurante

  Cenário: Excluir um restaurante existente
    Dado que existe um restaurante com ID 1
    Quando uma requisição DELETE é feita para "/api/restaurantes/1"
    Então o restaurante deve ser excluído com sucesso
    E deve retornar um status 200

  Cenário: Falha ao excluir restaurante inexistente
    Dado que o restaurante com ID 999 não existe
    Quando uma requisição DELETE é feita para "/api/restaurantes/999"
    Então deve retornar uma mensagem de erro
    E deve retornar um status 404
