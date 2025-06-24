package lab.app;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.GenericType;
import lab.dto.ComplaintDTO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget baseTarget = client.target("http://localhost:8080/Server-1.0-SNAPSHOT/api/complaints");

        List<ComplaintDTO> allComplaints = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<>() {});
        System.out.println("Wszystkie skargi:");
        allComplaints.forEach(System.out::println);

        ComplaintDTO openComplaint = allComplaints.stream()
                .filter(c -> "open".equalsIgnoreCase(c.getStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Brak otwartych skarg"));
        Long id = openComplaint.getId();
        System.out.println("\nOtwarta skarga ID=" + id + ":");
        System.out.println(openComplaint);

        openComplaint.setStatus("closed");
        baseTarget.path(String.valueOf(id))
                .request()
                .put(Entity.entity(openComplaint, MediaType.APPLICATION_JSON));

        List<ComplaintDTO> openComplaints = baseTarget
                .queryParam("status", "open")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<>() {});
        System.out.println("\nOtwarte skargi po modyfikacji:");
        openComplaints.forEach(System.out::println);


        client.close();
    }
}
