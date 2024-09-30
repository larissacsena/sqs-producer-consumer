package com.Solutis.SqsProducerConsumer;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URI;

public class SqsProducer {

    private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/teste-fila";

    public static void main(String[] args) {

        SqsClient sqsClient = SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey")))
                .build();

        sendMessage(sqsClient, "Olá, eu sou Maura, estou fazendo o Processo seletivo da Solutis.");
        sendMessage(sqsClient, "Muito prazer!");

        sqsClient.close();
    }

    public static void sendMessage(SqsClient sqsClient, String message) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .messageBody(message)
                .delaySeconds(0)
                .build();

        sqsClient.sendMessage(sendMsgRequest);
        System.out.println("Mensagem enviada: " + message);
    }
}
