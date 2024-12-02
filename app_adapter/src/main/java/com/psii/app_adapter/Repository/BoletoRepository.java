package com.psii.app_adapter.Repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.psii.app_adapter.Model.Boleto;
import java.util.List;


@Repository
public interface BoletoRepository extends MongoRepository<Boleto, String> {
    public List<Boleto> findByIdCliente(String idCliente);
}
