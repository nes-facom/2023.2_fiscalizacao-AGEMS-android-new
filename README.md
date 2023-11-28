# <p align="center"> App Fiscalização DSBRS AGEMS </p>

Este repositório reúne os artefatos de códigos do Front-End produzidos no ano de 2023, 2° semestre, durante o processo de desenvolvimento do projeto do App de Fiscalização da AGEMS no escopo da disciplinas de Prática em Desenvolvimento de Software II da Faculdade de Computação (FACOM) da Universidade Federal do Mato Grosso do Sul (UFMS).

## Descrição do projeto

A Diretoria de Regulação e Fiscalização - Saneamento Básico e Resíduos Sólidos (DSBRS) regula e fiscaliza os serviços de fornecimento de água potável, esgotamento sanitário e manejo de resíduos sólidos nos municípios conveniados.

Atualmente, as fiscalizações são feitas por meio de formulários físicos e registro de fotografias com smartphone. Cada fiscalização gera um relatório complexo, em que os fiscalizadores separam as centenas de imagens registradas por unidade, inserem as informações do município no Word, conferem o formulário e incluem manualmente todas as imagens, constatações, não conformidades, recomendações e determinações.

A solução proposta foi a criação de um aplicativo que cadastra modelos de formulário para cada unidade de cada município e utiliza os modelos criados como base para criar um formulário. Esses formulários ajudarão os fiscais a determinar se uma unidade está ou não em conformidade com as normas da portaria.

## Funcionalidades

- [x] Cadastro de usuários;
- [x] Autenticação de usuários;
- [x] Listagem de unidades;
- [x] Listagem de modelos;
- [x] Listagem de formulários;
- [x] Criação de modelo;
- [x] Criação de unidade;
- [x] Criação de formulário;
- [x] Sincronização dos dados;
- [x] Logout;

## Requisitos

- [Android Studio](https://developer.android.com/studio);
  - Android SDK Plataform (versão 8.0 ou superior);

**Observação:** As dependências e bibliotecas utilizadas já estão contidas no projeto no `build.gradle` da aplicação.

## Instalação / Implantação

1. Clone o repositório.
3. Abra o projeto no Android Studio.
4. Sincronize o projeto
5. Adicione as linhas abaixo no arquivo local.properties.
```
BASE_URL=http://10.0.2.2:8080  # Este é a URL base para acesso ao backend.
DS_NAME=agems  # Este é o nome do banco local.
```

## Autores e histórico

Este sistema foi desenvolvido pela seguinte equipe:

- [Lucas Vinicius Cardoso Moro](https://github.com/olucasmoro) (lucas.moro@ufms.br)
- [Vagner Filho](https://github.com/Vagner-Filho) (vagner.filho@ufms.br)

Orientado pelo professor [Awdren de Lima Fontão]() (awdren.fontao@ufms.br) e proposto por João Lucas Alves da Silva.
