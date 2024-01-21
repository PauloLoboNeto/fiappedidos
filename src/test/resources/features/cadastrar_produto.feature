# language: pt
Funcionalidade: Cadastrar Produto

  Cenario: Cadastrar Produto - Lanche
    Dado que o produto de tipo "1" não está cadastrado
    Quando cadastrar o produto
    Entao o produto é cadastrado com sucesso

  Cenario: Cadastrar Produto - Bebida
    Dado que o produto de tipo "2" não está cadastrado
    Quando cadastrar o produto
    Entao o produto é cadastrado com sucesso

  Cenario: Cadastrar Produto - Sobremesa
    Dado que o produto de tipo "3" não está cadastrado
    Quando cadastrar o produto
    Entao o produto é cadastrado com sucesso

  Cenario: Cadastrar Produto - Acompanhamento
    Dado que o produto de tipo "4" não está cadastrado
    Quando cadastrar o produto
    Entao o produto é cadastrado com sucesso