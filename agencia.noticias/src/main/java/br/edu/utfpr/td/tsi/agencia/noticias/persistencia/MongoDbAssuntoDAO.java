package br.edu.utfpr.td.tsi.agencia.noticias.persistencia;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.edu.utfpr.td.tsi.agencia.noticias.modelo.Assunto;

@Component
public class MongoDbAssuntoDAO implements AssuntoDAO {

    @Value("${mongodb.connection-string}")
    private String connectionString;

    @Value("${mongodb.database}")
    private String databaseName;

    @Override
    public void gravar(Assunto assunto) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("assunto");

            assunto.setId(UUID.randomUUID().toString());
            Document doc = new Document("_id", assunto.getId())
                    .append("nome", assunto.getNome())
                    .append("tipo", assunto.getTipo())
                    .append("descricao", assunto.getDescricao());

            collection.insertOne(doc);
        }
    }

    @Override
    public void remover(String id) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("assunto");
            Bson filtro = eq("_id", id);
            collection.deleteOne(filtro);
        }
    }
    @Override
    public List<Assunto> listarTodos() {
        List<Assunto> assuntos = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("assunto");

            FindIterable<Document> documentos = collection.find();
            for (Document doc : documentos) {
                Assunto assunto = new Assunto();
                assunto.setId(doc.getString("_id"));
                assunto.setNome(doc.getString("nome"));
                assunto.setTipo(doc.getString("tipo"));
                assunto.setDescricao(doc.getString("descricao"));
                assuntos.add(assunto);
            }
        }
        return assuntos;
    }

    @Override
    public Assunto procurar(String id) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection("assunto");
            Document doc = collection.find(eq("_id", id)).first();
            if (doc != null) {
                Assunto assunto = new Assunto();
                assunto.setId(doc.getString("_id"));
                assunto.setNome(doc.getString("nome"));
                assunto.setTipo(doc.getString("tipo"));
                assunto.setDescricao(doc.getString("descricao"));
                return assunto;
            }
        }
        return null;
    }

    @Override
    public void atualizar(String id, Assunto assuntoAtualizado) {
        this.remover(id);
        this.gravar(assuntoAtualizado);
    }
}
