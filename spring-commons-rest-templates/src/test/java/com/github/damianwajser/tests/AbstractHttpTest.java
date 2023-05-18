package com.github.damianwajser.tests;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.ClassRule;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

public class AbstractHttpTest {
    static Consumer<CreateContainerCmd> cmd = e -> e.withPortBindings(new PortBinding(Ports.Binding.bindPort(80), new ExposedPort(80)));
    private static int ECHO_PORT = 80;
    private static String ECHO_HOST = "http://localhost";

    @ClassRule
    public static GenericContainer<?> redisContainer = new GenericContainer(DockerImageName.parse("kennethreitz/httpbin:latest"))
            .withExposedPorts(ECHO_PORT)
            .withCreateContainerCmdModifier(cmd);

    public static String getEchoUrl(){
        return ECHO_HOST + ":" + ECHO_PORT;
    }

}
