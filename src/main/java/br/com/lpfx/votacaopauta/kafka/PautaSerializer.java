package br.com.lpfx.votacaopauta.kafka;

import br.com.lpfx.votacaopauta.model.Pauta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class PautaSerializer implements Serializer<Pauta> {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Pauta pauta) {
        try{
            return MAPPER.writeValueAsString(pauta).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
