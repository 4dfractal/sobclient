package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;

import java.time.Duration;

@Configuration
@EnableIntegration
public class HeartbeatClientConfig {

	@Value("${informationServer.port}")
	private int serverPort;

	@Bean
	@Qualifier("cf")
	public TcpNetClientConnectionFactory clientConnectionFactory() {
		TcpNetClientConnectionFactory connectionFactory = new TcpNetClientConnectionFactory("localhost", serverPort);
		connectionFactory.setSerializer(new CustomSerializerDeserializer());
		connectionFactory.setDeserializer(new CustomSerializerDeserializer());
		return connectionFactory;
	}

	@Bean
	public IntegrationFlow heartbeatClientFlow(
			@Qualifier("cf") TcpNetClientConnectionFactory clientConnectionFactory,
			HeartbeatClient heartbeatClient) {
		return IntegrationFlows.from(heartbeatClient::send,  e -> e.poller(Pollers.fixedDelay(Duration.ofSeconds(5))))
				.handle(Tcp.outboundGateway(clientConnectionFactory))
				.handle(heartbeatClient::receive)
				.get();
	}

	@Bean
	@Qualifier("cf1")
	public TcpNetClientConnectionFactory clientConnectionFactory1() {
		TcpNetClientConnectionFactory connectionFactory = new TcpNetClientConnectionFactory("localhost", serverPort);
		connectionFactory.setSerializer(new CustomSerializerDeserializer());
		connectionFactory.setDeserializer(new CustomSerializerDeserializer());
		return connectionFactory;
	}

	@Bean
	public IntegrationFlow heartbeatClientFlow1(
			@Qualifier("cf1") TcpNetClientConnectionFactory clientConnectionFactory	,
			HeartbeatClient heartbeatClient) {
		return IntegrationFlows.from(heartbeatClient::send1,  e -> e.poller(Pollers.fixedDelay(Duration.ofSeconds(5))))
							   .handle(Tcp.outboundGateway(clientConnectionFactory))
							   .handle(heartbeatClient::receive)
							   .get();
	}


	@Bean
	public HeartbeatClient heartbeatClient() {
		return new HeartbeatClient();
	}
}
