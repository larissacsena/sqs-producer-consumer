# sqs-producer-consumer
Este projeto Spring tem como objetivo produzir e consumir mensagens de uma fila SQS da AWS. Ele utiliza os serviços de mensageria assíncronos da AWS para envio e processamento de mensagens, garantindo escalabilidade e alta disponibilidade para sistemas distribuídos.

# Configuração do LocalStack e Docker com SQS

## 1. Passos para Instalação:

### 1.1. Instale o LocalStack;
Primeiro, instalei o LocalStack seguindo este vídeo: [Como instalar o LocalStack](https://www.youtube.com/watch?v=yOdp0wz5NeI).

### 1.2. Instale o Docker;
Também instalei o Docker: [Instalação do Docker](https://www.docker.com/).

### 1.3. Baixe a Imagem do LocalStack;
Baixei a imagem do LocalStack no Docker com o seguinte comando:

```bash
docker pull localstack/localstack
```

### 1.4. Execute o Container com o SQS;
Criei um container Docker com o serviço SQS usando o seguinte comando:
```bash
docker run --rm -it -p 4566:4566 -p 4571:4571 -e SERVICES=sqs localstack/localstack
```

## 2. Explicação da classe SqsProducer:
A classe pública SqsProducer é responsável por enviar mensagens para uma fila SQS.

### 2.1. Definindo o link da fila;
```bash
private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/teste-fila";
```

### 2.2. Criação do Cliente SQS;
```bash
SqsClient sqsClient = SqsClient.builder()
        .endpointOverride(URI.create("http://localhost:4566"))
        .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")))
        .build();
```

### 2.3. Envio de Mensagens;
```bash
sendMessage(sqsClient, "Olá, eu sou Maura, estou fazendo o Processo seletivo da Solutis.");
sendMessage(sqsClient, "Muito prazer!");
```

### 2.4. Fechamento do Cliente SQS;

```bash
sqsClient.close();
```

### 2.5. Método sendMessage;

```bash
SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
        .queueUrl(QUEUE_URL)
        .messageBody(message)
        .delaySeconds(0)
        .build();
```

## Explicação da classe SqsConsumer.
A classe pública SqsConsumer é responsável por receber mensagens de uma fila SQS.

### 1. Definindo o link da fila.
```bash
private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/teste-fila";
```

### 2. Criação do Cliente SQS.
```bash
SqsClient sqsClient = SqsClient.builder()
        .endpointOverride(URI.create("http://localhost:4566"))
        .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")))
        .build();
```

### 3. Recebendo mensagens do cliente.
```bash
receiveMessages(sqsClient);
```

### 4. Fechamento do Cliente SQS.

```bash
sqsClient.close();
```

### 5. Método receiveMessages.

```bash
    public static void receiveMessages(SqsClient sqsClient) {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : messages) {
            System.out.println("Mensagem recebida: " + message.body());

            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteRequest);
        }
    }
```




