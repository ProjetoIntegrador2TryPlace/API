appId: host.exp.Exponent
---
- launchApp

- tapOn:
    id: "input-nome"
- inputText: "João"

- tapOn:
    id: "input-sobrenome"
- inputText: "Silva"

- tapOn:
    id: "input-email"
- inputText: "joao.teste.e2e@email.com"

- tapOn:
    id: "input-cpf"
- inputText: "52998224725"

- tapOn:
    id: "input-telefone"
- inputText: "88999999999"

- tapOn:
    id: "input-data"
- inputText: "01011990"

- tapOn:
    id: "input-senha"
- inputText: "12345678"

- tapOn:
    id: "input-confirmar-senha"
- inputText: "12345678"

- tapOn:
    id: "botao-criar-conta"

- assertVisible: "Conta criada com sucesso!"