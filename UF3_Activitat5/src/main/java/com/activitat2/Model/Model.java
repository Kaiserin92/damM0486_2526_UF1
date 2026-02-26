package com.activitat2.Model;

import org.bson.Document;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Model {

    // La URL de la teva API a Vercel
    private static final String BASE_URL = "https://m6uf3api.vercel.app";

    private HttpClient httpClient;
    private boolean responseReceived;

    // Constructor limpio, ya no recibe ni pide MongoDatabase
    public Model() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public List<Document> getAllTasca() {
        List<Document> tasques = new ArrayList<>();
        this.responseReceived = false;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/list"))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                String responseBody = response.body();
                tasques.addAll(parseJsonArray(responseBody));
                this.responseReceived = true;
            }).exceptionally(e -> {
                e.printStackTrace();
                this.responseReceived = true;
                return null;
            });

            while (!this.responseReceived) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasques;
    }

    public void inserirTasca(Tasca tasca) {
        this.responseReceived = false;

        try {
            String jsonBody = tasca.toDocument().toJson();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                this.responseReceived = true;
            }).exceptionally(e -> {
                e.printStackTrace();
                this.responseReceived = true;
                return null;
            });

            while (!this.responseReceived) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarTascaPerId(String id) {
        this.responseReceived = false;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/delete/" + id))
                    .DELETE()
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                this.responseReceived = true;
            }).exceptionally(e -> {
                e.printStackTrace();
                this.responseReceived = true;
                return null;
            });

            while (!this.responseReceived) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarTasca(String id, Document nousValors) {
        this.responseReceived = false;

        try {
            nousValors.remove("_id");
            String jsonBody = nousValors.toJson();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/update/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                this.responseReceived = true;
            }).exceptionally(e -> {
                e.printStackTrace();
                this.responseReceived = true;
                return null;
            });

            while (!this.responseReceived) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> getTascaByDates(String dataInici, String dataFi) {
        List<Document> tasques = new ArrayList<>();
        this.responseReceived = false;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + "/filterdates/" + dataInici + "/" + dataFi))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            responseFuture.thenAccept(response -> {
                String responseBody = response.body();
                if (response.statusCode() == 200) {
                    tasques.addAll(parseJsonArray(responseBody));
                }
                this.responseReceived = true;
            }).exceptionally(e -> {
                e.printStackTrace();
                this.responseReceived = true;
                return null;
            });

            while (!this.responseReceived) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasques;
    }

    public List<Document> getFilteredTasca(String nom) {

        List<Document> totes = getAllTasca();
        List<Document> filtrades = new ArrayList<>();

        for (Document d : totes) {
            String nomAlumne = d.getString("nom_alumne");
            if (nomAlumne != null && nomAlumne.toLowerCase().contains(nom.toLowerCase())) {
                filtrades.add(d);
            }
        }
        return filtrades;
    }

// --- Mètode per transformar el JSON array de l'API en List<Document> BSON ---

    private List<Document> parseJsonArray(String json) {
        List<Document> llista = new ArrayList<>();
        if (json == null || json.trim().isEmpty() || json.equals("[]") || json.contains("message")) {
            return llista; 
        }
        try {
            Document wrapper = Document.parse("{ \"data\": " + json + " }");
            List<Document> elements = wrapper.getList("data", Document.class);
            if (elements != null) {
                // EL TRUC: Convertim els "_id" que vénen com a text (String) 
                // a objectes ObjectId reals perquè la Vista no peti.
                for (Document doc : elements) {
                    if (doc.containsKey("_id") && doc.get("_id") instanceof String) {
                        String idString = doc.getString("_id");
                        doc.put("_id", new org.bson.types.ObjectId(idString));
                    }
                }
                llista.addAll(elements);
            }
        } catch (Exception e) {
            System.err.println("Error en convertir JSON: " + e.getMessage());
        }
        return llista;
    }
}