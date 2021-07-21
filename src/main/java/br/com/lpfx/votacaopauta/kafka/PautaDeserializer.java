package br.com.lpfx.votacaopauta.kafka;

import br.com.lpfx.votacaopauta.model.Pauta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class PautaDeserializer implements Deserializer<Pauta> {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Pauta deserialize(String s, byte[] bytes) {
        try{
            return MAPPER.readValue(bytes, Pauta.class);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
