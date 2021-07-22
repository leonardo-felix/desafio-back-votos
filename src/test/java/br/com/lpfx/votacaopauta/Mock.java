package br.com.lpfx.votacaopauta;

import br.com.lpfx.votacaopauta.model.Pauta;

import java.time.LocalDateTime;

public class Mock {
    private static String calcDigVerif(String num) {
        int primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        if (soma % 11 == 0 | soma % 11 == 1)
            primDig = 0;
        else
            primDig = 11 - (soma % 11);

        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        soma += primDig * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            segDig = 0;
        else
            segDig = 11 - (soma % 11);

        return Integer.toString(primDig) + segDig;
    }

    public static String geraCPF() {
        StringBuilder iniciais = new StringBuilder();
        int numero;

        for (int i = 0; i < 9; i++) {
            numero = (int) (Math.random() * 10);
            iniciais.append(numero);
        }
        return iniciais + calcDigVerif(iniciais.toString());
    }


    public static Pauta pautaTeste(){
        final LocalDateTime agora = LocalDateTime.now();
        final LocalDateTime fim = agora.plusHours(12);

        Pauta pauta = new Pauta();
        pauta.setDescricao("Teste pauta");
        pauta.setInicio(agora);
        pauta.setFim(fim);

        return pauta;
    }
}
