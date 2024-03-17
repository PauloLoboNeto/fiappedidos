<br />
<div align="center">

  <h3 align="center">Microsserviço de Pedidos</h3>

  <p align="center">
    Aplicação criada para o projeto de Pós-Graduação em Arquitetura de software pela FIAP.
    <br />
    <br />
    <a href="https://github.com/PauloLoboNeto/fiappedidos/issues">Report Bug</a>
    ·
    <a href="https://github.com/PauloLoboNeto/fiappedidos/issues">Request Feature</a>
  </p>
</div>


<details>
  <summary>Tabela de conteúdos</summary>
  <ol>
    <li>
      <a href="#about-the-project">Sobre o projeto</a>
      <ul>
        <li><a href="#built-with">Construído com</a></li>
      </ul>
    </li>
    <li>
      <a href="#local-execution">Como executar local</a>
      <ul>
        <li><a href="#setup">Set up</a></li>
        <li><a href="#run-locally">Run locally</a></li>
      </ul>
    </li>
  </ol>
</details>


## Sobre o projeto

- Utilizando Arquitetura Hexagonal, conceitos de DDD(https://miro.com/app/board/uXjVMC27TvQ=/?share_link_id=505879927156).
  


Esta aplicação foi criada por:
- Gabriel Almeida dos Santos, rm430120, gabrielalmeidads@gmail.com
- Paulo Lobo Neto, rm430057, pauloloboneto@gmail.com

<p align="right">(<a href="#readme-top">ir para o topo</a>)</p>


### Construído com

<div align="center"> 

[![Docker][Docker]][Docker-url]

</div> 

<div align="center"> 

[![Java][Java]][Java-url]

</div> 

<div align="center"> 

[![AWS][AWS]][AWS-url]

</div> 

<div align="center"> 

[![H2][H2]][H2-url]

</div> 

<div align="center"> 

[![INSOMNIA][INSOMNIA]][INSOMNIA-url]

</div> 


<p align="right">(<a href="#readme-top">ir para o topo</a>)</p>


### Set up & Run Locally

Para roda essa aplicação, siga os seguintes steps:

1. Instale o [docker](https://docs.docker.com/desktop/?_gl=1*f60bmt*_ga*MTEzMjc4Nzg0NS4xNjkwNjc0MTM0*_ga_XJWPQMJYHQ*MTcxMDY1MjA5MC4xMS4xLjE3MTA2NTIwOTEuNTkuMC4w)
2. Na pasta .aws, altere as credenciais: aws_access_key_id e aws_secret_access_key para as suas credenciais da AWS. Esse step é importante, pois automaticamente quando o container subir, ele irá criar uma fila SQS na sua conta de forma automática. Se atente aos custos!
3. Na raíz do projeto, executo o comando: docker compose up
4. Instale o [insomnia](INSOMNIA)
5. Use a collection(pedidos-insomnia.json) do insomnia que está localizada na raíz desse repositório.
   


<!-- MARKDOWN LINKS & IMAGES -->
[Java]: https://img.shields.io/badge/Java-0769AD?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com/pt-BR/

[Docker]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/

[AWS]: https://img.shields.io/badge/AWS-ffa500?style=for-the-badge&logo=AWS&logoColor=orange
[AWS-url]: https://docs.aws.amazon.com/?nc2=h_ql_doc_do&refid=2ee11bb2-bc40-4546-9852-2c4ad8e8f646

[MONGO]: https://img.shields.io/badge/MONGO-008000?style=for-the-badge&logo=MONGO&logoColor=green
[MONGO-url]: https://www.mongodb.com

[H2]: https://img.shields.io/badge/H2-add8e6?style=for-the-badge&logo=H2&logoColor=blue
[H2-url]: https://www.h2database.com/html/main.html

[INSOMNIA]: https://img.shields.io/badge/INSOMNIA-993399?style=for-the-badge&logo=H2&logoColor=purple
[INSOMNIA-url]: https://insomnia.rest/download