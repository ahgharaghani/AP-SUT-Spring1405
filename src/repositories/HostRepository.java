package repositories;

import models.users.Guest;
import models.users.Host;

import java.util.ArrayList;
import java.util.List;

public class HostRepository {
    private static HostRepository instance;

    private List<Host> hosts;

    private HostRepository() {
        this.hosts = new ArrayList<>();
    }

    public static HostRepository getInstance() {
        if (instance == null) instance = new HostRepository();
        return instance;
    }

    public void addHost(Host newHost) {
        hosts.add(newHost);
    }

    public List<Host> getAllHosts() {
        return new ArrayList<>(hosts);
    }

    public Host getHostByName(String name) {
        return hosts.stream()
                .filter(host -> host.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Host getHostByEMail(String email) {
        return hosts.stream()
                .filter(host -> host.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
