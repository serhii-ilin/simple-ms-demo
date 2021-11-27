package com.demo.useradmin1;

import org.apache.avro.grpc.AvroGrpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.useradmin1.grpc.UserAdminService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;

@Configuration
public class GrpcConfig {
  @Bean(destroyMethod = "shutdown", initMethod = "start")
  public Server server(
      @Autowired ServerServiceDefinition userAdminServiceDefinition,
      @Value("${grpc.port:3333}") int grpcPort) {
    return ServerBuilder.forPort(grpcPort).addService(userAdminServiceDefinition).build();
  }

  @Bean
  public ServerServiceDefinition userAdminServiceDefinition(
      @Autowired UserAdminService userAdminService) {
    return AvroGrpcServer.createServiceDefinition(UserAdminService.class, userAdminService);
  }
}
