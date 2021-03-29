# BACK END do Teste Prático da Develcode
###### [Site da Develcode](https://www.develcode.com.br/)

### Subir o banco
Rode o comando na raiz do projeto:  ```docker-compose up``` e o container com o banco irá ser levantado.
### Subir a API
Adicione o parâmetro ```-Djps.track.ap.dependencies=false``` para não haver problemas de dependências do Mapstruct.

### Tecnologias utilizadas
- [X] Spring boot - Para produzir a API restful;
- [X] Mapsctruct - Para realizar as transformações de tipos de dados, como Domains de DTOs;
- [X] Lombok - Para tornar o código muito mais minimizado com suas Annotations que geram Getters, Setters e vários construtores;
- [X] Flyway - Para realizar as migrações;
- [X] iText - Para gerar PDFs a partir de scripts HTML;
- [X] Thymeleaf - Para preencher os scripts HTML com os dados corretos.
