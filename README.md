# sqs-producer-consumer
Este projeto Spring tem como objetivo produzir e consumir mensagens de uma fila SQS da AWS. Ele utiliza os serviços de mensageria assíncronos da AWS para envio e processamento de mensagens, garantindo escalabilidade e alta disponibilidade para sistemas distribuídos.

# Configuração do LocalStack e Docker com SQS

## Passos para Instalação

### 1. Instale o LocalStack
Primeiro, instalei o LocalStack seguindo este vídeo: [Como instalar o LocalStack](https://www.youtube.com/watch?v=yOdp0wz5NeI).

### 2. Instale o Docker
Também instalei o Docker: [Instalação do Docker](https://www.docker.com/).

### 3. Baixe a Imagem do LocalStack
Baixei a imagem do LocalStack no Docker com o seguinte comando:

```bash
docker pull localstack/localstack
```

### 4. Execute o Container com o SQS
Criei um container Docker com o serviço SQS usando o seguinte comando:
```bash
docker run --rm -it -p 4566:4566 -p 4571:4571 -e SERVICES=sqs localstack/localstack
```

### 5. Crie uma Fila SQS
Por fim, criei uma fila no SQS com o comando:
```bash
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name teste-fila
```

