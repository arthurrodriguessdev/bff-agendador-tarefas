
# BFF - Agendador de Tarefas
BFF é uma sigla para Backend For Frontend, o responsável por centralizar o acesso ao sistema de gerenciamento de tarefas, usuários e notificações.

O projeto utiliza uma arquitetura baseada em microsserviços, onde cada serviço possui uma responsabilidade única. O BFF atua como o ponto de entrada da aplicação, recebendo requisições do cliente e realizando a comunicação com os microsserviços.



## Arquitetura
O sistema é composto pelos seguintes sistemas:

- BFF Agendador de Tarefas - ponto de entrada da aplicação e o responsável por fazer a interface com o cliente e a comunicação entre os microsserviços.
- Usuário - gerenciamento e autenticação de usuários.
- Agendador - gerenciamento de tarefas.
- Notificador - processamento e envio de notificações via e-mail.

### Fluxo da aplicação
![Arquitetura do Sistema](docs/arquitetura.jpg)