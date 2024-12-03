package com.psii.app_adapter.Repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.psii.app_adapter.Model.Recibo;
import java.util.List;

@Repository
public interface ReciboRepository extends MongoRepository<Recibo, String> {
    public List<Recibo>  findByIdOrigem(String idOrigem);
    public List<Recibo>  findByIdDestino(String idDestino);
}
