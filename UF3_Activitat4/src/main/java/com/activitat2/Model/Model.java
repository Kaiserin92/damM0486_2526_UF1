package com.activitat2.Model;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private MongoCollection<Document> collection;

    public Model(MongoDatabase database) {
        this.collection = database.getCollection("Entrades");
    }

    public List<Document> getAllTasca() {

        List<Document> tasques = new ArrayList<>();

        for (Document doc : collection.find()) {
            tasques.add(doc);
        }
        return tasques;
    }

    public List<Document> getTascaByDates(String dataInici, String dataFi) {
        List<Document> tasques = new ArrayList<>();
        for (Document d : collection.find(and(gte("data_entrada", dataInici), lte("data_entrada", dataFi)))) {
            tasques.add(d);
        }
        return tasques;
    }

    public void inserirTasca(Tasca tasca) {
        collection.insertOne(tasca.toDocument());
    }

    public void eliminarTascaPerId(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    public void modificarTasca(String id, Document nousValors) {
        collection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", nousValors));
    }

    public List<Document> getFilteredTasca(String nom) {
        List<Document> tasques = new ArrayList<>();

        // regex amb "i" per ignorar majúscules/minúscules
        for (Document d : collection.find(regex("nom_alumne", nom, "i"))) {
            tasques.add(d);
        }

        return tasques;
    }

}
